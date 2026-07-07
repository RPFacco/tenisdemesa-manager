## Why

O visual atual usa Bootstrap 5 com tema Flatly, que é funcional mas genérico — parece um "site blank" sem identidade. Queremos um design moderno e polido no estilo Lovable: com glassmorphism, animações, micro-interações, gradientes dinâmicos e modo escuro, para dar uma experiência premium à atleta Ana Clara.

## What Changes

- Substituir o tema Bootswatch Flatly por CSS customizado moderno
- Adicionar **glassmorphism** (efeito vidro) em cards e modais
- Implementar **animações de entrada** (fade-in, slide-up) nos elementos
- Adicionar **animated counters** nos números de estatísticas
- Criar **gradientes dinâmicos** sutis no background
- Adicionar **modo escuro** com toggle
- Cards com **hover 3D** (transform perspective)
- **Floating labels** animados nos formulários
- **Micro-interações** em botões, links e navegação
- Navbar com **efeito blur** ao scroll
- **Tipografia moderna** (inter ou similar) com melhor hierarquia
- Separar CSS inline do layout.html em arquivo CSS externo dedicado

## Capabilities

### New Capabilities
- `design-system`: Conjunto de tokens de design (cores, sombras, bordas, animações) e componentes CSS customizados que substituem o tema Flatly
- `dark-mode`: Alternância entre tema claro e escuro com persistência local
- `motion-animations`: Sistema de animações de entrada, micro-interações e animated counters

### Modified Capabilities
<!-- Nenhuma capability existente tem requisitos alterados — apenas estilo visual -->

## Impact

- **Frontend**: Todos os templates Thymeleaf serão atualizados com novas classes CSS
- **CSS**: O estilo inline no `layout.html` será movido para `static/css/app.css` e expandido
- **JS**: Novo arquivo `static/js/app.js` para animations, dark mode e interações
- **Dependências**: Nenhuma nova dependência externa (apenas CSS/JS puros)
- **Backend**: Nenhuma alteração no Java/Spring Boot
- **Acessibilidade**: Animações respeitarão `prefers-reduced-motion`
