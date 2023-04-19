FROM eclipse-temurin:17 AS build
COPY . /build/
WORKDIR /build/
RUN chmod +x gradlew
RUN ./gradlew test --stacktrace && ./gradlew shadowJar --stacktrace

FROM moby/buildkit AS buildkit-stage

FROM eclipse-temurin:17
COPY --from=buildkit-stage /usr/bin/buildctl /usr/bin/buildctl

# Verify buildctl installation
RUN buildctl --version

COPY --from=build /build/build/libs/image-build-1.0-SNAPSHOT-all.jar /image-build.jar


CMD [ "java", "-jar", "/image-build.jar" ]