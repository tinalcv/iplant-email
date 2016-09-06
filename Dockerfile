FROM clojure:alpine

VOLUME ["/etc/iplant/de"]

ARG git_commit=unknown
ARG version=unknown
LABEL org.iplantc.de.iplant-email.git-ref="$git_commit" \
      org.iplantc.de.iplant-email.version="$version"

COPY . /usr/src/app
COPY conf/main/logback.xml /usr/src/app/logback.xml
COPY conf/*.st /usr/src/app/

WORKDIR /usr/src/app

RUN apk add --update git && \
    rm -rf /var/cache/apk

RUN lein uberjar && \
    cp target/iplant-email-standalone.jar .

RUN ln -s "/usr/bin/java" "/bin/iplant-email"

ENTRYPOINT ["iplant-email", "-Dlogback.configurationFile=/etc/iplant/de/logging/iplant-email-logging.xml", "-cp", ".:iplant-email-standalone.jar", "iplant_email.core"]
CMD ["--help"]
