buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(libs.flyway.postgresql)
    }
}

plugins {
    // Dev Plugins
    idea

    // Spring
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.depMan)

    // Kotlin
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)

    // Tests and Quality
    jacoco

    // Other
    alias(libs.plugins.sekret)
    alias(libs.plugins.flyway)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

extra["springCloudVersion"] = "2025.0.0"

dependencies {
    /* Kotlin */
    implementation(kotlin("reflect"))
    implementation(libs.jacksonKotlin)
    implementation(libs.jetbrain.annotation)

    /* Web */
    implementation(libs.spring.starter.web)

    /* Observability */
    runtimeOnly(libs.micrometer.statsd)
    implementation(libs.spring.starter.actuator)
    implementation(libs.spring.starter.logging)

    /* Persistence */
    runtimeOnly(libs.postgresql)
    implementation(libs.spring.starter.jpa)

    /* Security */
    implementation(libs.spring.starter.oauth2.resourceServer)

    /* Caching */
    implementation(libs.caffeine)
    implementation(libs.spring.starter.cache)

    /* Other */
    implementation(libs.uuidv7)
    implementation(libs.spring.starter.validation)

    /* Spring Dev Tools */
    developmentOnly(libs.spring.devtools)
    annotationProcessor(libs.spring.confProcessor)

    /* Test */
    testImplementation(libs.spring.starter.test) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(libs.restassured)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.spring.testcontainers)
    testImplementation(libs.testcontainers.base)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.faker)
//	testImplementation("org.springframework.boot:spring-boot-testcontainers")
//	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//	testImplementation("org.testcontainers:junit-jupiter")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

/* **************** */
/* Dev Environment  */
/* **************** */
idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true
    }
}

/* **************** */
/* Sekret Config    */
/* **************** */
sekret {
    mask = "<redacted>"
    annotations = listOf("io.github.g0dkar.spb.SensitiveData")
}

/* **************** */
/* Tasks Config     */
/* **************** */
tasks {
    jacocoTestReport {
        dependsOn(test)

        reports {
            xml.required.set(true)
            csv.required.set(false)
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    bootJar {
        archiveFileName.set("${project.name}.${archiveExtension.get()}")
    }

    bootBuildImage {
//        builder.set("paketobuildpacks/builder-jammy-base:latest")
//        environment.put("BP_JVM_VERSION", "25")
        environment.put("BP_JVM_CDS_ENABLED", "true")
    }
}

/* **************** */
/* Misc Config      */
/* **************** */
kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

springBoot {
    buildInfo {
        properties {
            group.set(project.group as String)
            version.set(project.version as String)
            artifact.set(project.name)
            name.set("Spring Panic Button")
        }
    }
}

/* **************** */
/* Database         */
/* **************** */
val jdbcDriver = properties.getOrDefault("jdbc.driver", "org.postgresql.Driver")?.toString()
val jdbcUrl = properties.getOrDefault("jdbc.url", "jdbc:postgresql://localhost:15432/spb")?.toString()
val jdbcUser = properties.getOrDefault("jdbc.username", "spb")?.toString()
val jdbcPassword = properties.getOrDefault("jdbc.password", "spb.123")?.toString()

flyway {
    driver = jdbcDriver
    url = jdbcUrl
    user = jdbcUser
    password = jdbcPassword
    createSchemas = true
    table = "flyway_schema_history"
}
