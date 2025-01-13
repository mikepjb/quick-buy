.PHONY: build

build:
	docker build -t quick-buy/base --target=quick-base .
	docker build -t quick-buy/dev --target=quick-dev .
	docker build -t quick-buy/build --target=quick-build .
	docker build -t quick-buy/pos --target=quick-pos .

parent:
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm gradle:8.11.0-jdk21-corretto /bin/sh

fallback-dev: # when things will not compile but you need Java 21 etc
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm quick-buy/dev /bin/sh

dev: # check on the final output jar
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm quick-buy/build /bin/sh

test:
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm quick-buy/build ./gradlew test

run:
	docker run --rm --net=host quick-buy/pos
