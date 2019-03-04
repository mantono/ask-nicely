object Version {
	const val KOTLIN = "1.3.21"
	const val JVM = "1.8"
	const val COROUTINES = "1.1.1"
	const val JUNIT = "5.3.2"
}

plugins {
	id("io.gitlab.arturbosch.detekt") version "1.0.0.RC6-2"
	id("org.jmailen.kotlinter") version "1.21.0"
	id("org.sonarqube") version "2.6.2"
	id("application") apply true
	id("org.jetbrains.kotlin.jvm") version "1.3.21" apply true
	id("java") apply true
	id("maven") apply true
	id("idea") apply true
}

application {
	mainClassName = "com.mantono.MainKt"
}

group = "com.mantono"
version = "0.1.0"
description = "Library for easier providing an interactive command line interface"

defaultTasks = listOf("test")

repositories {
	mavenLocal()
	jcenter()
	mavenCentral()
	maven(url = "https://jitpack.io")
}

dependencies {
	implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", Version.KOTLIN)
	implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", Version.COROUTINES)
	implementation("com.github.ajalt.mordant:mordant:1.0.1")

	// Junit
	testCompile("org.junit.jupiter", "junit-jupiter-api", Version.JUNIT)
	testRuntime("org.junit.jupiter", "junit-jupiter-engine", Version.JUNIT)
}

tasks {
	test {
		useJUnitPlatform()

		// Show test results.
		testLogging {
			events("passed", "skipped", "failed")
		}
		reports {
			junitXml.isEnabled = false
			html.isEnabled = true
		}
	}

	compileKotlin {
		sourceCompatibility = Version.JVM
		kotlinOptions {
			jvmTarget = Version.JVM
		}
	}


	wrapper {
		description = "Generates gradlew[.bat] scripts for faster execution"
		gradleVersion = "5.2.1"
	}
}
