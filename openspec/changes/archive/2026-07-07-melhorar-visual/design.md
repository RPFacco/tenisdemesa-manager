## Context

Site single-user Thymeleaf + Bootstrap 5.3 + Bootswatch Flatly. Atualmente sem CSS customizado — toda estilização é feita via classes Bootstrap padrão (bg-success, bg-danger, cards com border, tabelas table-hover). Não há footer, não há destaque de nav ativa, e as listagens são todas em tabelas HTML.

## Goals / Non-Goals

**Goals:**
- Substituir tabelas de listagem por cards visuais com sombra e elevacão no hover
- Aplicar gradientes suaves nos cards de estatística (substituindo bg-success/bg-danger sólidos)
- Adicionar footer fixo com créditos
- Destacar link ativo na navbar
- Adicionar transições CSS (0.3s ease) em cards e botões
- Refinar tipografia e espaçamentos
- CSS customizado inline no `<style>` dentro de layout.html (sem build step)

**Non-Goals:**
- Não adicionar novas funcionalidades ou páginas
- Não alterar controllers, services, models ou repositories
- Não adicionar dependências JS (Chart.js, etc.)
- Não implementar dark mode
- Não criar arquivos .css separados (mantido inline no layout.html para simplicidade)

## Decisions

1. **CSS inline no layout.html em vez de arquivo .css separado**
   - Justificativa: évita criar novo asset pipeline. O projeto não tem suporte a static resources build. CSS inline é suficiente para o escopo.
   - Alternativa descartada: criar `static/css/custom.css` — desnecessário para o volume de CSS.

2. **Gradientes via linear-gradient em vez de imagens**
   - Justificativa: performático, não requer assets, funciona com qualquer tema Bootswatch.
   - Paletas: verde (#00b894 → #00cec9), vermelho (#e17055 → #d63031), azul (#0984e3 → #74b9ff), amarelo (#fdcb6e → #e17055).

3. **Cards de listagem em vez de tabelas**
   - Campeonatos: cada campeonato vira um card com borda colorida pela categoria, nome, local, período e medalhas em destaque.
   - Treinos: cards menores em grid (3 colunas) em vez de linhas de tabela.
   - Justificativa: visualmente mais agradável e informativo que tabelas genéricas.

4. **Navbar ativa via Thymeleaf** comparando `#httpServletRequest.requestURI` com o link
   - Justificativa: não requer JS, resolved no server side.

5. **Footer fixo inferior** com `position: relative` (não fixed) para não sobrepor conteúdo em mobile.

## Risks / Trade-offs

- **Cards ocupam mais espaço vertical que tabelas** → Em desktop com telas largas, usar grid responsivo (col-md-4, col-lg-3) para manter densidade de informacão.
- **CSS inline polui o layout.html** → Volume estimado < 100 linhas, aceitável para o porte do projeto.
- **Transições CSS podem parecer lentas em dispositivos fracos** → Limitado a opacity/transform (não animar height/width).
