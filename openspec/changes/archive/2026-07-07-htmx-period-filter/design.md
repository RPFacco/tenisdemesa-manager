## Context

Atualmente, o filtro de período na página `/geral` funciona via links GET com reload completo:

```
[Ano] → GET /geral?periodo=ano → renderiza layout.html + geral.html → browser reparseia tudo
```

O cálculo das estatísticas é rápido (COUNTs SQL + `findTop5`), então o gargalo está no transporte/renderização. HTMX permite que o servidor retorne **apenas o HTML da região que muda** (os cards), eliminando o overhead de layout, navbar, footer e re-parsing CSS/JS.

## Goals / Non-Goals

**Goals:**
- Eliminar o reload completo ao trocar o filtro de período
- Manter toda a lógica de cálculo no servidor (zero duplicação em JS)
- Mínimo de código novo (HTMX é uma lib de ~14KB minificada)
- Compatibilidade com `prefers-reduced-motion`

**Non-Goals:**
- Não substituir o Thymeleaf por SPA ou framework JS
- Não criar endpoint REST separado (usaremos detecção `HX-Request`)
- Não adicionar caching server-side (dados pequenos demais para valer a pena)

## Decisions

### 1. HTMX via CDN (script tag) em vez de npm/bundle
HTMX é uma dependência de frontend pura. Colocar no `layout.html` via `<script src="https://unpkg.com/htmx.org@2"></script>` é simples e não exige toolchain JS. Se no futuro houver necessidade de bundle, migra-se para npm.

### 2. Fragmento Thymeleaf reutilizável
Extrair `<div class="row stagger-children">...</div>` (cards de estatísticas) e `<div class="row mb-4">...</div>` (medalhas) para `geral.html :: estatisticas` fragmento.

Isolamento claro: o controller decide se retorna página completa ou fragmento baseado no header `HX-Request`.

### 3. Botões com `hx-get` em vez de links comuns
Os atuais `<a>` com `href` serão substituídos por `<a hx-get="/geral?periodo=X" hx-target="#estatisticas-cards" hx-swap="outerHTML">`. Mantêm fallback via `href` para graceful degradation se JS estiver desabilitado.

Alternativa considerada: fetch + JS vanilla. Rejeitada porque exigiria manipulação manual do DOM e serialização de HTML, exatamente o que HTMX abstrai.

## Risks / Trade-offs

| Risco | Mitigação |
|-------|-----------|
| CDN pode ficar indisponível | Manter `href` normal nos links como fallback; considerar download local do htmx.min.js |
| Animação de scroll/resize com `hx-swap` causa flicker | Usar `hx-swap="outerHTML"` + `hx-target` específico; os elementos já têm classes de animação via IntersectionObserver que reagem ao novo DOM |
| Perda dos event listeners JS nos cards substituídos | Os listeners atuais (`animate-counter`, `card-3d`, `stagger-children`) disparam no `DOMContentLoaded` e/ou via IntersectionObserver — o novo conteúdo dispara `htmx:afterSwap` que pode ser usado para reinicializar |
| `animate-counter` zera ao recarregar fragmento | Aceitável: é parte da experiência visual, e o número correto aparece ao final da animação |
