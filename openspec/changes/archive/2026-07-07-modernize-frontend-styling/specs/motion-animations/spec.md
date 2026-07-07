## ADDED Requirements

### Requirement: Animações de entrada com Intersection Observer
Elementos com classe `.animate-on-scroll` SHALL animar ao entrar no viewport usando Intersection Observer, com fade-in e slide-up.

#### Scenario: Fade-in ao scroll
- **WHEN** um elemento com `.animate-on-scroll` entra no viewport
- **THEN** ele anima de `opacity: 0` e `translateY(20px)` para `opacity: 1` e `translateY(0)` em 600ms
- **WHEN** o elemento já está visível ao carregar
- **THEN** a animação ocorre imediatamente (sem atraso)

### Requirement: Animated counters em estatísticas
Números exibidos em elementos com classe `.animate-counter` SHALL contar de 0 até o valor final com easing suave usando `requestAnimationFrame`.

#### Scenario: Contador animado
- **WHEN** a página carrega e um elemento `.animate-counter` está visível
- **THEN** o número incrementa de 0 até o valor final em ~1.5 segundos com easing ease-out
- **WHEN** `prefers-reduced-motion` está ativo
- **THEN** o número aparece instantaneamente sem animação

### Requirement: Micro-interações em botões
Botões SHALL ter efeito de escala (`scale: 0.97`) no `:active` e elevação sutil (`translateY(-2px)`) no `:hover`.

#### Scenario: Hover e active em botões
- **WHEN** o usuário passa o mouse sobre um botão
- **THEN** o botão eleva 2px com sombra aumentada
- **WHEN** o usuário clica no botão
- **THEN** o botão escala para 0.97 instantaneamente

### Requirement: Navbar com blur ao scroll
A navbar SHALL ter `backdrop-filter: blur(10px)` quando o scroll vertical for maior que 50px.

#### Scenario: Navbar com blur
- **WHEN** o usuário rola a página > 50px
- **THEN** a navbar fica com fundo semi-transparente e efeito blur
- **WHEN** o usuário volta ao topo (< 50px)
- **THEN** a navbar retorna ao fundo sólido original

### Requirement: Staggered animation em grids
Filhos diretos de um container com classe `.stagger-children` SHALL animar em sequência com delay progressivo (cada filho 100ms após o anterior).

#### Scenario: Stagger em cards
- **WHEN** uma grid de cards tem classe `.stagger-children`
- **THEN** cada card anima em sequência com delay de 100ms entre eles

### Requirement: Respeitar prefers-reduced-motion
Todas as animações SHALL ser desabilitadas quando `prefers-reduced-motion: reduce` estiver ativo, usando `@media (prefers-reduced-motion: reduce)`.

#### Scenario: reduced-motion ativo
- **WHEN** o sistema do usuário tem `prefers-reduced-motion: reduce`
- **THEN** nenhuma animação de entrada, counter ou micro-interação é executada
- **THEN** todos os elementos aparecem instantaneamente no estado final
