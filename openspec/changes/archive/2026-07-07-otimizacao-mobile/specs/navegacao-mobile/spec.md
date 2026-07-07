## ADDED Requirements

### Requirement: Bottom navigation em mobile
O sistema DEVE exibir uma barra de navegação fixa na parte inferior da tela em dispositivos móveis.

#### Scenario: Bottom nav aparece em telas < 768px
- **WHEN** a tela tem largura < 768px
- **THEN** uma bottom navigation fixa DEVE ser exibida com os 3 links: Campeonatos, Treino, Geral

#### Scenario: Bottom nav some em telas >= 768px
- **WHEN** a tela tem largura >= 768px
- **THEN** a bottom navigation DEVE estar oculta

#### Scenario: Ícone ativo destacado
- **WHEN** o usuário está em uma rota (ex: /campeonatos)
- **THEN** o ícone correspondente na bottom nav DEVE estar visualmente ativo (cor primária + escala)

#### Scenario: Top navbar some em mobile
- **WHEN** a tela tem largura < 768px
- **THEN** a navbar superior DEVE ficar oculta (d-none em mobile, d-md-block em desktop)

### Requirement: Transição suave entre rotas
A bottom nav DEVE fornecer feedback visual ao toque.

#### Scenario: Feedback ao tocar
- **WHEN** o usuário toca em um item da bottom nav
- **THEN** o item DEVE ter um efeito visual de feedback (escala ou cor) com transição suave de 200ms
