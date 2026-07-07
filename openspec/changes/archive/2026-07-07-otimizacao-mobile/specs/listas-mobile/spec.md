## ADDED Requirements

### Requirement: Tabela de partidas adaptável
A listagem de partidas no detalhe do campeonato DEVE ser legível em mobile sem scroll horizontal excessivo.

#### Scenario: Partidas em card-list no mobile
- **WHEN** a tela tem largura < 768px
- **THEN** cada partida DEVE ser exibida como um card compacto com data, adversário, sets e resultado em linhas verticais, em vez de uma tabela com 7 colunas

#### Scenario: Partidas em tabela no desktop
- **WHEN** a tela tem largura >= 768px
- **THEN** as partidas DEVEM ser exibidas em tabela normal (como hoje)

#### Scenario: Ações por partida no mobile
- **WHEN** a tela tem largura < 768px
- **THEN** os botões de editar e excluir partida DEVEM estar visíveis e com touch target ≥ 44px

### Requirement: Tabela de medalhas adaptável
A listagem de medalhas DEVE seguir o mesmo padrão das partidas.

#### Scenario: Medalhas em card-list no mobile
- **WHEN** a tela tem largura < 768px
- **THEN** cada medalha DEVE ser exibida como um card com tipo, modalidade e ação de excluir

#### Scenario: Medalhas em tabela no desktop
- **WHEN** a tela tem largura >= 768px
- **THEN** as medalhas DEVEM ser exibidas em tabela normal

### Requirement: Lista de campeonatos adaptável
A lista de campeonatos já usa cards (col-md-6 col-lg-4), mas DEVE ser ajustada para mobile.

#### Scenario: Cards ocupam largura total no mobile
- **WHEN** a tela tem largura < 576px
- **THEN** cada card de campeonato DEVE ocupar 100% da largura (col-12)

### Requirement: Lista de treinos adaptável
A lista de treinos já usa cards, mas DEVE ser ajustada.

#### Scenario: Cards de treino em 1 coluna no mobile
- **WHEN** a tela tem largura < 576px
- **THEN** cada card de treino DEVE ocupar 100% da largura (col-12)
