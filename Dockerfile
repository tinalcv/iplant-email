FROM clojure:lein-alpine

WORKDIR /usr/src/app

RUN apk add --no-cache git

RUN ln -s "/usr/bin/java" "/bin/iplant-email"

COPY project.clj /usr/src/app/
RUN lein deps

COPY conf/main/logback.xml /usr/src/app/
COPY conf/*.st /usr/src/app/
COPY . /usr/src/app

RUN lein uberjar && \
    cp target/iplant-email-standalone.jar .

ENTRYPOINT ["iplant-email", "-Dlogback.configurationFile=/etc/iplant/de/logging/iplant-email-logging.xml", "-cp", ".:iplant-email-standalone.jar", "iplant_email.core"]
CMD ["--help"]

ARG git_commit=unknown
ARG version=unknown
ARG descriptive_version=unknown

LABEL org.cyverse.git-ref="$git_commit"
LABEL org.cyverse.version="$version"
LABEL org.cyverse.descriptive-version="$descriptive_version"
LABEL org.label-schema.vcs-ref="$git_commit"
LABEL org.label-schema.vcs-url="https://github.com/cyverse-de/iplant-email"
LABEL org.label-schema.version="$descriptive_version"
