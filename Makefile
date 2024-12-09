.PHONY: build

build:
	docker build -t quick-buy/base --target=quick-base .
	docker build -t quick-buy/dev --target=quick-dev .
	docker build -t quick-buy/build --target=quick-build .
	docker build -t quick-buy/pos --target=quick-pos .

parent:
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm gradle:8.11.0-jdk21-corretto /bin/sh

dev:
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm quick-buy/dev /bin/sh

jar: # check on the final output jar
	docker run --detach-keys='ctrl-t,t' --mount type=bind,source="${PWD}",target=/app -it --rm quick-buy/build /bin/sh

run:
	docker run --rm quick-buy/pos
