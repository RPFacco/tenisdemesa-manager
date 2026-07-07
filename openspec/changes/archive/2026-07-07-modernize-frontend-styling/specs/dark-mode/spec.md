## ADDED Requirements

### Requirement: Alternância claro/escuro
O sistema SHALL prover um botão toggle na navbar que alterna entre tema claro e escuro.

#### Scenario: Toggle modo escuro
- **WHEN** o usuário clica no botão de toggle do tema
- **THEN** todas as cores do sistema mudam para o tema escuro
- **WHEN** o usuário clica novamente
- **THEN** retorna ao tema claro

### Requirement: Persistência em localStorage
A preferência de tema SHALL ser salva em `localStorage` com chave `theme-preference` e aplicada automaticamente ao recarregar a página.

#### Scenario: Persistência ao recarregar
- **WHEN** o usuário seleciona modo escuro e recarrega a página
- **THEN** o modo escuro é aplicado automaticamente sem piscar (flash)
- **WHEN** o usuário limpa o localStorage
- **THEN** o tema volta ao padrão (claro)

### Requirement: Tema escuro com design tokens
O modo escuro SHALL ser implementado via seletor `[data-theme="dark"]` que sobrescreve os design tokens (CSS custom properties) com valores escuros.

#### Scenario: Tokens no modo escuro
- **WHEN** `data-theme="dark"` está no `<html>`
- **THEN** `--color-bg` é escuro, `--color-surface` é cinza escuro, `--color-text` é claro
- **THEN** todos os componentes que usam `var(--color-*)` refletem o tema escuro

### Requirement: Prevenir flash de tema incorreto
O sistema SHALL aplicar o tema escuro via um `<script>` inline no `<head>` (antes do CSS carregar) para evitar flash de tema claro.

#### Scenario: Sem flash ao recarregar
- **WHEN** a página carrega com tema escuro salvo
- **THEN** o tema escuro é aplicado antes da primeira renderização
- **THEN** não há flash de cores claras
