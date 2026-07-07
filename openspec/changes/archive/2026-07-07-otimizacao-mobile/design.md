## Context

O app usa Bootstrap 5.3 com Bootswatch Flatly. Todo o CSS customizado está inline no `layout.html`. O layout atual é desktop-first: navbar colapsa em `lg` (992px), tabelas são usadas para listar partidas/medalhas, botões de ação são `btn-sm`, e o botão "Novo" fica no topo da página. O site é usado principalmente no celular.

## Goals / Non-Goals

**Goals:**
- Layout mobile-first: tudo funciona bem em telas ≥ 320px
- Bottom nav fixa para navegação ergonômica com uma mão
- Touch targets ≥ 44px em todos os botões e links clicáveis
- Tabelas de partidas e medalhas viram cards touch-friendly em mobile
- FAB (Floating Action Button) para ações "Novo" em listas
- Aproveitar ao máximo Bootstrap 5.3 sem adicionar dependências

**Non-Goals:**
- Criar um PWA (por enquanto)
- Suporte offline
- Animações complexas ou gestos customizados (swipe, pull-to-refresh)
- Mudar o backend (controllers, services, models)

## Decisions

### 1. Bottom Nav em vez de Top Nav em mobile
**Decisão:** Adicionar uma bottom navigation fixa que aparece apenas em telas < 768px. A navbar superior permanece em desktop.

**Por quê:** Bottom nav é mais ergonômica em celular — o dedão alcança a parte inferior com facilidade. Estudo de padrões de UI mobile (iOS HIG, Material Design) recomenda bottom tabs para navegação primária.

**Alternativa considerada:** Manter só a navbar superior colapsável. Rejeitada porque o hamburger menu requer dois toques e não é tão rápido quanto bottom tabs.

### 2. Tabelas → Cards em mobile
**Decisão:** Usar `table-responsive` com um wrapper que em telas pequenas transforma cada linha em um card usando `d-md-table` / `d-md-none`.

**Por quê:** Sem dependências extras. Bootstrap já tem as utility classes. A mesma markup funciona nos dois breakpoints.

**Alternativa considerada:** Duas markups separadas (table + div) com show/hide. Mais manutenção.

### 3. FAB em vez de botão "Novo" no topo
**Decisão:** Botão "+" flutuante no canto inferior direito, visível apenas em mobile. Em desktop mantém o botão no topo.

**Por quê:** Posicionamento natural para o polegar, padrão consolidado em apps mobile.

**Alternativa considerada:** Botão fixo no topo. Rejeitado porque exige esticar o dedão.

### 4. CSS inline vs arquivo separado
**Decisão:** Manter CSS no `layout.html` por enquanto, adicionando os estilos mobile onde já estão os demais.

**Por quê:** O projeto já segue esse padrão. Extrair para CSS separado pode ser feito depois como refatoração separada.

### 5. Breakpoints
- `>= 768px` (md): layout desktop normal com top navbar
- `< 768px` (sm): bottom nav ativa, FAB aparece, tabelas viram cards
- `>= 992px` (lg): top navbar com links completos (já funciona)

## Risks / Trade-offs

| Risco | Mitigação |
|-------|-----------|
| Bottom nav duplica markup da navbar | Usar um `nav` só com `d-md-none` / `d-none d-md-block` |
| FAB pode sobrepor conteúdo no fim da página | Aplicar `mb-5` ou padding inferior no container |
| Touch targets de 44px podem quebrar layout em formulários densos | Manter `btn-sm` só onde o espaço é realmente crítico; usar `btn` ou `btn-md` como padrão |
| Manter CSS inline cresce o layout.html | Aceitável no curto prazo; refatorar depois se necessário |
