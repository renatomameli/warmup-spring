plugins {
    kotlin("jvm") version "2.1.20"
    `java-library`
    `maven-publish`
}

group = "com.mameli"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly(platform("org.springframework.boot:spring-boot-dependencies:3.5.2"))

    compileOnly("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    implementation("org.slf4j:slf4j-api")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = "warmup"
            version = version.toString()
        }
    }
    repositories {
        maven {
            name = "local"
            url = uri("${layout.buildDirectory}/repo")
        }
    }
}
