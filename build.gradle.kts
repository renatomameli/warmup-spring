plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("kapt") version "2.1.20"
    `java-library`
    `maven-publish`
}

group = "com.mameli"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

val jmh: SourceSet by sourceSets.creating {
    kotlin.srcDirs("src/jmh/kotlin")
}

configurations.getByName("jmhImplementation").extendsFrom(configurations["implementation"])
configurations.getByName("jmhRuntimeOnly").extendsFrom(configurations["runtimeOnly"])

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.2"))

    compileOnly("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    implementation("org.slf4j:slf4j-api")

    "jmhImplementation"(sourceSets.main.get().output)
    "jmhImplementation"("org.springframework.boot:spring-boot-starter-web")
    "jmhImplementation"("org.openjdk.jmh:jmh-core:1.37")
    "kaptJmh"("org.openjdk.jmh:jmh-generator-annprocess:1.37")

    testImplementation(kotlin("test"))
}

tasks.register<JavaExec>("runJmh") {
    group = "benchmark"
    description = "Runs JMH benchmarks"
    classpath = jmh.runtimeClasspath
    mainClass = "com.mameli.BenchmarkRunnerKt"
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
