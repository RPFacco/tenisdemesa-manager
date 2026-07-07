## ADDED Requirements

### Requirement: Seletor de período
A página inicial SHALL exibir um seletor de período com as opções "Ano atual", "Últimos 30 dias" e "Todos os tempos".
O seletor SHALL ser implementado como links/botões que recarregam a página com o parâmetro `?periodo=`.
O valor padrão SHALL ser "Ano atual" quando nenhum parâmetro for informado.

#### Scenario: Selecionar período "Ano atual"
- **WHEN** o usuário clica em "Ano atual"
- **THEN** a página recarrega com `?periodo=2026` e os cards de desempenho mostram valores filtrados para o ano corrente

#### Scenario: Selecionar período "Últimos 30 dias"
- **WHEN** o usuário clica em "Últimos 30 dias"
- **THEN** a página recarrega com `?periodo=30d` e os cards mostram valores dos últimos 30 dias

#### Scenario: Selecionar período "Todos os tempos"
- **WHEN** o usuário clica em "Todos os tempos"
- **THEN** a página recarrega com `?periodo=todos` e os cards mostram o histórico completo

#### Scenario: Botão ativo destacado
- **WHEN** a página carrega com um período selecionado
- **THEN** o botão correspondente deve aparecer visualmente ativo (ex: `btn-primary` vs `btn-outline-primary`)

### Requirement: Cards de desempenho filtrados por período
O sistema SHALL calcular e exibir os valores de Vitórias, Derrotas, Taxa de Vitória e Sequência Atual filtrados pelo período selecionado.
O cálculo SHALL considerar apenas partidas cuja data esteja dentro do período.
Quando o período for "Todos os tempos" ou não houver parâmetro, o cálculo SHALL considerar todo o histórico.
Quando não houver partidas no período, Vitórias e Derrotas SHALL exibir 0 e Taxa/Sequência SHALL exibir "—".
Os cards SHALL usar o estilo `card card-elevated` (sem gradiente).

#### Scenario: Período com partidas
- **WHEN** o período selecionado contém partidas registradas
- **THEN** os cards exibem os valores calculados corretamente (ex: "5" em Vitórias)

#### Scenario: Período sem partidas
- **WHEN** o período selecionado não contém partidas
- **THEN** Vitórias exibe "0", Derrotas exibe "0", Taxa exibe "—", Sequência exibe "—"

### Requirement: Unificação visual dos cards
Todos os cards de estatística na página inicial SHALL usar o mesmo estilo visual (`card card-elevated`), substituindo os estilos `card-gradient-*` atuais.

#### Scenario: Cards sem gradiente
- **WHEN** a página inicial carrega
- **THEN** nenhum card de estatística possui gradiente de fundo; todos usam `card-elevated`
