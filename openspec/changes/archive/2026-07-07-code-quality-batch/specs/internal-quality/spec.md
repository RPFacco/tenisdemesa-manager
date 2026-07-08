## ADDED Requirements

### Requirement: Exceções customizadas para entidades não encontradas
O sistema DEVE lançar `NotFoundException` (ou similar) quando uma entidade não é encontrada por ID, em vez de `RuntimeException` genérico.

#### Scenario: Campeonato não encontrado
- **WHEN** `CampeonatoService.buscarPorId()` recebe um ID inexistente
- **THEN** lança `NotFoundException` com mensagem "Campeonato não encontrado"

#### Scenario: Partida não encontrada
- **WHEN** `PartidaController.editarForm()` busca partida por ID inexistente
- **THEN** lança `NotFoundException` com mensagem "Partida não encontrada"

### Requirement: Lógica de domínio extraída para services
Controllers NÃO DEVEM conter cálculos de domínio (vitórias, derrotas, taxas). Esses cálculos DEVEM estar nos services.

#### Scenario: Estatísticas de campeonato via service
- **WHEN** `CampeonatoController.detalhe()` é chamado
- **THEN** o cálculo de vitórias, derrotas e taxa de vitória DELEGA para `CampeonatoService.calcularEstatisticas()`
- **THEN** o controller apenas repassa os resultados para o model

### Requirement: Conversão horas/minutos no model Treino
O método `Treino.setDuracaoPorHorasEMinutos(horas, minutos)` DEVE existir no model, em vez da conversão estar no controller.

#### Scenario: Salvar treino com horas e minutos
- **WHEN** `TreinoController.salvar()` recebe horas e minutos
- **THEN** chama `treino.setDuracaoPorHorasEMinutos(horas, minutos)` em vez de fazer a conta inline

### Requirement: Templates usam helpers booleanos em vez de enum.name()
Os models DEVEM expor métodos como `isVitoria()`, `isDerrota()`, `isOuro()`, `isPrata()`, `isBronze()` para evitar comparação de strings nos templates.

#### Scenario: Renderizar resultado da partida
- **WHEN** o template verifica se uma partida foi vitória
- **THEN** usa `p.vitoria` em vez de `p.resultado.name() == 'VITORIA'`

#### Scenario: Renderizar tipo de medalha
- **WHEN** o template aplica classe CSS a uma medalha
- **THEN** usa métodos `m.ouro`, `m.prata`, `m.bronze` ou `m.tipoCssClass` em vez de `m.tipo.name() == 'OURO'`

### Requirement: Estilo de medalha centralizado em fragmento
A marcação HTML para exibir medalhas (ícone, cor, texto) DEVE estar em um fragmento Thymeleaf único, reutilizado em todos os templates.

#### Scenario: Medalha renderizada em qualquer página
- **WHEN** qualquer template precisa exibir uma medalha
- **THEN** usa `th:replace="medalhas/fragment :: medalha-badge"` em vez de repetir a marcação

### Requirement: ddl-auto=validate em produção
O Hibernate DEVE ser configurado com `ddl-auto=validate` para evitar perda acidental de dados.

#### Scenario: Aplicação inicia com schema diferente
- **WHEN** a aplicação sobe em produção
- **THEN** Hibernate valida que as entidades JPA correspondem ao schema do banco
- **THEN** se houver divergência, a aplicação NÃO sobe (fail-fast)

### Requirement: Índices explícitos nas FKs
As colunas `campeonato_id` em `Partida` e `Medalha` DEVEM ter índices explícitos para consultas por campeonato.

#### Scenario: Buscar partidas por campeonato
- **WHEN** o sistema consulta partidas de um campeonato
- **THEN** o banco usa o índice em `campeonato_id` em vez de full scan

### Requirement: Testes unitários para services
Os services com lógica não-trivial DEVEM ter testes unitários.

#### Scenario: validarSets rejeita set inválido
- **WHEN** `PartidaService.validarSets()` recebe um set com 10-8
- **THEN** lança IllegalArgumentException dizendo que vencedor precisa de mínimo 11 pontos

#### Scenario: sequenciaAtual calcula sequência correta
- **WHEN** há 3 vitórias seguidas seguidas de 1 derrota
- **THEN** `sequenciaAtual()` retorna "3V"

#### Scenario: buildResumo agrega treinos por tipo
- **WHEN** há treinos de tipos diferentes no período
- **THEN** `buildResumo()` retorna total e breakdown por tipo

### Requirement: Inline JS de sets migrado para app.js
O JavaScript para adicionar/remover sets em partidas/form.html DEVE estar em `app.js`, não inline no template.

#### Scenario: Adicionar set dinamicamente
- **WHEN** usuário clica "Adicionar Set" no formulário de partida
- **THEN** o evento é tratado pelo script em `app.js` via delegação
- **THEN** o novo set tem índices reindexados corretamente após remoção
