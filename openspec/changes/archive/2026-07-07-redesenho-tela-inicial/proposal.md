## Why

A tela inicial atual mostra apenas números soltos (vitórias, derrotas, medalhas) em cards sem hierarquia visual, sem contexto temporal e sem conteúdo dinâmico. O usuário precisa navegar por menus para ver atividades recentes ou saber qual campeonato está em andamento. A página não conta uma história — é só um painel de estáticas.

## What Changes

- Adicionar seletor de período (Ano atual / Últimos 30 dias / Todos os tempos) no topo da página
- Manter os 4 cards de desempenho (vitórias, derrotas, taxa, sequência) com valores filtrados pelo período selecionado
- Substituir a fileira de cards de medalha por um bloco de **Campeonato Atual** destacado, com nome, local, data e progresso de partidas
- Adicionar seção **Últimas Partidas** com as 5 partidas mais recentes (data, adversário, resultado, campeonato)
- Adicionar seção **Últimos Treinos** com os 3-5 treinos mais recentes (data, tipo, duração)
- Exibir medalhas (OURO/PRATA/BRONZE) em formato compacto ao lado do campeonato atual ou como linha auxiliar
- Unificar o estilo visual dos cards (remover a inconsistência entre gradient e elevated)

## Capabilities

### New Capabilities
- `estatisticas-periodo`: Estatísticas de desempenho (vitórias, derrotas, taxa, sequência) com suporte a filtro por período (ano, 30 dias, todos)
- `campeonato-atual`: Destaque do campeonato em andamento ou mais recente, com nome, local, datas e progresso de partidas
- `atividades-recentes`: Listagem das últimas partidas e últimos treinos na página inicial

### Modified Capabilities
- *(nenhuma — não existem specs anteriores para modificar)*

## Impact

- `GeralController.java`: novos atributos no model (`ultimasPartidas`, `ultimosTreinos`, `campeonatoAtual`, período) e parâmetro opcional de período
- `EstatisticaService.java`: métodos com filtro por período (ano/data)
- `CampeonatoService.java`: método para buscar campeonato atual ou mais recente
- `PartidaRepository.java`, `TreinoRepository.java`: queries ordenadas por data com limit
- `geral.html`: reestruturação completa do template com novas seções e seletor de período
- `layout.html`: possível ajuste mínimo de estilos
