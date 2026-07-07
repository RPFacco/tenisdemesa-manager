# Especificação técnica — Painel de Desempenho de Tênis de Mesa

Este documento é o contexto completo para implementação em **oneshot** via OpenSpec. Ele assume zero conhecimento prévio do projeto — tudo que a IA precisa pra gerar o código está aqui.

## 1. Visão geral

Site pessoal para minha irmã registrar seu desempenho no tênis de mesa: campeonatos disputados, partidas de cada campeonato (com placar, adversário e vídeo do YouTube), medalhas conquistadas, estatísticas agregadas e horas de treino. Uso pessoal, single-user, sem necessidade de login.

## 2. Stack técnica

| Camada | Tecnologia | Versão |
|---|---|---|
| Linguagem | Java | 21 (LTS) |
| Framework | Spring Boot | **3.5.x** (última release: 3.5.16) — ver nota abaixo |
| Build | Maven | 3.9+ |
| Banco de dados | MySQL | 8.4.x (LTS) |
| Driver JDBC | mysql-connector-j | versão compatível com Spring Boot 3.5 (gerenciada pelo starter) |
| Frontend | Thymeleaf + thymeleaf-layout-dialect | via starter |
| CSS | Bootstrap 5.3.x | via CDN |
| Tema | Bootswatch "Flatly" | via CDN (substitui a URL do bootstrap.min.css padrão) |
| Ícones | Bootstrap Icons | via CDN |
| Boilerplate | Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`) | opcional mas recomendado |
| Hospedagem app | Render (Docker, free tier) | — |
| Hospedagem banco | Aiven (MySQL, tier sempre-grátis) | — |

**Nota sobre a versão do Spring Boot:** a série 3.x chegou ao fim do suporte oficial em 30/06/2026 (a 4.x é a atual). Optamos por 3.5.x deliberadamente para este projeto, por ser a versão com que a maioria dos tutoriais, exemplos e a própria geração de código por IA têm mais familiaridade — reduz o risco de erros no oneshot. Usar `jakarta.persistence.*` (não `javax.*`) nas entidades, pois o namespace Jakarta já vale desde o Spring Boot 3.0. Migração para 4.x pode ser feita depois, como melhoria futura.

## 3. Estrutura de pacotes

```
com.ana.tenisdemesa
 ├─ TenisDeMesaApplication.java
 ├─ config/
 │   └─ DataSeeder.java          (CommandLineRunner — popula dados de exemplo)
 ├─ model/
 │   ├─ Campeonato.java
 │   ├─ Medalha.java
 │   ├─ Partida.java
 │   ├─ SetPartida.java          (@Embeddable)
 │   ├─ Treino.java
 │   └─ enums/
 │       ├─ TipoMedalha.java     (OURO, PRATA, BRONZE)
 │       ├─ Modalidade.java      (SIMPLES, DUPLAS, DUPLAS_MISTAS, EQUIPE)
 │       ├─ FasePartida.java     (FASE_DE_GRUPOS, OITAVAS_DE_FINAL, QUARTAS_DE_FINAL, SEMIFINAL, DISPUTA_DE_TERCEIRO_LUGAR, FINAL, AMISTOSA)
 │       ├─ ResultadoPartida.java (VITORIA, DERROTA — nunca persistido, sempre calculado)
 │       └─ TipoTreino.java      (TECNICA, FISICO, JOGO)
 ├─ repository/
 │   ├─ CampeonatoRepository.java
 │   ├─ MedalhaRepository.java
 │   ├─ PartidaRepository.java
 │   └─ TreinoRepository.java
 ├─ service/
 │   ├─ CampeonatoService.java
 │   ├─ PartidaService.java      (contém o cálculo de resultado a partir dos sets)
 │   ├─ MedalhaService.java
 │   ├─ TreinoService.java
 │   └─ EstatisticaService.java  (cálculos agregados da aba Geral e sequência atual)
 └─ controller/
     ├─ CampeonatoController.java
     ├─ PartidaController.java
     ├─ MedalhaController.java
     ├─ TreinoController.java
     └─ GeralController.java

