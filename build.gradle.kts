plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.3"
    id("java")
}

group = "online.aruka"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "online.aruka.td_kt.MainKt"
    }
    archiveBaseName = ""
}

tasks.shadowJar {
    archiveBaseName = "td-kt"
    archiveClassifier = ""
    archiveVersion = "v${version}"
}

kotlin {
    jvmToolchain(17)
}