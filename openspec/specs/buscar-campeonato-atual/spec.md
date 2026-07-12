# buscar-campeonato-atual Specification

## Purpose
Define a lógica de prioridade para selecionar o campeonato atual exibido na página Geral.

## Requirements

### Requirement: Buscar campeonato atual com prioridade
O sistema SHALL retornar o campeonato atual seguindo a prioridade:
1. Campeonato em andamento (dataInicio ≤ hoje ≤ dataFim)
2. Próximo campeonato futuro (dataInicio > hoje, o mais próximo)
3. Campeonato mais recente do passado (dataInicio ≤ hoje, o mais recente)

#### Scenario: Há campeonato em andamento
- **WHEN** existe campeonato onde `dataInicio ≤ hoje ≤ dataFim`
- **THEN** o sistema SHALL retornar esse campeonato

#### Scenario: Há múltiplos campeonatos em andamento
- **WHEN** existem vários campeonatos em andamento
- **THEN** o sistema SHALL retornar aquele com `dataInicio` mais recente

#### Scenario: Não há campeonato em andamento, há futuros
- **WHEN** não existe campeonato em andamento e há campeonatos futuros
- **THEN** o sistema SHALL retornar o próximo campeonato futuro (com `dataInicio` mais próximo de hoje)

#### Scenario: Não há campeonato em andamento nem futuro
- **WHEN** não existem campeonatos em andamento nem futuros
- **THEN** o sistema SHALL retornar o campeonato mais recente do passado (com `dataInicio` mais próximo de hoje)

#### Scenario: Não há campeonatos cadastrados
- **WHEN** não existem campeonatos no sistema
- **THEN** o sistema SHALL retornar null
