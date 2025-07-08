# global service name
SERVICE                 := reportwriter

#######################################################################
#                 OVERRIDE THIS TO MATCH YOUR PROJECT                 #
#######################################################################
APP_VERSION             := $(shell echo `grep "^version =" web/build.gradle | sed 's/version = //'`)
VERSION                 := $(shell echo `grep "^version =" web/build.gradle | sed 's/version = //; s/.RELEASE//'`)

# Builds should be repeatable, therefore we need a method to reference the git
# sha where a version came from.
GIT_VERSION             ?= $(shell echo `git describe --match=NeVeRmAtCh --always --dirty`)
GIT_COMMIT              ?= $(shell echo `git log | grep -m1 -oE '[^ ]+$'`)
GIT_COMMITTED_AT        ?= $(shell echo `git log -1 --format=%ct`)
GIT_BRANCH           ?=
FULL_VERSION            := v$(APP_VERSION)-g$(GIT_VERSION)

.PHONY: build

# consider also "docker save..." and "docker load..." to avoid registry.
clean:
	./gradlew :web:clean

# Build the library without tests
build: build-core build-web build-frontend

build-core:
	./gradlew :core:clean :core:build -x test

build-web: build-core
	./gradlew :web:clean :web:build -x test

build-frontend:
	./gradlew :frontend:clean :frontend:build -x test

# build the frontend (cleans web static resources first)
frontend:
	rm -rf web/src/main/resources/static/*
	./gradlew :frontend:build

# Run the web application
run:
	./gradlew :web:bootRun

# Run frontend in development mode (if needed)
run-frontend-dev:
	cd frontend && npm start

releasetag:
	git tag -a "${VERSION}-RC-`/bin/date +%Y-%m-%d`" -m "Release ${VERSION}-RC-`/bin/date +%Y-%m-%d`"
	git push origin "${VERSION}-RC-`/bin/date +%Y-%m-%d`"

rmreleasetag:
	git tag -d "${VERSION}-RC-`/bin/date +%Y-%m-%d`"
	git push origin --delete "${VERSION}-RC-`/bin/date +%Y-%m-%d`"

tag: frontend
	@git diff --quiet HEAD -- || { echo "verify no repository changes on frontend build, commit changes from make frontend before running make tag"; exit 1; }
	git tag -a "v`/bin/date +%Y-%m-%d`-${APP_VERSION}" -m "Release `/bin/date +%Y-%m-%d`"
	git push origin "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"

rmtag:
	git tag -d "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"
	git push origin --delete "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"

version:
	@echo $(APP_VERSION)

scan:
	trivy fs frontend/package-lock.json --format template -o report.html --template "@config/trivy/html.tpl"
	grep CRITICAL report.html
	./gradlew :web:dependencies --write-locks
	trivy fs web/gradle.lockfile --format template -o reportJava.html --template "@config/trivy/html.tpl"
	grep CRITICAL reportJava.html
	/bin/rm -rf web/gradle.lockfile

scanJava:
	./gradlew :web:dependencies --write-locks
	trivy fs web/gradle.lockfile --format template -o reportJava.html --template "@config/trivy/html.tpl"
	grep CRITICAL reportJava.html
	/bin/rm -rf web/gradle.lockfile

.PHONY: frontend