## Why

A página `/geral` recarrega por completo ao alternar entre períodos (Ano, 30 dias, Todos). O cálculo das estatísticas é leve (COUNTs simples), mas o round-trip HTTP + re-renderização completa do layout Thymeleaf + re-parsing de CSS/JS pelo browser introduz delay desnecessário para um filtro que o usuário troca frequentemente.

## What Changes

- Substituir os links de período por botões que disparam requisições HTMX
- Extrair os cards de estatísticas em um fragmento Thymeleaf separado
- Adicionar dependência HTMX via CDN (ou bundle local)
- Manter toda lógica de cálculo no servidor (nenhuma duplicação em JS)
- O fragmento retornado substitui apenas a região dos cards, sem reload da página

## Capabilities

### New Capabilities
- `geral-period-filter`: Filtro por período na página geral usando HTMX para trocar apenas os cards de estatísticas sem recarregar o layout completo

### Modified Capabilities
(nenhuma)

## Impact

- **src/main/resources/templates/geral.html** — extrair cards para fragmento, adicionar atributos hx- nos botões
- **src/main/resources/templates/geral.html** — novo arquivo `geral :: estatisticas` fragmento
- **src/main/resources/templates/layout.html** — adicionar script HTMX (CDN)
- **src/main/java/.../controller/GeralController.java** — ajustar endpoint `/geral` para retornar fragmento quando chamado com header HX-Request
- **Dependência nova**: htmx.org (CDN via script tag)
