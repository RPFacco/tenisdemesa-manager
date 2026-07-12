## ADDED Requirements

### Requirement: Listagem de campeonatos ordenada por data de início
O sistema SHALL listar os campeonatos ordenados por `dataInicio` em ordem descendente (mais recente primeiro).

#### Scenario: Campeonatos exibidos do mais recente ao mais antigo
- **WHEN** o usuário acessa a página `/campeonatos`
- **THEN** os campeonatos SHALL ser exibidos com o mais recente (dataInicio maior) no topo

#### Scenario: Dois campeonatos com mesma data de início
- **WHEN** dois campeonatos possuem a mesma `dataInicio`
- **THEN** a ordenação SHALL usar `id` como critério de desempate (maior ID primeiro)
