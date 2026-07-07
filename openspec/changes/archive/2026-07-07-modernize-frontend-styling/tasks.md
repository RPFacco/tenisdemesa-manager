## 1. Design Tokens e CSS Base

- [x] 1.1 Criar `static/css/app.css` com design tokens (CSS custom properties) para claro e escuro
- [x] 1.2 Importar Google Fonts (Inter) via `@import` no `app.css`
- [x] 1.3 Definir estilos base: body, tipografia, cores de fundo, links
- [x] 1.4 Adicionar gradiente de fundo animado (slow-shift)

## 2. Glassmorphism e Cards

- [x] 2.1 Criar classe `.card-glass` com backdrop-filter, border RGBA e fallback
- [x] 2.2 Adaptar classes `.card-gradient-*` existentes para usar os tokens
- [x] 2.3 Criar efeito hover 3D (`.card-3d`) com perspective e JS para tracking do mouse
- [x] 2.4 Refinar `.card-elevated` com sombras e bordas arredondadas usando tokens

## 3. Modo Escuro

- [x] 3.1 Adicionar `<script>` inline no `<head>` do `layout.html` para prevenir flash
- [x] 3.2 Implementar `[data-theme="dark"]` com valores escuros dos tokens
- [x] 3.3 Adicionar botão toggle na navbar (ícone lua/sol)
- [x] 3.4 Implementar JS para alternar tema e persistir em localStorage
- [x] 3.5 Garantir que bottom-nav e footer também reajam ao tema

## 4. Animações e Micro-interações

- [x] 4.1 Criar `static/js/app.js` com Intersection Observer para `.animate-on-scroll`
- [x] 4.2 Implementar animated counters (`.animate-counter`) com requestAnimationFrame
- [x] 4.3 Adicionar staggered animation (`.stagger-children`)
- [x] 4.4 Implementar micro-interações em botões (hover scale, active shrink)
- [x] 4.5 Navbar com backdrop-filter blur ao scroll (JS listener)
- [x] 4.6 Respeitar `prefers-reduced-motion` em todas as animações

## 5. Formulários Minimalistas

- [x] 5.1 Adicionar floating labels animados nos forms (campeonato, partida, treino, medalha)
- [x] 5.2 Estilizar inputs, selects e textarea com design consistente
- [x] 5.3 Garantir que labels flutuantes funcionem com valores preenchidos (edição)

## 6. Aplicar nos Templates

- [x] 6.1 Remover CSS inline do `layout.html` e referenciar `app.css`
- [x] 6.2 Adicionar `app.js` e script de tema no `layout.html`
- [x] 6.3 Atualizar `geral.html` com classes `.animate-counter` e `.animate-on-scroll`
- [x] 6.4 Atualizar `campeonatos/lista.html` com `.stagger-children` e `.card-3d`
- [x] 6.5 Atualizar `campeonatos/detalhe.html` com `.card-glass` e `.animate-counter`
- [x] 6.6 Atualizar `campeonatos/form.html` com floating labels
- [x] 6.7 Atualizar `partidas/form.html` com floating labels
- [x] 6.8 Atualizar `treinos/lista.html` com `.animate-on-scroll` e `.card-glass`
- [x] 6.9 Atualizar `treinos/form.html` com floating labels
- [x] 6.10 Atualizar `medalhas/form.html` com floating labels
