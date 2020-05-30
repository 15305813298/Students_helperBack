import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	kotlin("plugin.jpa") version "1.3.61"
}


group = "cn.lintyone.out"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("com.graphql-java:graphql-spring-boot-starter:5.0.2")
	implementation("com.graphql-java-kickstart:playground-spring-boot-starter:6.0.1")
	implementation("com.graphql-java:graphql-java-tools:5.2.4")
	implementation("com.auth0:java-jwt:3.8.3")
	implementation("com.google.code.gson:gson:2.8.6")
	implementation("com.github.penggle:kaptcha:2.3.2")
	implementation("org.mindrot:jbcrypt:0.4")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.4.1")
	implementation("com.github.wenhao:jpa-spec:3.2.5")


	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
