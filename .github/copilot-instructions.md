# GitHub Copilot Context

## Repository profile
This repository is an Adobe Experience Manager (AEM) 6.5 multi-module Maven project based on the AEM Project Archetype. Use AEM, Sling, OSGi Declarative Services, HTL, JCR, and Core Components conventions in all suggestions.

## Module map
Main modules and responsibilities:
- `all/`: deployment package aggregator
- `core/`: Java code, OSGi services, Sling Models, servlets, filters, listeners, schedulers
- `ui.apps/`: components, HTL, dialogs, clientlibs, templates, i18n
- `ui.apps.structure/`: base repository structure under `/apps` and `/conf`
- `ui.config/`: OSGi configuration by runmode
- `ui.content/`: initial or sample content
- `it.tests/`: Java integration tests
- `ui.tests/`: Cypress UI tests

## Architectural rules
- Respect Maven module boundaries and AEM layering
- Keep business logic in OSGi services under `core`
- Keep HTL focused on presentation
- Keep Sling Models thin and component-oriented
- Use configuration instead of hardcoded environment values
- Reuse Core Components through proxies when possible
- Prefer minimal, production-ready changes over broad rewrites

## Java and AEM conventions
- Prefer Sling Models for component backing logic
- Prefer OSGi services for reusable business logic
- Prefer `@SlingServletResourceTypes` over path-based servlets
- Use OSGi DS annotations such as `@Component`, `@Reference`, `@Activate`, `@Modified`, and `@Deactivate`
- Use `@Designate` and typed OSGi config interfaces for configurable services
- Use `@OSGiService`, `@SlingObject`, and `@ValueMapValue` where appropriate
- If repository write or privileged access is needed, use service users and least privilege
- Always close `ResourceResolver` instances obtained from factories
- Use try-with-resources when applicable

## Packaging conventions
Typical package layout under `core/src/main/java/...`:
- `models/`
- `services/`
- `services/impl/`
- `servlets/`
- `filters/`
- `listeners/`
- `schedulers/`

Place new classes in the correct package and keep names explicit and conventional.

## HTL and component guidance
- Keep logic out of HTL when it becomes non-trivial
- Expose data to HTL through Sling Models
- Keep dialogs, templates, policies, and clientlibs aligned with AEM conventions
- Prefer maintainable component structure over inline shortcuts

## Patterns to prefer
- Layered architecture
- Dependency injection
- Sling Model pattern
- Adapter pattern via `adaptTo(...)`
- OSGi service abstraction
- Resource type servlet binding
- Scheduler configuration through OSGi
- Structured JSON error responses for APIs

## Anti-patterns to avoid
Do not generate code that introduces these problems:
- God components or oversized Sling Models
- Manual service lookup instead of dependency injection
- Hardcoded content paths, hosts, usernames, or passwords
- Admin sessions or unnecessarily privileged access
- Business logic embedded in HTL
- Path-based servlets when resource-type binding is viable
- Unclosed `ResourceResolver` instances
- Mixing rendering, business logic, and integration logic in the same class

## Testing expectations
- Use JUnit 5 and Mockito for unit tests
- Keep tests focused and close to class responsibility
- Assume Cypress for end-to-end UI tests in `ui.tests/`
- Preserve existing test style where possible

## Build conventions
Common commands in this repository:
- `mvn clean install`
- `mvn clean install -PautoInstallSinglePackage`
- `mvn clean install -PautoInstallSinglePackagePublish`
- `mvn clean install -PautoInstallBundle`
- `mvn clean verify -Pit`

Assume local defaults unless code indicates otherwise:
- Author: `localhost:4502`
- Publish: `localhost:4503`

## Code generation behavior
When proposing changes:
1. Mention the target module and probable path.
2. Match existing AEM and Java conventions.
3. Include imports for Java snippets.
4. Prefer concrete, compilable examples.
5. Call out required OSGi configuration when relevant.
6. Call out service user mapping when relevant.
7. Warn when a suggestion breaks AEM best practices.

## Commit and branch conventions
Follow repository conventions when suggesting commits:
- Conventional commits: `<type>(<scope>): <description>`
- Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `perf`
- Use short imperative descriptions
- Branch naming: `<type>/<issue-number>-<short-description>`

## Preferred assistant style
- Be precise and implementation-oriented
- Prefer AEM-specific guidance over generic Java advice
- Explain where each file should live
- Note trade-offs only when they matter
