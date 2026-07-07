## ADDED Requirements

### Requirement: Formulários adaptáveis
Todos os formulários DEVEM ser usáveis em telas de celular com campos largos e botões acessíveis.

#### Scenario: Campos ocupam 100% no mobile
- **WHEN** a tela tem largura < 768px
- **THEN** todos os campos de formulário DEVEM ocupar 100% da largura disponível

#### Scenario: Labels visíveis e alinhados
- **WHEN** qualquer formulário é exibido
- **THEN** os labels DEVEM estar acima dos campos (padrão Bootstrap), visíveis e com tamanho de fonte ≥ 14px

#### Scenario: Teclado não esconde campos
- **WHEN** o usuário toca em um campo de input em mobile
- **THEN** o campo DEVE estar visível sem ser obstruído pelo teclado virtual (usar viewport height e scroll automático)

### Requirement: Botões de formulário com touch target adequado
Botões de submit/cancelar em formulários DEVEM ter tamanho mínimo para toque.

#### Scenario: Botões com altura mínima de 44px
- **WHEN** um formulário exibe botões
- **THEN** cada botão DEVE ter no mínimo 44px de altura
