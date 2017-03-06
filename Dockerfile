FROM discoenv/clojure-base:master

ENV CONF_TEMPLATE=/usr/src/app/iplant-email.properties.tmpl
ENV CONF_FILENAME=iplant-email.properties
ENV PROGRAM=iplant-email

VOLUME ["/etc/iplant/de"]

COPY project.clj /usr/src/app/
RUN lein deps

COPY conf/main/logback.xml /usr/src/app/
COPY conf/*.st /usr/src/app/
COPY . /usr/src/app

RUN lein uberjar && \
    cp target/iplant-email-standalone.jar .

RUN ln -s "/usr/bin/java" "/bin/iplant-email"

ENTRYPOINT ["run-service", "-Dlogback.configurationFile=/etc/iplant/de/logging/iplant-email-logging.xml", "-cp", ".:iplant-email-standalone.jar", "iplant_email.core"]

ARG git_commit=unknown
ARG version=unknown
ARG descriptive_version=unknown

LABEL org.cyverse.git-ref="$git_commit"
LABEL org.cyverse.version="$version"
LABEL org.cyverse.descriptive-version="$descriptive_version"
LABEL org.label-schema.vcs-ref="$git_commit"
LABEL org.label-schema.vcs-url="https://github.com/cyverse-de/iplant-email"
LABEL org.label-schema.version="$version"
