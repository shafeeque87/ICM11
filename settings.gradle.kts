pluginManagement {
    val dockerPluginVersion = "2.11.2"
    val icmGradlePluginVersion = "5.8.0"
    
    plugins {
        id("com.intershop.gradle.icm.project") version icmGradlePluginVersion
        id("com.intershop.icm.cartridge.product") version icmGradlePluginVersion

        id("com.intershop.gradle.icm.docker.customization") version dockerPluginVersion
        id("com.intershop.gradle.icm.docker.solrcloud") version dockerPluginVersion
        id("com.intershop.gradle.icm.docker.gebtest") version dockerPluginVersion

        id("com.intershop.gradle.cartridge-resourcelist") version "4.4.1"
        id("com.intershop.gradle.jaxb") version "5.2.1"
        id("com.intershop.gradle.isml") version "6.3.2"
        id("org.gradle.test-retry") version "1.3.1"

        id("com.intershop.gradle.version.gitflow") version "1.9.0"
        id("com.dorongold.task-tree") version "2.1.0"

        id("org.sonarqube") version "3.4.0.2513"
    }

    val repoUser: String by settings
    val repoPassword: String by settings

    val adoOrganizationName: String by settings
    val adoProjectName: String by settings

    repositories {
        maven {
            name = "PluginRepository"
            url = uri("https://pkgs.dev.azure.com/${adoOrganizationName}/${adoProjectName}/_packaging/icm-maven-artifacts/maven/v1")
            credentials {
                username = repoUser
                password = repoPassword
            }
        }
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

plugins {
    id("com.gradle.enterprise").version("3.5.1")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("selenium", "3.141.59")
            version("testscontainers", "1.16.0")
            version("groovy", "2.5.4")

            library("selenium-api", "org.seleniumhq.selenium", "selenium-api").versionRef("selenium")
            library("selenium-support", "org.seleniumhq.selenium", "selenium-support").versionRef("selenium")
            library("selenium-remote-driver", "org.seleniumhq.selenium", "selenium-remote-driver").versionRef("selenium")
            library("selenium-firefox-driver", "org.seleniumhq.selenium", "selenium-firefox-driver").versionRef("selenium")
            library("selenium-chrome-driver", "org.seleniumhq.selenium", "selenium-chrome-driver").versionRef("selenium")

            library("testscontainers-spock", "org.testcontainers", "spock").versionRef("testscontainers")
            library("testscontainers-core", "org.testcontainers", "testcontainers").versionRef("testscontainers")
            library("testscontainers-selenium", "org.testcontainers", "selenium").versionRef("testscontainers")

            library("groovy-core", "org.codehaus.groovy", "groovy").versionRef("groovy")
            library("groovy-http-builder", "org.codehaus.groovy.modules.http-builder:http-builder:0.7.1")

            library("rest-spock-core", "org.spockframework:spock-core:1.3-groovy-2.5")
            library("rest-hamcrest", "org.hamcrest:hamcrest-library:1.3")

            library("geb-spock", "org.gebish:geb-spock:5.0")
            library("geb-slf4j", "org.slf4j:slf4j-simple:1.7.35")
            library("spock-core", "org.spockframework:spock-core:2.0-groovy-3.0")

            bundle("selenium", listOf("selenium-api", "selenium-support", "selenium-remote-driver", "selenium-firefox-driver", "selenium-chrome-driver"))
            bundle("testscontainers", listOf("testscontainers-spock", "testscontainers-core", "testscontainers-selenium"))
            bundle("geb", listOf("geb-spock", "spock-core", "geb-slf4j"))
            bundle("resttest", listOf("groovy-core", "groovy-http-builder", "rest-spock-core", "rest-hamcrest"))
        }
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

// define root project name
rootProject.name = "devtraining-icm"

val excludeList = listOf("gradle", ".gradle", "build", "bin", "reports", "buildSrc", "config", "sites", "docker")

val filter: (File, String) -> Boolean = { dir, filename ->
    File(dir, filename).isDirectory &&
            ! excludeList.contains(filename) &&
            ! filename.startsWith(".")
}

settings.rootDir.listFiles( filter )?.forEach { sub ->
    include(":${sub.name}")
}
