## ADDED Requirements

### Requirement: Bloco de campeonato atual
A página inicial SHALL exibir um bloco destacado com informações do campeonato atual.
O campeonato atual SHALL ser definido como:
1. O campeonato com `dataFim >= hoje`, ordenado por `dataInicio DESC`; ou
2. Se nenhum estiver em andamento, o campeonato com `dataFim` mais recente.
O bloco SHALL exibir: nome, local, data (início a fim) e categoria (se houver).
O bloco SHALL incluir um link "Ver detalhes →" que leva à página de detalhe do campeonato.
Se não houver nenhum campeonato cadastrado, o bloco SHALL exibir uma mensagem "Nenhum campeonato registrado" com um link para criar um novo.

#### Scenario: Campeonato em andamento
- **WHEN** existe um campeonato com `dataFim >= hoje`
- **THEN** o bloco exibe seu nome, local, data, categoria e um link para detalhes

#### Scenario: Nenhum campeonato em andamento
- **WHEN** todos os campeonatos têm `dataFim < hoje`
- **THEN** o bloco exibe o campeonato mais recente (último a terminar)

#### Scenario: Nenhum campeonato cadastrado
- **WHEN** não há campeonatos no banco
- **THEN** o bloco exibe "Nenhum campeonato registrado" com link para criar

### Requirement: Barra de progresso do campeonato
O bloco do campeonato atual SHALL exibir uma barra de progresso indicando quantas partidas já foram realizadas (com resultado definido) em relação ao total de partidas do campeonato.
Se o campeonato não tiver partidas, a barra SHALL exibir 0%.

#### Scenario: Progresso parcial
- **WHEN** o campeonato tem 4 partidas, das quais 2 têm resultado
- **THEN** a barra exibe "2 de 4 partidas" com 50% preenchido

#### Scenario: Campeonato sem partidas
- **WHEN** o campeonato não tem partidas cadastradas
- **THEN** a barra exibe "0 de 0 partidas" com 0% preenchido

### Requirement: Medalhas no campeonato atual
O bloco do campeonato atual SHALL exibir as medalhas conquistadas naquele campeonato, com ícone e tipo (OURO/PRATA/BRONZE).
Se o campeonato não tiver medalhas, nenhuma medalha SHALL ser exibida.

#### Scenario: Campeonato com medalhas
- **WHEN** o campeonato atual tem medalhas
- **THEN** elas são exibidas com ícone e tipo abaixo do nome do campeonato

#### Scenario: Campeonato sem medalhas
- **WHEN** o campeonato atual não tem medalhas
- **THEN** nenhuma medalha é exibida no bloco
