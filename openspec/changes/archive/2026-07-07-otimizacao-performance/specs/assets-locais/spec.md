## ADDED Requirements

### Requirement: Bootstrap CSS servido localmente
O sistema MUST servir o Bootstrap 5.3.3 CSS a partir dos assets locais em vez de CDN.

#### Scenario: Carregamento sem requisição externa
- **WHEN** qualquer página é carregada
- **THEN** o href do Bootstrap CSS MUST apontar para `/css/bootstrap.min.css` (local)
- **AND** MUST NOT load from `cdn.jsdelivr.net`

### Requirement: Bootswatch Flatly servido localmente
O tema Bootswatch Flatly MUST ser servido localmente.

#### Scenario: Tema carregado do diretório local
- **WHEN** qualquer página é carregada
- **THEN** o href do tema MUST apontar para `/css/bootswatch-flatly.min.css` (local)

### Requirement: Bootstrap Icons servido localmente
Os ícones do Bootstrap Icons MUST ser servidos localmente.

#### Scenario: Ícones carregados localmente
- **WHEN** qualquer página é carregada
- **THEN** o href do Bootstrap Icons CSS MUST apontar para `/css/bootstrap-icons.min.css` (local)
- **AND** os arquivos de fonte (`fonts/`) MUST estar no diretório estático

### Requirement: Bootstrap JS servido localmente
O bundle JS do Bootstrap MUST ser servido localmente.

#### Scenario: Script carregado localmente
- **WHEN** qualquer página é carregada
- **THEN** o src do script MUST apontar para `/js/bootstrap.bundle.min.js` (local)
