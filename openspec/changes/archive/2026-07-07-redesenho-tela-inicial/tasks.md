## 1. Repository Layer

- [x] 1.1 Adicionar `findTop5ByOrderByDataDesc()` em `PartidaRepository`
- [x] 1.2 Adicionar `findTop5ByOrderByDataDesc()` em `TreinoRepository`

## 2. Service Layer

- [x] 2.1 Adicionar em `EstatisticaService` métodos com filtro por `LocalDate` (início/fim): `totalVitorias(Periodo)`, `totalDerrotas(Periodo)`, `taxaVitoria(Periodo)`, `sequenciaAtual(Periodo)`
- [x] 2.2 Adicionar em `CampeonatoService` método `buscarAtual()` que retorna o campeonato em andamento (dataFim >= hoje) ou o mais recente, ou null se não houver nenhum
- [x] 2.3 Extrair em `CampeonatoService` método `calcularProgresso(campeonato)` que retorna (partidasRealizadas, totalPartidas)

## 3. Controller Layer

- [x] 3.1 Atualizar `GeralController.geral()` para aceitar parâmetro `@RequestParam(defaultValue = "ano") String periodo`
- [x] 3.2 Adicionar ao model: `ultimasPartidas` (do PartidaRepository), `ultimosTreinos` (do TreinoRepository), `campeonatoAtual` (do CampeonatoService), `periodo` selecionado, `progressoCampeonato`

## 4. Template (geral.html)

- [x] 4.1 Adicionar seletor de período (btn-group com 3 opções: Ano / 30 dias / Todos)
- [x] 4.2 Substituir cards de estatística para usar `card-elevated` (remover `card-gradient-*`) com valores condicionais ao período
- [x] 4.3 Adicionar bloco de Campeonato Atual com nome, local, datas, barra de progresso, medalhas e link "Ver detalhes"
- [x] 4.4 Adicionar seção Últimas Partidas com tabela/listagem das 5 mais recentes
- [x] 4.5 Adicionar seção Últimos Treinos com tabela/listagem dos 5 mais recentes
- [x] 4.6 Adicionar linha compacta de medalhas totais (OURO/PRATA/BRONZE)

## 5. Estilos

- [x] 5.1 Remover classes `card-gradient-*` não utilizadas do `<style>` em `layout.html` (após confirmar que não são usadas em outras páginas)
- [x] 5.2 Verificar responsividade da nova tela inicial em mobile