src/main/resources
 ├─ templates/
 │   ├─ layout.html              (base com navbar: Campeonatos | Geral | Treino)
 │   ├─ geral.html
 │   ├─ campeonatos/
 │   │   ├─ lista.html
 │   │   ├─ form.html
 │   │   └─ detalhe.html         (stats do campeonato + partidas + medalhas)
 │   ├─ partidas/
 │   │   └─ form.html            (com JS para adicionar/remover sets dinamicamente)
 │   ├─ medalhas/
 │   │   └─ form.html
 │   └─ treinos/
 │       ├─ lista.html
 │       └─ form.html
 └─ application.properties
```

## 4. Modelagem de dados

### 4.1 Campeonato
| Campo | Tipo | Regras |
|---|---|---|
| id | Long | PK, auto-gerado |
| nome | String | obrigatório |
| local | String | obrigatório |
| dataInicio | LocalDate | obrigatório |
| dataFim | LocalDate | obrigatório, >= dataInicio |
| categoria | String | opcional, texto livre (ex: "Estadual", "Regional", "Copa da cidade") |

Relacionamentos: `@OneToMany` para `Partida` e `Medalha`, ambos com `cascade = CascadeType.ALL, orphanRemoval = true` (excluir campeonato exclui partidas e medalhas junto).

### 4.2 Medalha
| Campo | Tipo | Regras |
|---|---|---|
| id | Long | PK, auto-gerado |
| campeonato | Campeonato | `@ManyToOne`, obrigatório |
| tipo | TipoMedalha (enum) | obrigatório: OURO, PRATA, BRONZE |
| modalidade | Modalidade (enum) | obrigatório: SIMPLES, DUPLAS, DUPLAS_MISTAS, EQUIPE |

Sem limite de medalhas por campeonato (permite, ex: ouro em simples + prata em duplas no mesmo campeonato).

### 4.3 Partida
| Campo | Tipo | Regras |
|---|---|---|
| id | Long | PK, auto-gerado |
| campeonato | Campeonato | `@ManyToOne`, obrigatório |
| adversario | String | obrigatório |
| fase | FasePartida (enum) | opcional (nem todo formato tem fases eliminatórias) |
| data | LocalDate | obrigatório, não pode ser data futura |
| sets | List\<SetPartida\> | `@ElementCollection`, ordenada, mínimo 1 set |
| linkYoutube | String | opcional; se preenchido, deve começar com `https://youtube.com`, `https://www.youtube.com` ou `https://youtu.be` |

**`resultado` (VITORIA/DERROTA) NÃO é uma coluna do banco.** É sempre calculado dinamicamente por `PartidaService`, tanto na exibição quanto nas agregações de estatísticas — assim nunca fica dessincronizado se os sets forem editados depois.

### 4.4 SetPartida (`@Embeddable`)
| Campo | Tipo | Regras |
|---|---|---|
| numeroSet | int | ordem do set na partida (1, 2, 3...) |
| pontosAtleta | int | >= 0 |
| pontosAdversario | int | >= 0 |

**Regra de validação de um set:** o vencedor (maior pontuação) precisa ter no mínimo 11 pontos **e** vantagem mínima de 2 sobre o perdedor. Exemplos válidos: 11-8, 11-9, 12-10, 14-12. Exemplo inválido: 11-10 (vantagem insuficiente — precisaria continuar até alguém abrir 2 pontos, ex: 12-10).

### 4.5 Treino
| Campo | Tipo | Regras |
|---|---|---|
| id | Long | PK, auto-gerado |
| data | LocalDate | obrigatório, não pode ser data futura |
| duracaoHoras | Double | obrigatório, > 0, aceita fração (ex: 1.5) |
| tipo | TipoTreino (enum) | obrigatório: TECNICA, FISICO, JOGO |

Sem relação com Campeonato — treino é registrado independentemente.

## 5. Regras de negócio e cálculos

### 5.1 Cálculo do resultado de uma partida (`PartidaService`)
1. Validar cada `SetPartida` conforme a regra da seção 4.4.
2. Contar sets vencidos pela atleta vs. pelo adversário (comparando `pontosAtleta` x `pontosAdversario` em cada set).
3. Validar que a soma de sets vencidos pela atleta é **diferente** da soma vencida pelo adversário (não pode empatar no total de sets — isso indicaria erro de digitação).
4. `resultado` = VITORIA se sets vencidos pela atleta > sets vencidos pelo adversário; caso contrário, DERROTA.
5. Esse cálculo roda inteiramente no backend — o formulário nunca envia um campo "resultado".

