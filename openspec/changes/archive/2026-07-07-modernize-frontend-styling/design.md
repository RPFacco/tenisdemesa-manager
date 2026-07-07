## Context

O projeto usa Spring Boot + Thymeleaf com Bootstrap 5 e tema Bootswatch Flatly. Todo o CSS customizado está inline no `layout.html`. Não há arquivo JS próprio. O frontend é server-side renderized, sem SPA.

O objetivo é transformar o visual de "Bootstrap genérico" para um design moderno estilo Lovable, sem adicionar dependências externas — apenas CSS e JS puros.

## Goals / Non-Goals

**Goals:**
- Design visual moderno com glassmorphism, gradientes e sombras refinadas
- Animações de entrada (fade-in, slide-up) com Intersection Observer
- Animated counters nas estatísticas (contagem progressiva ao carregar)
- Modo escuro com toggle e persistência em localStorage
- Micro-interações em hover/focus/active em todos os componentes
- Navbar com backdrop-filter blur ao scroll
- Floating labels animados nos formulários
- Tipografia com fonte Inter (Google Fonts)
- CSS modular em arquivo externo (`static/css/app.css`)
- JS modular em arquivo externo (`static/js/app.js`)
- Animações respeitam `prefers-reduced-motion`

**Non-Goals:**
- Alterar backend Java/Spring Boot
- Adicionar frameworks JS (React, Vue, Alpine, etc.)
- Modificar estrutura HTML dos templates (apenas classes CSS e wrappers)
- Alterar funcionalidades existentes

## Decisions

| Decisão | Opção Escolhida | Alternativa | Motivo |
|---|---|---|---|
| Fonte | Inter (Google Fonts) | System fonts, Poppins | Inter é moderna, legível, bem otimizada para web, mesma usada em designs estilo Lovable |
| Animações | CSS + Intersection Observer | AOS.js, GSAP | Zero dependências externas, controle total, performance |
| Modo escuro | CSS custom properties + localStorage | Bootstrap native dark mode | Mais leve, sem depender de versão específica do Bootstrap |
| Glassmorphism | CSS `backdrop-filter: blur()` com RGBA | Imagens SVG de fundo | Efeito nativo, performático, sem assets extras |
| Hover 3D | CSS `transform: perspective()` + `rotateX/Y` via JS | CSS puro :hover | Efeito mais suave e responsivo ao movimento do mouse |
| Animated counters | `requestAnimationFrame` | CSS `@property` animations | Suporte cross-browser, controle de easing |
| Organização CSS | Design tokens + componentes | BEM tradicional | Mais flexível para tema claro/escuro com custom properties |

## Risks / Trade-offs

- [Performance] `backdrop-filter: blur()` pode impactar dispositivos low-end → Fallback para fundo semi-sólido sem blur
- [Acessibilidade] Animações podem causar desconforto → `prefers-reduced-motion` desativa todas as animações
- [Manutenção] CSS customizado substitui Bootstrap theme → Manter compatibilidade com classes Bootstrap existentes
- [Browser] `backdrop-filter` não funciona no Firefox < 103 → Fallback graceful com opacity apenas
