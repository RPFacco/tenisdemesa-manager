## ADDED Requirements

### Requirement: Login obrigatório para todas as rotas
O sistema SHALL exigir autenticação para acessar qualquer rota da aplicação, exceto recursos estáticos, actuator e a página de login.

#### Scenario: Usuário não autenticado tenta acessar rota protegida
- **WHEN** um usuário não autenticado acessa qualquer rota (ex: `/campeonatos`, `/treinos`, `/geral`)
- **THEN** o sistema SHALL redirecionar para `/login` com parâmetro `returnUrl`

#### Scenario: Usuário autenticado acessa rota protegida
- **WHEN** um usuário autenticado acessa qualquer rota
- **THEN** o sistema SHALL permitir o acesso normalmente

### Requirement: Recursos estáticos e actuator acessíveis sem login
O sistema SHALL permitir acesso sem autenticação a CSS, JavaScript, imagens e endpoints do actuator.

#### Scenario: Acesso a CSS sem autenticação
- **WHEN** um request é feito para `/css/**`
- **THEN** o sistema SHALL retornar o arquivo sem exigir login

#### Scenario: Acesso a JavaScript sem autenticação
- **WHEN** um request é feito para `/js/**`
- **THEN** o sistema SHALL retornar o arquivo sem exigir login

#### Scenario: Acesso a actuator health sem autenticação
- **WHEN** um request é feito para `/actuator/health`
- **THEN** o sistema SHALL retornar o status de saúde sem exigir login

### Requirement: Form login com remember me
O sistema SHALL fornecer formulário de login com opção de "lembrar-me" por 30 dias.

#### Scenario: Login bem sucedido sem remember me
- **WHEN** o usuário faz login com credenciais válidas sem marcar "lembrar-me"
- **THEN** o sistema SHALL autenticar e criar sessão de navegador (expira ao fechar)

#### Scenario: Login bem sucedido com remember me
- **WHEN** o usuário faz login com credenciais válidas marcando "lembrar-me"
- **THEN** o sistema SHALL autenticar e criar cookie persistente por 30 dias

#### Scenario: Login com credenciais inválidas
- **WHEN** o usuário tenta login com senha incorreta
- **THEN** o sistema SHALL retornar mensagem de erro e permanecer na página de login

### Requirement: Logout disponível
O sistema SHALL permitir que o usuário faça logout, encerrando a sessão e o cookie remember me.

#### Scenario: Logout com sessão ativa
- **WHEN** o usuário autenticado acessa `/logout`
- **THEN** o sistema SHALL encerrar a sessão, limpar cookie remember me e redirecionar para `/login`

### Requirement: Tratamento de requests HTMX não autenticados
O sistema SHALL retornar status HTTP 401 para requests HTMX quando a sessão expirou, em vez de redirecionar para a página de login.

#### Scenario: Request HTMX com sessão expirada
- **WHEN** um request com header `HX-Request: true` é feito sem sessão válida
- **THEN** o sistema SHALL retornar status HTTP 401 (Unauthorized)

#### Scenario: Request HTMX com sessão válida
- **WHEN** um request com header `HX-Request: true` é feito com sessão válida
- **THEN** o sistema SHALL retornar a resposta normalmente (fragmento HTML)

### Requirement: Credenciais via variáveis de ambiente
O sistema SHALL ler usuário e senha de variáveis de ambiente, sem hardcode no código fonte.

#### Scenario: Variáveis de ambiente definidas
- **WHEN** `APP_LOGIN_USERNAME` e `APP_LOGIN_PASSWORD` estão definidas
- **THEN** o sistema SHALL criar o usuário com essas credenciais ao iniciar

#### Scenario: Variáveis de ambiente não definidas
- **WHEN** `APP_LOGIN_USERNAME` ou `APP_LOGIN_PASSWORD` não estão definidas
- **THEN** o sistema SHALL falhar na inicialização com erro claro