### 5.2 Estatísticas por campeonato (exibidas em `/campeonatos/{id}`)
- Vitórias = quantidade de partidas do campeonato com resultado calculado = VITORIA
- Derrotas = quantidade de partidas com resultado = DERROTA
- Taxa de vitória = vitórias / (vitórias + derrotas) × 100, arredondada ao inteiro mais próximo; se não houver partidas, exibir "—"
- Medalhas do campeonato, agrupadas por tipo (contagem de ouro/prata/bronze)

### 5.3 Estatísticas gerais (aba `/geral`)
- Mesmas métricas da seção 5.2, agregando **todas** as partidas de **todos** os campeonatos
- Total de medalhas por tipo, somando todos os campeonatos
- Total de campeonatos disputados
- **Sequência atual:** ordenar todas as partidas (de todos os campeonatos) por `data` decrescente; a partir da mais recente, contar quantas partidas consecutivas têm o mesmo resultado. Exibir como "4V" (4 vitórias seguidas) ou "2D" (2 derrotas seguidas). Sem partidas registradas → exibir "—"

### 5.4 Estatísticas de treino (aba `/treinos`)
- Por dia: soma de `duracaoHoras` de todos os treinos daquela data
- Por semana: agrupamento por semana ISO (segunda a domingo), soma de `duracaoHoras`
- Por mês: agrupamento por mês corrido (`YearMonth` da data), soma de `duracaoHoras`
- Em cada período (dia/semana/mês), exibir também o breakdown por `tipo` (técnica/físico/jogo)

## 6. Mapa de rotas

| Método | Rota | Descrição |
|---|---|---|
| GET | `/` | Redireciona para `/geral` |
| GET | `/geral` | Estatísticas agregadas de todos os campeonatos + sequência atual |
| GET | `/campeonatos` | Lista de campeonatos (nome, local, período, contagem de medalhas) |
| GET | `/campeonatos/novo` | Formulário de criação |
| POST | `/campeonatos` | Salva novo campeonato |
| GET | `/campeonatos/{id}` | Detalhe: estatísticas do campeonato + partidas + medalhas |
| GET | `/campeonatos/{id}/editar` | Formulário de edição |
| POST | `/campeonatos/{id}` | Atualiza campeonato |
| POST | `/campeonatos/{id}/excluir` | Exclui campeonato (cascade em partidas e medalhas) |
| GET | `/campeonatos/{id}/partidas/nova` | Formulário de nova partida (sets dinâmicos via JS) |
| POST | `/campeonatos/{id}/partidas` | Salva partida (resultado calculado no backend) |
| GET | `/campeonatos/{id}/partidas/{partidaId}/editar` | Formulário de edição de partida |
| POST | `/campeonatos/{id}/partidas/{partidaId}` | Atualiza partida |
| POST | `/campeonatos/{id}/partidas/{partidaId}/excluir` | Exclui partida |
| GET | `/campeonatos/{id}/medalhas/nova` | Formulário de nova medalha |
| POST | `/campeonatos/{id}/medalhas` | Salva medalha |
| POST | `/campeonatos/{id}/medalhas/{medalhaId}/excluir` | Exclui medalha |
| GET | `/treinos` | Lista de treinos + resumo por dia/semana/mês |
| GET | `/treinos/novo` | Formulário de novo treino |
| POST | `/treinos` | Salva treino |
| POST | `/treinos/{id}/excluir` | Exclui treino |

Todas as ações de escrita usam POST (sem PUT/DELETE via HiddenHttpMethodFilter, para manter simples com formulários Thymeleaf padrão).

## 7. Fluxos de CRUD — validações e comportamento

**Campeonato:** nome e local obrigatórios; dataFim >= dataInicio (validação server-side com Bean Validation). Excluir pede confirmação (modal Bootstrap) antes do POST; após excluir, mensagem flash "Campeonato excluído" e redireciona para `/campeonatos`.

**Partida:** adversário obrigatório; data obrigatória e não-futura; interface permite adicionar/remover linhas de set via JavaScript puro (sem framework); ao menos 1 set; link do YouTube validado só se preenchido. Se a validação de sets falhar (set inválido ou empate no total), retorna o formulário com mensagem de erro específica, sem perder os dados já digitados.

**Medalha:** tipo e modalidade obrigatórios (selects). Sem limite de quantidade por campeonato.

