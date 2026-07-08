## ADDED Requirements

### Requirement: Usuário pode filtrar estatísticas por período sem reload completo
O sistema DEVE trocar os cards de estatísticas e medalhas na página `/geral` via requisição parcial (HTMX) ao alternar entre períodos, sem recarregar layout, navbar, footer ou scripts.

#### Scenario: Alternar período de "Ano" para "30 dias"
- **WHEN** usuário clica no botão "30 dias" na página `/geral`
- **THEN** o sistema faz uma requisição GET `/geral?periodo=30d` com header `HX-Request: true`
- **THEN** o servidor retorna APENAS o fragmento HTML dos cards de estatísticas e medalhas
- **THEN** o browser substitui o conteúdo anterior sem reload completo

#### Scenario: Fallback sem JavaScript
- **WHEN** JavaScript está desabilitado e usuário clica no link de período
- **THEN** o link funciona como navegação GET normal (`/geral?periodo=30d`)
- **THEN** a página é carregada por completo (comportamento atual)

#### Scenario: Animação de contadores reinicia no fragmento
- **WHEN** o fragmento de estatísticas é substituído via HTMX
- **THEN** os elementos com classe `animate-counter` disparam a animação do zero
- **THEN** o valor animado final corresponde ao valor correto retornado pelo servidor

### Requirement: Servidor diferencia requisição parcial de completa
O controller DEVE detectar o header `HX-Request` para decidir se retorna a página inteira ou apenas o fragmento.

#### Scenario: Requisição com HX-Request
- **WHEN** o endpoint `/geral` recebe GET com header `HX-Request: true`
- **THEN** o controller retorna apenas o fragmento `geral :: estatisticas`
- **THEN** a resposta NÃO contém layout, navbar, footer ou scripts

#### Scenario: Requisição sem HX-Request
- **WHEN** o endpoint `/geral` recebe GET sem header `HX-Request`
- **THEN** o controller retorna a página completa com layout (comportamento atual)
