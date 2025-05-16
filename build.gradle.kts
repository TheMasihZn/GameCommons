plugins {
    kotlin("jvm") version "1.7.10"
}

sourceSets {
    main {
        java.srcDirs("src/main/java/")
        java.srcDirs("src/main/kotlin/")
    }
}
tasks.register<Jar>("packageJar") {
    archiveBaseName.set("commons") // or any name you prefer
    from(sourceSets.main.get().output)
}

group = "dirty7server.masih.zn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://download2.dynamsoft.com/maven/dce/aar") }
}


val ktorVersion = "2.2.4"
val logbackVersion = "1.2.11"
dependencies {
    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}
