## ADDED Requirements

### Requirement: Design tokens via CSS custom properties
O sistema SHALL definir um conjunto de design tokens como CSS custom properties no `:root`, incluindo:
- `--color-primary`, `--color-primary-light`, `--color-primary-dark`
- `--color-success`, `--color-danger`, `--color-warning`, `--color-info`
- `--color-bg` (fundo principal), `--color-surface` (cards/containers)
- `--color-text`, `--color-text-muted`
- `--color-border`
- `--shadow-sm`, `--shadow-md`, `--shadow-lg`
- `--radius-sm`, `--radius-md`, `--radius-lg`
- `--font-sans` (Inter)
- `--transition-fast`, `--transition-normal`

#### Scenario: Tokens disponíveis globalmente
- **WHEN** qualquer elemento CSS referencia `var(--color-primary)`
- **THEN** o valor é resolvido corretamente em todos os navegadores modernos

### Requirement: Glassmorphism em cards
Os cards principais (estatísticas, detalhes) SHALL suportar classe `.card-glass` com:
- Fundo `rgba(255,255,255,0.1)` (claro) / `rgba(0,0,0,0.2)` (escuro)
- `backdrop-filter: blur(12px)`
- Borda sutil `rgba(255,255,255,0.2)`
- Fallback sem blur para Firefox < 103

#### Scenario: Card com glassmorphism
- **WHEN** um card possui classe `card-glass`
- **THEN** ele exibe efeito vidro com blur no fundo

### Requirement: Gradientes de fundo
O fundo da página SHALL ter um gradiente suave e sutil animado (slow-shift), usando as cores primárias com baixa opacidade.

#### Scenario: Background gradiente visível
- **WHEN** a página carrega
- **THEN** o fundo exibe um gradiente suave que muda lentamente

### Requirement: Tipografia com Inter
A fonte Inter (Google Fonts) SHALL ser carregada via `@import` e aplicada como `--font-sans` padrão em todo o sistema.

#### Scenario: Fonte Inter carregada
- **WHEN** a página renderiza
- **THEN** o `font-family` padrão é Inter, com fallbacks sans-serif

### Requirement: Hover 3D em cards
Cards com classe `.card-3d` SHALL ter efeito de inclinação 3D ao mover o mouse (transform: perspective() rotateX/Y), com transição suave e reset ao sair.

#### Scenario: Hover 3D em card
- **WHEN** o usuário move o mouse sobre um card com `.card-3d`
- **THEN** o card inclina sutilmente na direção do cursor
- **WHEN** o mouse sai do card
- **THEN** o card retorna à posição original suavemente
