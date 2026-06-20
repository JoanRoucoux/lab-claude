# AGENTS.md

Ce fichier fournit des consignes communes à tous les agents IA (Claude Code, Cursor, GitHub Copilot, etc.) qui travaillent dans ce dépôt.

## Langue de communication

Communique toujours en français avec l'utilisateur dans ce dépôt (réponses, messages de commit, commentaires de PR, etc.), même si le code et les identifiants restent en anglais.

## Project overview

A minimal Spring Boot 4.1.0 web application (Java 25) using the Maven wrapper. It currently exposes `/hello` and `/version` REST endpoints plus the Actuator health endpoint.

## Common commands

Use the Maven wrapper (`mvnw.cmd` on Windows, `./mvnw` in a POSIX shell) rather than a globally installed Maven.

- Build: `mvnw.cmd clean package`
- Run the app: `mvnw.cmd spring-boot:run` (starts on the default port 8080)
- Run all tests: `mvnw.cmd test`
- Run a single test class: `mvnw.cmd test -Dtest=HelloServiceTest`
- Run a single test method: `mvnw.cmd test -Dtest=HelloServiceTest#shouldReturnHelloMessage`

Le numéro de version exposé par `/version` vient de `src/main/resources/application.properties` (`app.version=@project.version@`), substitué par le filtrage de ressources Maven (`pom.xml`) avec la version du `pom.xml` au moment du build — pas besoin de packager (`package`) pour que ce soit disponible, le filtrage s'exécute pendant la phase `process-resources`, avant `test`.

## Architecture

Standard layered Spring MVC structure under `com.joanroucoux.labclaude`:

- `controller` — `@RestController` classes that map HTTP routes and delegate to services. Controllers are tested in isolation with `@WebMvcTest` + `MockMvc`, mocking the service layer via `@MockitoBean` (see `HelloControllerTest`).
- `service` — `@Service` classes with the actual logic. Tested as plain unit tests (no Spring context), instantiated directly with `new`.
- `model` — data carriers, implemented as Java records (e.g. `HelloResponse`).

`LabClaudeApplicationTests` is a full `@SpringBootTest` smoke test that just verifies the application context loads.

Note: the package is `com.joanroucoux.labclaude` (no hyphen), not `com.joanroucoux.lab-claude` — the artifact id has a hyphen but Java package names cannot, so the package was deliberately renamed (see `HELP.md`).

Lombok is on the classpath with the annotation processor wired into both the compile and test-compile executions of `maven-compiler-plugin`, but no class currently uses Lombok annotations — keep this in mind if adding boilerplate-heavy classes (getters/setters/constructors) instead of writing them by hand.

Actuator is included with only the `health` endpoint exposed over the web (`management.endpoints.web.exposure.include=health` in `application.properties`). Other Actuator endpoints are not exposed by default — extend this property if more are needed.

`spring-boot-starter-test` ne suffit plus à `@WebMvcTest` depuis Spring Boot 4.1 : la classe `WebMvcTest` vit dans le module séparé `spring-boot-webmvc-test`, apporté par la dépendance `spring-boot-starter-webmvc-test` (test scope) dans le `pom.xml`. Sans elle, les tests de contrôleur ne compilent pas.

## Bonnes pratiques Java (à respecter dans ce dépôt)

- **Injection par constructeur uniquement** — jamais de champ `@Autowired`. Les constructeurs des `@RestController`/`@Service` exposent leurs dépendances et restent facilement testables avec `new` (voir `HelloController`, `HelloService`).
- **Records pour les DTO immuables** — `model/` ne doit contenir que des `record` (pas de classe avec getters/setters mutables). N'introduire Lombok que si une classe a réellement besoin de champs mutables ou de constructeurs complexes ; ne pas l'utiliser pour remplacer un simple record.
- **Services sans état** — pas de champ mutable dans les `@Service` (ils sont singletons partagés entre toutes les requêtes Tomcat). Toute donnée de configuration doit arriver par injection (constructeur, `@Value`), jamais via un champ modifiable après construction.
- **Visibilité minimale** — seules les classes que Spring doit instancier ou que les tests doivent importer depuis un autre package sont `public` ; le reste peut rester package-private.
- **Tests adaptés à la couche** — un test de `service` n'a pas besoin de contexte Spring (instanciation directe avec `new`, voir `HelloServiceTest`/`VersionServiceTest`) ; réserver `@WebMvcTest`/`@SpringBootTest` aux couches qui en dépendent réellement (couche web, chargement du contexte).
- **Pas de logique dans les `model`** — les records restent de simples porteurs de données ; toute logique (formatage, validation, calcul) vit dans le `service`.
