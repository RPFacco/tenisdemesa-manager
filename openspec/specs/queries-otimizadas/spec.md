# queries-otimizadas Specification

## Purpose
TBD - created by archiving change otimizacao-performance. Update Purpose after archive.
## Requirements
### Requirement: Consulta de treinos por período no banco
O sistema MUST buscar treinos de um período específico usando query SQL em vez de filtrar em memória.

#### Scenario: Resumo diário usa query por data
- **WHEN** o resumo diário de treinos é solicitado
- **THEN** o banco MUST ser consultado com filtro `data = hoje` em vez de `findAll()` + stream filter

#### Scenario: Resumo semanal usa query entre datas
- **WHEN** o resumo semanal de treinos é solicitado
- **THEN** o banco MUST ser consultado com filtro `data BETWEEN inicioSemana AND fimSemana`

#### Scenario: Resumo mensal usa query entre datas
- **WHEN** o resumo mensal de treinos é solicitado
- **THEN** o banco MUST ser consultado com filtro `data BETWEEN inicioMes AND fimMes`

### Requirement: Consulta de partidas por período no banco
O sistema MUST buscar partidas de um período usando query SQL em vez de navegar por campeonatos em memória.

#### Scenario: Partidas no período via PartidaRepository
- **WHEN** estatísticas de vitórias/derrotas são calculadas para um período
- **THEN** a consulta MUST ser feita diretamente em `PartidaRepository` com filtro `data BETWEEN inicio AND fim`

#### Scenario: Sequência atual usa partidas ordenadas por data
- **WHEN** a sequência atual é calculada
- **THEN** a consulta MUST buscar partidas do período ordenadas por data descendente diretamente no banco

