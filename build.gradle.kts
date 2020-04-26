import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    base
    id("org.springframework.boot") version "2.2.6.RELEASE"  apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE"  apply false
    id("org.asciidoctor.convert") version "1.5.8"  apply false
    kotlin("jvm") version "1.3.61"  apply false
    kotlin("plugin.spring") version "1.3.61"  apply false
}

allprojects {
    group = "com.luminoso"
    version = "0.0.1"

    repositories {
        jcenter()
    }
}

subprojects {
//    apply(plugin = "org.springframework.boot")

    println("Enabling Kotlin Spring plugin in project ${project.name}...")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    println("Enabling Spring Boot Dependency Management in project ${project.name}...")
    apply(plugin = "io.spring.dependency-management")
    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}
