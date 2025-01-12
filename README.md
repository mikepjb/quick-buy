# Quick Buy

A Point of Sale (POS) system with a focus on taking payments quickly to serve many customers and
keep them all happy!

This is also a project dedicated to learning modern Java. (No I have asked and it isn't an
oxymoron).

## Getting Started

`make build && make run`

## Learning Objectives

- [X] Gradle (setup the project, learnt basics of calling/discovering tasks)
- [-] RestTemplate (use template to make call, hydrate with exchange => string.class & objectmapper)
- [ ] Annotations
    - [ ] Cache
    - [ ] Bean (manually creating a bean)
    - [ ] Transaction
    - [-] Service (created as part of RestTemplate learning `ApiService`.)
    - [ ] Component
- [ ] Rest API Validation (like NotNull)
- [ ] Error Handling
- [ ] Fat Jar
- [ ] Docker Image

### Side knowledge

- DTO => Data Transfer Objects or code for exchanging data over the network.

## Tools used

- Spring boot & Java 21
- HTMX, plain CSS & JS.

## Notes on how this codebase was setup

- Start the parent image with `make parent` and run `cd /app && gradle init` running through the
  interactive setup prompt.
  - Java 21
  - Single Application
  - Kotlin Gradle DSL
  - JUnit 4
  - Using newer APIs (this was a prompt question)

- Discover `gradle` tasks with `./gradlew -q :tasks --all`

## Ideas to extend

- Write an integration test using mockmvc
- Add production-grade services e.g actuator (health checks, remote execution tasks)

## A series of unfortunate events

- After building, it looks like a server starts but Spring doesn't seem to mention which port it's
  listening on. I also can't find the config for this. Turns out it does mention which port it's
  running on (8080), "Tomcat is running on.. X".
- Hitting the root (localhost:8080/) returns a whitelabel error page, we have to implement /error.
- I thought this would solve the problem:
```java
@GetMapping("/error")
public String error(String name) {
  return String.format("Page not found under %s", name);
}
```
- It did not!
- Anyway I am switching focus to making an index page from a template.
- Creating `/app/resources/templates` to store the template files since this is java.
- Learning about beans.. these are components/parts managed by the Spring IoC container. It's fully
  managed by the IoC container, the object gets build, configured etc (Inversion of Control).
- IoC is a process in which an object defines its dependencies without creating them.
- Continue later: https://www.baeldung.com/spring-bean
- Yeah we managed to load a file from resources folder.. because we did it sanely (i.e we
  explicitly loaded it without magic) I expect this is not the expected way of doing things.
