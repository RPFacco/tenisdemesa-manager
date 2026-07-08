## Context

O código atual tem diversas small debts que não foram endereçadas nas mudanças anteriores. Elas são independentes entre si (podem ser implementadas em qualquer ordem) e nenhuma muda comportamento visível ao usuário. Isso torna seguro agrupá-las num único batch.

## Goals / Non-Goals

**Goals:**
- Eliminar lógica de domínio dos controllers
- Substituir RuntimeException por exceções nomeadas
- Remover comparações `name()` de enums nos templates
- Centralizar estilo de medalha em fragmento único
- Corrigir URLs hardcoded
- Migrar inline JS de sets para app.js
- Ajustar configuração de produção (ddl-auto, índices)
- Adicionar testes unitários para services críticos

**Non-Goals:**
- Não muda comportamento visível (nenhuma tela ou fluxo se altera)
- Não introduz novas funcionalidades
- Não refatora a estrutura de pacotes (cada classe fica onde está)
- Não adiciona integração contínua ou cobertura mínima obrigatória

## Decisions

### 1. Exceção única `NotFoundException` em vez de uma por entidade
Uma única classe `NotFoundException` (RuntimeException) com construtor que aceita mensagem. Evita explosão de classes (PartidaNotFoundException, CampeonatoNotFoundException, etc.) sem ganho real.

### 2. Métodos booleanos no model vs. Thymeleaf utility
Adicionar `isVitoria()`, `isDerrota()` em `Partida` e `getTipoCssClass()`, `isOuro()`, `isPrata()`, `isBronze()` em `Medalha`. Alternativa considerada: utility class Thymeleaf. Rejeitada porque o model é o lugar natural e evita injetar dependência nos templates.

### 3. Fragmento `medalhas/fragment.html` em vez de inline
Criar `medalhas/fragment.html` com `th:fragment="medalha-badge"` e reusar com `th:replace`. A abordagem é a mesma usada no HTMX period-filter (fragmentos Thymeleaf), então o padrão já está estabelecido.

### 4. `ddl-auto` em produção: `validate` + script manual
Mudar para `validate` e documentar que migrações devem ser feitas manualmente (ou via Flyway no futuro). Para um app pessoal, scripts SQL manuais são suficientes.

### 5. Inline JS de sets → app.js via delegação de evento
O código atual define o handler no `DOMContentLoaded` dentro do template. Migrar para `app.js` usando delegação de evento no container `#sets-container`. Isso também resolve o bug de reindexação após remoção (task 4.3). Alternativa: HTMX para sets dinâmicos — rejeitada por ser overkill para este caso.

### 6. Testes com JUnit 5 + Mockito
Usar `@ExtendWith(MockitoExtension.class)` nos testes de service. O projeto já tem JUnit 5 como dependência (spring-boot-starter-test). Mockito não precisa de dependência extra.

## Risks / Trade-offs

| Risco | Mitigação |
|-------|-----------|
| Mudar `ddl-auto` para `validate` pode impedir startup se schema divergir | É o comportamento desejado (fail-fast). Rodar `openspec doctor` ou verificar schema antes do deploy |
| Testes novos podem falhar se a lógica existente tiver bugs não detectados | Bugs descobertos são bugs corrigidos — aceitável |
| Fragmento de medalha requer mudança em 5 templates ao mesmo tempo | Trocas são puramente mecânicas (find & replace), risco baixo |
| Migrar inline JS para app.js pode quebrar se houver conflito de eventos | O handler por delegação é mais robusto que o atual, e o template pode manter fallback |
