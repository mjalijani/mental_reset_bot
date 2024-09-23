plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "com.ideaapp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.telegram:telegrambots:6.8.0")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("io.ktor:ktor-client-core:1.6.3")
    implementation("io.ktor:ktor-client-json:1.6.3")
    implementation("io.ktor:ktor-client-serialization:1.6.3")
    implementation("io.ktor:ktor-client-okhttp:2.2.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.2")
    implementation("io.ktor:ktor-client-logging-jvm:2.2.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.2.2")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.apache.commons:commons-imaging:1.0-alpha3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}