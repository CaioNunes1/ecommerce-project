# README — Backend (Spring Boot)

## Visão geral

API REST em Spring Boot (versão 4.x) com JPA/Hibernate, suporte a H2 (dev) e Postgres (prod/dev via Docker). Endpoints principais: CRUD de usuários, produtos, pedidos; autenticação básica usando Spring Security (HTTP Basic) com `PasswordEncoder` (BCrypt). Possui separação de roles (ROLE_USER, ROLE_ADMIN).

---

## Requisitos

* Java 17
* Maven
* Docker (opcional, se usar Postgres em container)

---

## Como rodar

### Rodar local (H2)

* Por padrão o projeto pode rodar com H2 (embutido). Execute:

```bash
mvn spring-boot:run
```

A H2 console (se ativada) fica disponível em `/h2-console`.

### Rodar com Postgres em Docker Compose

1. Tenha `docker` e `docker-compose`.
2. Arquivo `docker-compose.yml` (exemplo):

```yaml
version: '3.8'
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: ecommerce
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL","pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
volumes:
  pgdata:
```

3. `docker compose up -d`
4. Ajuste `application.properties` para apontar para Postgres:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

---

## Endpoints principais

* `POST /user/create` — cria usuário (permitido sem autenticação)
* `GET /user/findByEmail?email=...` — retorna usuário (pode ser público)
* `GET /products/findAll` — lista produtos
* `GET /products/{id}` — detalhes de produto
* `POST /orders/create` — cria pedido (usuário autenticado)
* `GET /orders/findAll` — listar todos os pedidos (admin)
* `GET /orders/{id}` — pegar pedido

---

## Segurança (Spring Security)

* Autenticação: HTTP Basic (somente para desenvolvimento). O `SecurityConfig` define rotas públicas e protegidas.
* Roles: `ROLE_USER`, `ROLE_ADMIN`.
* `PasswordEncoder` utilizado: `BCryptPasswordEncoder`.

### Observações importantes

* Se receber 401 no frontend mas `curl -u email:senha` funciona, provavelmente o frontend **não está enviando** o header Authorization ou está enviando token errado.
* Se `curl` com admin retorna 403, verifique a atribuição de authorities no `UserDetails` (o `GrantedAuthority` deve ser `ROLE_ADMIN`) e as regras em `SecurityConfig`.
* Evite duplicar beans de `SecurityConfig` e CORS. Se tiver duas classes com `@Configuration` que definem beans com os mesmos nomes (`filterChain`, `corsConfigurationSource`), remova/renomeie uma ou consolide o CORS no `SecurityConfig`.

---

## Logs úteis para debug

Habilite logs do Spring Security para debugar autenticação/authorization:

```
logging.level.org.springframework.security=DEBUG
```

Revise os logs quando fizer requisições autenticadas para entender se as credenciais foram reconhecidas e quais `GrantedAuthorities` foram atribuídas.

---

## Troubleshooting comum (backend)

* **Cannot load driver class: org.postgresql.Driver**: verifique dependência no `pom.xml` e escopo `runtime`. Execute `mvn clean package` para baixar dependências.
* **Password authentication failed for user "postgres"**: confirme variáveis do container (`POSTGRES_PASSWORD`) e `application.properties` com as mesmas credenciais.
* **BeanDefinitionOverrideException / Conflicting beans**: você tem duas classes declarando o mesmo bean (`filterChain` ou `corsConfigurationSource`). Remova/renomeie ou habilite `spring.main.allow-bean-definition-overriding=true` (não recomendado para produção).
* **CORS no frontend**: configure `CorsConfigurationSource` no `SecurityConfig` ou no `WebMvcConfigurer`, mas não em ambas sem ajustar nomes de bean — pode provocar conflito. A solução mais simples é definir **apenas** no `SecurityConfig` (bean `CorsConfigurationSource`) e remover o `WebMvcConfigurer` que registra CORS.

---

## Exemplos curl

* Criar usuário (se endpoint público):

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"Caio","email":"caio@email.com","password":"123"}' http://localhost:8080/user/create
```

* Autenticar e listar pedidos (admin):

```bash
curl -u "admin@local:senhaDoAdmin" http://localhost:8080/orders/findAll
```

---

## Boas práticas / próximos passos

* Substituir HTTP Basic por JWT para uma UX melhor (persistência de sessão no front é mais simples e segura em muitos cenários).
* Adicionar validação/DTOs para requests (por exemplo `SignUpRequest`, `LoginRequest`) e tratar exceções com `@ControllerAdvice`.
* Adicionar testes de integração (Spring Boot Test) para endpoints críticos.

---

Se quiser, eu posso:

* Gerar `README.md` separado como arquivos (frontend/README.md e backend/README.md) para você baixar;
* Adicionar exemplos de código específicos (ex.: `apiClient.ts`, `AuthContext` final) diretamente no README;
* Ou criar um `docker-compose` que inclua tanto backend quanto db para rodar tudo junto.

Diga o que prefere.
