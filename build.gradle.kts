import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVersion = "1.6.21"
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.6.3")
        classpath("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
    }
}

plugins {
    val kotlinVersion = "1.6.21"

    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("io.gitlab.arturbosch.detekt") version "1.20.0"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.noarg") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "com.here-city"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    google()
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
    plugin("org.jetbrains.kotlin.plugin.spring")

    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-jpa")
    plugin("kotlin-kapt")
}

allOpen {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}

dependencies {
    val springdocVersion = "1.6.12"
    val jwtVersion = "0.11.5"
    val querydslVersion = "5.0.0"
    val kotestVersion = "5.1.0"

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // oauth2 && security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.boot:spring-boot-starter-validation")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // springdoc
    implementation("org.springdoc:springdoc-openapi-kotlin:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-security:$springdocVersion")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    // firebase-admin
    implementation("com.google.firebase:firebase-admin:9.1.1")
    runtimeOnly("com.google.firebase:firebase-admin:9.1.1")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:$querydslVersion")
    implementation("org.hibernate:hibernate-spatial:5.6.12.Final")

// 	implementation("org.springframework.boot:spring-boot-starter-batch")
// 	implementation("org.springframework.boot:spring-boot-starter-quartz")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("com.querydsl:querydsl-apt:$querydslVersion:jpa")

    // kapt
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // mockk
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("com.ninja-squad:springmockk:3.1.1")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    // testImplementation("io.kotest:kotest-framework-datatest:5.3.0")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    // kotlin-faker
    implementation("io.github.serpro69:kotlin-faker:1.12.0")

    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.1.0")
}

kotlin.sourceSets.main {
    setBuildDir("$buildDir/generated/source/kapt/main")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

detekt {
    toolVersion = "1.22.0"
    config = files("detekt-config.yaml")
    buildUponDefaultConfig = true
}
