## ADDED Requirements

### Requirement: FAB (Floating Action Button)
O sistema DEVE exibir um botão de ação flutuante "Novo" nas páginas de lista em mobile.

#### Scenario: FAB aparece em listas no mobile
- **WHEN** o usuário está na página de listagem de campeonatos ou treinos
- **AND** a tela tem largura < 768px
- **THEN** um botão circular "+" DEVE aparecer no canto inferior direito, a 16px da borda

#### Scenario: FAB some no desktop
- **WHEN** a tela tem largura >= 768px
- **THEN** o FAB NÃO DEVE ser exibido (o botão "Novo" no topo já está visível)

#### Scenario: FAB navega para criação
- **WHEN** o usuário toca no FAB
- **THEN** o sistema DEVE navegar para a página de criação do recurso correspondente

#### Scenario: FAB não sobrepõe bottom nav
- **WHEN** o FAB está visível
- **THEN** ele DEVE ser posicionado acima da bottom navigation (bottom: ~80px)

### Requirement: Touch targets mínimos
Todos os elementos clicáveis DEVEM ter área de toque ≥ 44x44px.

#### Scenario: Botões de ação têm 44px de altura
- **WHEN** um botão ou link de ação é renderizado
- **THEN** sua altura DEVE ser no mínimo 44px

#### Scenario: Ícones clicáveis têm padding adequado
- **WHEN** um ícone (ex: lápis de editar, lixeira) é usado como botão
- **THEN** ele DEVE estar envolto em um elemento com padding que garanta 44x44px de área de toque

### Requirement: Stats com font-size adaptativo
O tamanho da fonte dos valores estatísticos DEVE ser reduzido em mobile.

#### Scenario: stat-value menor no mobile
- **WHEN** a tela tem largura < 576px
- **THEN** a classe .stat-value DEVE ter font-size de 1.8rem (em vez de 2.5rem)
