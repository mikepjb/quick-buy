# Quick Buy

A Point of Sale (POS) system with a focus on taking payments quickly to serve many customers and
keep them all happy!

This is also a project dedicated to learning modern Java. (No I have asked and it isn't an
oxymoron).

## Getting Started

`make build && make run`

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
