plugins {
    kotlin("jvm") version "1.7.10"
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin/commons")
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


val ktor_version = "2.2.4"
val logback_version = "1.2.11"
dependencies {
    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
//    implementation("io.ktor.plugin:plugin:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
}

tasks.test {
    useJUnitPlatform()
}
