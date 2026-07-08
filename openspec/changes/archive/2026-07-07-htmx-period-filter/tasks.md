## 1. Setup — Dependência HTMX

- [x] 1.1 Adicionar script HTMX via CDN (ou local) no `layout.html`

## 2. Fragmento Thymeleaf

- [x] 2.1 Extrair `<div class="row stagger-children">...</div>` (cards de estatísticas) para fragmento `geral.html :: estatisticas-cards`
- [x] 2.2 Extrair `<div class="row mb-4">...</div>` (medalhas) para fragmento `geral.html :: medalhas`
- [x] 2.3 Extrair `<div th:if/unless="${campeonatoAtual}">...</div>` (campeonato atual) para fragmento `geral.html :: campeonato-atual`
- [x] 2.4 Substituir as regiões extraídas por `th:replace` com os fragmentos, mantendo a página completa funcional para o primeiro GET

## 3. Controller — Detecção HX-Request

- [x] 3.1 Em `GeralController.geral()`, verificar header `HX-Request` via `@RequestHeader(value = "HX-Request", required = false) Boolean hxRequest`
- [x] 3.2 Quando `hxRequest` for true, retornar apenas a view do fragmento (`"geral :: estatisticas"` combinado)
- [x] 3.3 Garantir que todos os atributos de modelo necessários ainda sejam populados (totalVitorias, totalDerrotas, taxaVitoria, sequenciaAtual, medalhasPorTipo, etc.)

## 4. Botões de Período — Atributos HTMX

- [x] 4.1 Substituir os links de período por `<a hx-get="/geral?periodo=X" hx-target="#estatisticas-container" hx-swap="outerHTML">`
- [x] 4.2 Manter o `href` original para fallback sem JS (graceful degradation)
- [x] 4.3 Garantir que o botão ativo (classe `btn-primary`) seja atualizado corretamente no fragmento retornado

## 5. Pós-swap — Reinicialização de Animations

- [x] 5.1 No `app.js`, escutar evento `htmx:afterSwap` para reinicializar `initAnimatedCounters()`, `initStaggeredAnimations()`, `initCard3D()` e `initScrollAnimations()` nos novos elementos
- [x] 5.2 Remover o observer anterior se os cards substituídos estavam sendo observados pelo IntersectionObserver
