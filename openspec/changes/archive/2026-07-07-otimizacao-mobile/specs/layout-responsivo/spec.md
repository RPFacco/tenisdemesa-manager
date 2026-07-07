## ADDED Requirements

### Requirement: Layout adaptável por breakpoint
O layout base DEVE se adaptar aos breakpoints do Bootstrap 5.3, priorizando mobile-first.

#### Scenario: Container ocupa 100% em mobile
- **WHEN** a tela tem largura < 768px
- **THEN** o container principal DEVE ocupar 100% da largura com padding lateral de 12px

#### Scenario: Navbar colapsa em md
- **WHEN** a tela tem largura < 768px
- **THEN** a navbar superior DEVE colapsar (navbar-expand-md)

#### Scenario: Padding inferior evita sobreposição com bottom nav
- **WHEN** a tela tem largura < 768px
- **THEN** o content-wrapper DEVE ter padding-bottom suficiente para não sobrepor a bottom nav fixa

### Requirement: Estatísticas adaptativas na página geral
Os cards de estatísticas (vitórias, derrotas, taxa, sequência) DEVEM se reorganizar em mobile.

#### Scenario: Stats em 2 colunas no mobile
- **WHEN** a tela tem largura < 576px
- **THEN** os 4 cards de estatísticas DEVEM ser exibidos em 2 colunas (col-6)

#### Scenario: Stats em 4 colunas no desktop
- **WHEN** a tela tem largura >= 768px
- **THEN** os 4 cards de estatísticas DEVEM ser exibidos em 4 colunas (col-md-3)
