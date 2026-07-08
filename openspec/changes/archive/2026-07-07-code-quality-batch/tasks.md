## 1. Exceção customizada NotFoundException

- [x] 1.1 Criar `src/main/java/com/ana/tenisdemesa/exception/NotFoundException.java` (RuntimeException com construtor mensagem)
- [x] 1.2 Substituir `RuntimeException("Partida não encontrada")` em `PartidaController.editarForm()`, `salvar()`, `excluir()`
- [x] 1.3 Substituir `RuntimeException("Campeonato não encontrado")` em `CampeonatoService.buscarPorId()`

## 2. Extrair lógica de domínio dos controllers

- [x] 2.1 Extrair cálculo de vitórias, derrotas, taxa para `CampeonatoService.calcularEstatisticas()`
- [x] 2.2 Extrair validação de data duplicada para `CampeonatoService.validarDatas()`
- [x] 2.3 Mover `Treino.setDuracaoPorHorasEMinutos(horas, minutos)` para o model `Treino`

## 3. Helpers booleanos nos models

- [x] 3.1 Adicionar `Partida.isVitoria()`, `Partida.isDerrota()`
- [x] 3.2 Adicionar `Medalha.getTipoCssClass()`, `Medalha.isOuro()`, `Medalha.isPrata()`, `Medalha.isBronze()`, `Medalha.getTipoLabel()`, `Medalha.getTipoColorStyle()`

## 4. Templates: remover string de enum, URLs hardcoded, medalha inline

- [x] 4.1 Substituir `p.resultado.name() == 'VITORIA'` → `p.vitoria` e `p.resultado.name() == 'DERROTA'` → `p.derrota` em templates
- [x] 4.2 Substituir `m.tipo.name() == 'OURO'` → `m.ouro` (e prata/bronze) em templates
- [x] 4.3 Substituir opcionais com `m.tipoCssClass`, `m.tipoLabel` nos templates
- [x] 4.4 Trocar links hardcoded (`/campeonatos/novo`, `/treinos/novo`) por `th:href="@{...}"` nos templates
- [x] 4.5 Criar `medalhas/fragment.html` com `th:fragment="medalha-badge"` e reusar nos templates existentes
- [x] 4.6 Migrar JS inline de adicionar/remover sets para `app.js` com delegação de evento

## 5. Configuração e índices

- [x] 5.1 Mudar `spring.jpa.hibernate.ddl-auto=update` para `validate` em `application.properties`
- [x] 5.2 Adicionar `@Index(name="idx_partida_campeonato", columnList="campeonato_id")` em `Partida`
- [x] 5.3 Adicionar `@Index(name="idx_medalha_campeonato", columnList="campeonato_id")` em `Medalha`

## 6. Testes unitários

- [x] 6.1 Escrever `PartidaServiceTest.validarSets_quandoSet10x8_lancaExcecao()`
- [x] 6.2 Escrever `PartidaServiceTest.validarSets_quandoSet11x9_ok()`
- [x] 6.3 Escrever `EstatisticaServiceTest.sequenciaAtual_3V1D_retorna3V()`
- [x] 6.4 Escrever `TreinoServiceTest.buildResumo_agregaTipos()`
