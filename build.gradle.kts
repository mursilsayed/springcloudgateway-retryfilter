import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2022.0.4"

dependencies {
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}"))
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.cloud", "spring-cloud-contract-wiremock")
//	testImplementation("com.github.tomakehurst", "wiremock-jre8-standalone", "2.35.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito.kotlin", "mockito-kotlin", "5.1.0")
	testImplementation("org.mockito", "mockito-junit-jupiter", "5.7.0")

}

kotlin{
	jvmToolchain(17)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
