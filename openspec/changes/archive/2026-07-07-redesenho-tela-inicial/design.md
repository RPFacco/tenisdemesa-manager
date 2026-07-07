## Context

A tela inicial (`/geral`) exibe 8 cards estáticos sem hierarquia, contexto temporal ou atividades recentes. As estatísticas são calculadas sobre todo o histórico e os cards usam dois estilos visuais diferentes (gradiente vs elevated). Não há seção de campeonato atual, partidas recentes ou treinos recentes — o usuário precisa navegar pelos menus para acessar essas informações.

O app é Spring Boot + Thymeleaf + Bootstrap 5 + Bootswatch Flatly, sem frameworks JS no frontend.

## Goals / Non-Goals

**Goals:**
- Adicionar seletor de período (ano, 30 dias, todos) que filtra os 4 cards de desempenho
- Destacar o campeonato atual (ou mais recente) em um bloco separado com informações e progresso
- Exibir as 5 partidas mais recentes com resultado e campeonato de origem
- Exibir os 3-5 treinos mais recentes com tipo e duração
- Unificar o estilo visual dos cards para card-elevated (sombra suave, sem gradiente)
- Manter compatibilidade com o layout existente (navbar, footer, fragment)

**Non-Goals:**
- Não alterar as páginas de campeonatos, treinos ou partidas (apenas a tela inicial)
- Não adicionar dependências JS (React, Vue, HTMX)
- Não implementar paginação ou infinit scroll nas listas
- Não modificar o modelo de dados (Campeonato, Partida, Treino, Medalha)

## Decisions

### Seletor de período: links com parâmetro GET
- Botões de período (Ano / 30 dias / Todos) no topo como links `<a>` ou botões de filtro
- Recarregam a página com `?periodo=2026`, `?periodo=30d` ou `?periodo=todos`
- Controller lê o parâmetro e filtra as estatísticas no backend
- Alternativa considerada: fetch JS + atualização parcial — descartada para manter simplicidade

### Definição de "campeonato atual"
- Buscar campeonatos com `dataFim >= hoje`, ordenar por `dataInicio DESC`, pegar o primeiro
- Se nenhum estiver em andamento, pegar o campeonato com `dataFim` mais recente
- Progresso: `(partidas com resultado definido) / (total de partidas)`

### Queries de atividades recentes
- Adicionar no `PartidaRepository`: `findTop5ByOrderByDataDesc()` — retorna List<Partida>
- Adicionar no `TreinoRepository`: `findTop5ByOrderByDataDesc()` — retorna List<Treino>
- As partidas já têm referência ao campeonato via `Partida.campeonato.nome`

### Unificação visual dos cards
- Remover classes `card-gradient-*` da página inicial
- Usar `card card-elevated text-center` para todos os cards de estatística
- Manter ícones e cores específicas (ex: `text-success` para vitórias) mas sem gradiente de fundo

### Estrutura do template (geral.html)
```
+-- Seletor de período (btn-group)
+-- Row 1: 4 cards de desempenho (col-md-3)
+-- Row 2: Campeonato Atual (col-12, card destacado)
    +-- nome, local, datas
    +-- barra de progresso (partidas realizadas / total)
    +-- medalhas do campeonato (ícones)
    +-- link "Ver detalhes →"
+-- Row 3: Medalhas totais (compacto, 3 cards)
+-- Row 4: Últimas Partidas (tabela ou lista, 5 linhas)
+-- Row 5: Últimos Treinos (tabela ou lista, 3-5 linhas)
```

## Risks / Trade-offs

- **Performance**: PartidaRepository e TreinoRepository não têm queries hoje. As queries `findTop5ByOrderByDataDesc()` são simples e eficientes com índice em `data`.
- **Campeonato atual heuristic**: Se o usuário não preenche `dataFim` corretamente, o campeonato errado pode aparecer como "atual". Solução: usar regra baseada em `dataFim >= hoje`, que é a informação mais objetiva disponível sem adicionar nova coluna.
- **Partidas sem campeonato associado**: Hoje toda partida pertence a um campeonato (FK), então não há risco de partidas órfãs.
