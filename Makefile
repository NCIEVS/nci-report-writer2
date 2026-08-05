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
DOCKER_TAG              := $(shell grep "^version =" web/build.gradle | sed 's/version = //; s/"//g; s/.RELEASE//')
DOCKER_IMAGE            ?= $(SERVICE):$(DOCKER_TAG)
DOCKER_PORT             ?= 8080
MYSQL_PORT              ?= 3306
GRAPH_DB_PORT           ?= 3030
DOCKER_MYSQL_HOST       ?= host.docker.internal
DOCKER_GRAPH_DB_HOST    ?= host.docker.internal
RW_DATABASE             ?= rw
WEB_DOCKER_INPUTS       := $(shell git ls-files --cached --others --exclude-standard web)
DOCKER_BUILD_STAMP      := web/build/.docker-image-$(DOCKER_TAG)

ifeq ($(OS),Windows_NT)
DOCKER                  ?= docker.exe
WEB_GRADLEW             ?= java -classpath gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain
else
DOCKER                  ?= docker
WEB_GRADLEW             ?= java -classpath gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain
endif

.PHONY: build docker scandocker rundocker

# consider also "docker save..." and "docker load..." to avoid registry.
clean:
	cd web; $(WEB_GRADLEW) clean

# Build the library without tests
build:
	cd web; $(WEB_GRADLEW) clean build -x test

# build the 

frontend:
	/bin/rm -rf web/src/main/resources/static/*
	cd frontend; ./gradlew build

# Run the web application
run:
	cd web; java -jar build/libs/ncireportwriter2-*war

# Build the executable Spring Boot WAR and Docker image only when an application
# input, the Dockerfile, or this Makefile is newer than the build stamp.
$(DOCKER_BUILD_STAMP): Makefile web/Dockerfile $(WEB_DOCKER_INPUTS)
	cd web; $(WEB_GRADLEW) clean build -x test
	$(DOCKER) build --file web/Dockerfile --tag "$(DOCKER_IMAGE)" web
	touch "$@"

# Reuse a current image. If it was removed outside Make, rebuild it on demand.
docker: $(DOCKER_BUILD_STAMP)
	@$(DOCKER) image inspect "$(DOCKER_IMAGE)" > /dev/null 2>&1 || { rm -f "$(DOCKER_BUILD_STAMP)"; $(MAKE) --no-print-directory "$(DOCKER_BUILD_STAMP)"; }

# Report HIGH and CRITICAL image vulnerabilities and write the complete HTML report.
scandocker: docker
	trivy image "$(DOCKER_IMAGE)" --scanners vuln --severity HIGH,CRITICAL --format table
	trivy image "$(DOCKER_IMAGE)" --scanners vuln --format template -o report-docker.html --template "@config/trivy/html.tpl"

# Run against MySQL and Jena/Fuseki services exposed on the Docker host.
# Override DOCKER_MYSQL_HOST, DOCKER_GRAPH_DB_HOST, ports, or credentials as needed.
rundocker: docker
	$(DOCKER) run --rm --name "$(SERVICE)" -p "$(DOCKER_PORT):8080" \
		-e RW_API_SERVER_PORT=8080 \
		-e RW_API_DATASOURCE_URL="jdbc:mysql://$(DOCKER_MYSQL_HOST):$(MYSQL_PORT)/$(RW_DATABASE)?useSSL=false&allowPublicKeyRetrieval=true" \
		-e RW_API_DATASOURCE_USERNAME \
		-e RW_API_DATASOURCE_PASSWORD \
		-e GRAPHDB_MONTHLY_QUERY_URL="http://$(DOCKER_GRAPH_DB_HOST):$(GRAPH_DB_PORT)/NCIT2/query" \
		-e GRAPHDB_WEEKLY_QUERY_URL="http://$(DOCKER_GRAPH_DB_HOST):$(GRAPH_DB_PORT)/CTRP/query" \
		-e GRAPHDB_USERNAME \
		-e GRAPHDB_PASSWORD \
		-e RW_DEFAULT_USERNAME \
		"$(DOCKER_IMAGE)"

runfrontend:
	cd frontend; npm start

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

test: 
	cd frontend; npm run test

rmtag:
	git tag -d "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"
	git push origin --delete "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"

version:
	@echo $(APP_VERSION)

scan:
	trivy fs frontend/package-lock.json --format template -o report.html --template "@config/trivy/html.tpl"
	grep CRITICAL report.html
	cd web; ./gradlew dependencies --write-locks
	trivy fs web/gradle.lockfile --format template -o reportJava.html --template "@config/trivy/html.tpl"
	grep CRITICAL reportJava.html
	/bin/rm -rf web/gradle.lockfile

.PHONY: frontend