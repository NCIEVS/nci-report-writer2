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

ifeq ($(OS),Windows_NT)
DOCKER                  ?= docker.exe
WEB_GRADLEW             ?= java -classpath gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain
else
DOCKER                  ?= docker
WEB_GRADLEW             ?= java -classpath gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain
endif

.PHONY: build

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

# Build a Linux deployment image of the web module (uses the committed frontend in web/src/main/resources/static).
docker:

	@$(DOCKER) image inspect "$(DOCKER_IMAGE)" > /dev/null 2>&1 && $(DOCKER) rmi -f "$(DOCKER_IMAGE)" || true
	$(DOCKER) build --platform linux/amd64 --no-cache-filter=gradle-build --file web/Dockerfile --tag "$(DOCKER_IMAGE)" web

# Report all HIGH and CRITICAL image vulnerabilities with their installed and fixed versions.
# The complete HTML report is written to report.html.
scandocker:
	$(DOCKER) save -o scan.tar "$(DOCKER_IMAGE)"
	trivy image --input scan.tar "$(DOCKER_IMAGE)" --format template -o report.html --template "@config/trivy/html.tpl"
	/bin/rm -f scan.tar

# Run against the standard local MySQL and Jena/Fuseki testing services exposed on the Docker host.
# This runs in the foreground; add -d to run in the background.
rundocker:
	$(DOCKER) run --rm --name "$(SERVICE)" -p "8080:8080" \
		-e RW_API_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3312/reportwriter?useSSL=false&allowPublicKeyRetrieval=true" \
		-e RW_API_DATASOURCE_USERNAME="root" \
		-e RW_API_DATASOURCE_PASSWORD="reportwriter234" \
		-e GRAPHDB_MONTHLY_QUERY_URL="http://host.docker.internal:3030/NCIT2/query" \
		-e GRAPHDB_WEEKLY_QUERY_URL="http://host.docker.internal:3030/CTRP/query" \
		-e GRAPHDB_USERNAME="admin" \
		-e GRAPHDB_PASSWORD="admin" \
		-e RW_DEFAULT_USERNAME="system" \
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

# Match the CI "Angular Jest Tests" job: clean install from package-lock.json, then run jest.
test:
	cd frontend; npm ci
	cd frontend; npm run test

rmtag:
	git tag -d "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"
	git push origin --delete "v`/bin/date +%Y-%m-%d`-${APP_VERSION}"

version:
	@echo $(APP_VERSION)

# Scan frontend and backend dependencies. Full HTML reports are written to report.html (frontend)
# and reportJava.html (backend); fails if either has HIGH or CRITICAL vulnerabilities (.trivyignore applies).
scan:
	@status=0; \
	trivy fs frontend/package-lock.json --format template -o report.html --template "@config/trivy/html.tpl" || status=1; \
	trivy fs frontend/package-lock.json --scanners vuln --severity HIGH,CRITICAL --exit-code 1 || status=1; \
	(cd web; ./gradlew dependencies --write-locks -q) || status=1; \
	trivy fs web/gradle.lockfile --format template -o reportJava.html --template "@config/trivy/html.tpl" || status=1; \
	trivy fs web/gradle.lockfile --scanners vuln --severity HIGH,CRITICAL --exit-code 1 || status=1; \
	/bin/rm -f web/gradle.lockfile; \
	if [ $$status -ne 0 ]; then echo "FAILED: HIGH or CRITICAL vulnerabilities found (or scan error) - see report.html / reportJava.html"; fi; \
	exit $$status

.PHONY: frontend
