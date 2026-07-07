## ADDED Requirements

### Requirement: Lista de últimas partidas
A página inicial SHALL exibir uma seção "Últimas Partidas" com as 5 partidas mais recentes ordenadas por data descendente.
Cada linha SHALL exibir: data formatada (dd/MM), nome do adversário, placar dos sets (ex: "3-1"), resultado (✅ Vitória / ❌ Derrota) e nome do campeonato.
Se não houver partidas cadastradas, a seção SHALL exibir "Nenhuma partida registrada" com um link para criar um campeonato.

#### Scenario: Partidas recentes existem
- **WHEN** há partidas cadastradas
- **THEN** as 5 mais recentes são exibidas com data, adversário, placar, resultado e campeonato

#### Scenario: Menos de 5 partidas
- **WHEN** há apenas 3 partidas cadastradas
- **THEN** apenas essas 3 são exibidas (sem linhas vazias)

#### Scenario: Nenhuma partida
- **WHEN** não há partidas no banco
- **THEN** a seção exibe "Nenhuma partida registrada"

### Requirement: Lista de últimos treinos
A página inicial SHALL exibir uma seção "Últimos Treinos" com os 5 treinos mais recentes ordenados por data descendente.
Cada linha SHALL exibir: data formatada (dd/MM), tipo do treino (Técnica / Físico / Jogo) e duração em minutos.
Se não houver treinos cadastrados, a seção SHALL exibir "Nenhum treino registrado" com um link para a página de treinos.

#### Scenario: Treinos recentes existem
- **WHEN** há treinos cadastrados
- **THEN** os 5 mais recentes são exibidos com data, tipo e duração

#### Scenario: Nenhum treino
- **WHEN** não há treinos no banco
- **THEN** a seção exibe "Nenhum treino registrado"

### Requirement: Medalhas totais
A página inicial SHALL exibir as medalhas totais (de todos os campeonatos) em formato compacto, abaixo do bloco de campeonato atual.
O formato SHALL ser uma linha de 3 cards pequenos (OURO, PRATA, BRONZE) com a contagem total de cada tipo.
Se não houver medalhas de um tipo, a contagem SHALL exibir 0.

#### Scenario: Medalhas existem
- **WHEN** há medalhas cadastradas
- **THEN** os 3 cards exibem a contagem total de OURO, PRATA e BRONZE

#### Scenario: Nenhuma medalha
- **WHEN** não há medalhas
- **THEN** todos os 3 cards exibem 0