**Treino:** data obrigatória e não-futura; duração > 0; tipo obrigatório (select). Tela de listagem mostra cards de resumo no topo (horas hoje / na semana atual / no mês atual) antes da lista detalhada.

Todas as exclusões pedem confirmação. Todas as ações de salvar/atualizar exibem mensagem flash de sucesso e redirecionam para a tela de detalhe/lista relevante.

## 8. Design / UI

Seguir a direção visual já validada: tema Bootswatch "Flatly" (ou similar, limpo e neutro), Bootstrap Icons para os ícones (troféu para vitórias, chama para sequência, etc.), cards de estatística no topo de cada aba, badges verdes para vitória e vermelhos para derrota. Navbar fixa no topo com 3 itens: Campeonatos, Geral, Treino. Botão de ação principal (ex: "Nova partida", "Novo campeonato") sempre no canto superior direito de cada tela, usando a cor de destaque do tema.

## 9. Dados de exemplo (seed)

Implementar `DataSeeder` (`CommandLineRunner`) que verifica `campeonatoRepository.count() == 0` e, apenas nesse caso, popula:
- 2 campeonatos de exemplo (datas e locais fictícios, ex: interior do Rio Grande do Sul)
- 3–4 partidas em cada campeonato, com sets variados (algumas vitórias, algumas derrotas), 1–2 com link do YouTube fictício
- 1–2 medalhas por campeonato
- 8–10 registros de treino distribuídos nas últimas 3–4 semanas, variando entre os 3 tipos

Isso garante que, na primeira execução, as telas já aparecem preenchidas em vez de vazias.

## 10. Configuração de ambiente e deploy

`application.properties` (valores sensíveis via variável de ambiente, nunca hardcoded):

```properties
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?sslMode=REQUIRED&serverTimezone=America/Sao_Paulo
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
server.port=${PORT:8080}
```

> Aiven exige SSL nas conexões MySQL do tier gratuito — por isso `sslMode=REQUIRED` é obrigatório, não opcional.

### 10.1 Endpoint de health-check (para manter o app acordado no Render free tier)

O free tier do Render coloca o serviço pra dormir após ~15 min sem requisições, e o primeiro request seguinte demora cerca de 1 minuto pra acordar o container. A mitigação é um serviço externo de ping/cron fazendo requisições periódicas (a cada 5–10 min) a um endpoint leve, que não toque no banco.

Adicionar ao `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

E ao `application.properties`:

```properties
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=never
```

Isso expõe `GET /actuator/health`, retornando `{"status":"UP"}` sem consultar o banco por padrão — ideal como alvo do ping externo.

**Nota (fora do escopo do código, passo manual pós-deploy):** a configuração do serviço de ping (ex: cron-job.org, gratuito, sem restrição de uso comercial, intervalos de até 1 min) é feita depois do deploy, apontando para `https://seu-app.onrender.com/actuator/health`, e depende de uma conta criada por você — não pode ser automatizada via este `.md` nem faz parte do oneshot de código.

`Dockerfile` (multi-stage):

```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

No Render, configurar as variáveis de ambiente `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` com os valores do painel do Aiven. O Render injeta `PORT` automaticamente — por isso `server.port=${PORT:8080}` (usa 8080 como fallback local).

## 11. Fora de escopo (explícito)

- Autenticação/login (acesso direto, sem tela de login)
- Múltiplos usuários/atletas (single-user)
- Importação automática de dados da CBTM ou federações estaduais (não existe API pública para isso — ver decisão anterior)
- Testes automatizados (pode ser adicionado depois, não é objetivo deste oneshot)
- Internacionalização (só português)

## 12. Critérios de aceite

- [ ] Aplicação sobe localmente com `mvn spring-boot:run` conectando a um MySQL (local ou remoto)
- [ ] CRUD completo funcionando: Campeonato, Partida (com sets), Medalha, Treino
- [ ] Resultado da partida sempre calculado a partir dos sets, nunca informado manualmente, nunca persistido
- [ ] Aba Geral mostra estatísticas agregadas corretas, incluindo sequência atual
- [ ] Aba Treino mostra resumo por dia/semana/mês com breakdown por tipo
- [ ] Interface visualmente consistente (tema Bootswatch) em todas as páginas
- [ ] Projeto builda como imagem Docker e roda no Render conectado ao MySQL do Aiven
- [ ] Banco nasce populado com dados de exemplo na primeira execução (`DataSeeder`)
