## 1. Assets Locais

- [x] 1.1 Baixar `bootstrap@5.3.3/dist/css/bootstrap.min.css` e `bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js` para `src/main/resources/static/css/` e `static/js/`
- [x] 1.2 Baixar `bootswatch@5.3.3/dist/flatly/bootstrap.min.css` para `src/main/resources/static/css/bootswatch-flatly.min.css`
- [x] 1.3 Baixar `bootstrap-icons@1.11.3/font/bootstrap-icons.min.css` e a pasta `fonts/` para `src/main/resources/static/css/` e `static/fonts/`
- [x] 1.4 Atualizar `layout.html`: trocar hrefs das CDNs pelos caminhos locais

## 2. Cache do Thymeleaf

- [x] 2.1 Adicionar `spring.thymeleaf.cache=true` em `application.properties`

## 3. Queries Otimizadas — TreinoRepository

- [x] 3.1 Adicionar `findByDataBetween(LocalDate inicio, LocalDate fim)` em `TreinoRepository`
- [x] 3.2 Adicionar `findByData(LocalDate data)` em `TreinoRepository`
- [x] 3.3 Refatorar `TreinoService.resumoDiario()` para usar `findByData()`
- [x] 3.4 Refatorar `TreinoService.resumoSemanal()` para usar `findByDataBetween()`
- [x] 3.5 Refatorar `TreinoService.resumoMensal()` para usar `findByDataBetween()`
- [x] 3.6 `listar()` ainda usado pelo controller — mantido

## 4. Queries Otimizadas — PartidaRepository e EstatisticaService

- [x] 4.1 Adicionar `findByDataBetween(LocalDate inicio, LocalDate fim)` em `PartidaRepository`
- [x] 4.2 Adicionar `findByDataBetweenOrderByDataDesc(LocalDate inicio, LocalDate fim)` em `PartidaRepository`
- [x] 4.3 Refatorar `EstatisticaService` para injetar `PartidaRepository` e usar as queries diretas
- [x] 4.4 Remover `todasPartidas()`; `partidasNoPeriodo()` simplificada para usar `PartidaRepository`

## 5. Verificação

- [ ] 5.1 Testar carregamento das páginas sem erros no console do navegador
- [ ] 5.2 Verificar que assets locais estão sendo servidos (network tab — sem requests a jsdelivr.net)
- [ ] 5.3 Verificar que resumos de treinos e estatísticas continuam funcionando
