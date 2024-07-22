How to Work with an ICM AppServer Customer Project
==================================================

[TOC]: #

# Table of Contents
- [Prerequisites](#prerequisites)
- [Git Checkout](#git-checkout)
- [Set Environment](#set-environment)
- [Component Configurations](#component-configurations)
  - [Web Adapter and Web Adapter Agent](#web-adapter-and-web-adapter-agent)
  - [Database and Database Preparation for Local Environment](#database-and-database-preparation-for-local-environment)
  - [Application Server](#application-server)
  - [Solr Server and ZooKeeper](#solr-server-and-zookeeper)
  - [Mail Server](#mail-server)
- [ICM Test](#icm-tests)
  - [ISH Unit Tests](#ish-unit-tests)
  - [REST Tests](#rest-tests)
  - [Geb Tests](#geb-tests)
- [Workflow Overview](#workflow-overview)
- [Gradle Tasks Overview](#gradle-tasks-overview)
- [IStudio](#istudio)
- [Links for Local Running Applications](#links-for-local-running-applications)

## Prerequisites

- Installed [AdoptOpenJDK 17 HotSpot](https://adoptopenjdk.net)
- Installed Container Runtime:
  - Windows: [Install Docker Desktop on Windows](https://docs.docker.com/desktop/install/windows-install/)
  - Mac: [Install Docker Desktop on Mac](https://docs.docker.com/desktop/install/mac-install/)
  - Linux: [Install Docker Desktop on Linux](https://docs.docker.com/desktop/install/linux-install/)
- IDE with extension for container/Docker ([Intershop Studio](https://repository.cloud.intershop.com/ui/native/studio/com.intershop.studio/), VSCode, IntelliJ)
- Basic knowledge of Docker, Docker Compose and container networking (https://docs.docker.com)

- Access to:
  * Azure DevOps project (https://dev.azure.com/ish-devtraining/devtraining)
  * Azure Artifacts Maven project repositories (https://dev.azure.com/ish-devtraining/devtraining/_artifacts/feed/icm-maven-artifacts)
  - Docker registry with the ICM base containers (https://docker.tools.intershop.com)
  - Docker registry with project containers (ishdvtrnacr.azurecr.io)

- Basic Docker Configuration:
  - 8 GB RAM are recommended (settings: *Resources* | *Advanced*)
  - File sharing of the drive/folder used for development must be enabled (Settings: *Resources* | *File Sharing*)
  - Expose the daemon on port 2375 without TLS (Settings: *General*)

### Docker Login

Do not forget to run `docker login` with your account for the necessary repositories.

**Login for private DockerHub images**:
```
docker login <Docker repository>
```

Retrieve credentials from docker.tools.intershop.com, for details see [Guide - Access to Intershop Docker Images](https://support.intershop.com/kb/index.php/Display/30669A).

**Login for docker.tools.intershop.com**:
```
docker login docker.tools.intershop.com -u <XXX@user-intershop.de> -p <SuperStrongCLISecret>
```

**Login for Docker project registry**:
```
az login
az acr login --name ishdvtrnacr.azurecr.io
```
For more details, see [Authenticate with an Azure container registry](https://learn.microsoft.com/en-us/azure/container-registry/container-registry-authentication?tabs=azure-cli) in the Microsoft Azure Documentation.

## Git Checkout

To clone the directory:

    cd <target directory>
    git clone git@ssh.dev.azure.com:v3/ish-devtraining/devtraining/devtraining-icm
    # or
    git clone https://intershop-com@dev.azure.com/ish-devtraining/devtraining/_git/devtraining-icm

    # branch for Gradle update and Containerization
    git checkout master

For a detailed description, see [Share your code with Git](https://docs.microsoft.com/en-us/azure/devops/user-guide/code-with-git?view=azure-devops) in the Microsoft Azure DevOps Documentation. 

## Set Environment

1. Optional: Set your `GRADLE_USER_HOME` via:

    ```batch
    SET GRADLE_USER_HOME=<DEV_HOME>/gradle
    ```

    or

    ```shell
    export GRADLE_USER_HOME=<DEV_HOME>/gradle
     ```

2. Create a *&lt;GRADLE_USER_HOME&gt;/icm-default/conf/icm.properties* file. For an example, see https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#PropertiesFile.

     > **Notes**:
     >
     > It is possible to change the location of the configuration file using a system property,
     > a project property or an environment variable:
     >
     > - Configuration directory:
     >   - System property: `configDir`
     >   - Project property: `configDir`
     >   - Environment variable: `CONFIGDIR`
     >
     > To do so, you can use variables in a *gradle.properties* file:
     >
     >     systemProp.gradle.configDir = <path to dir>
     >
     > Alternatively, you may set environment variables:
     >
     > ```batch
     > SET CONFIGDIR=<path to dir>
     > ```
     >
     > ```shell
     > export CONFIGDIR=<path to dir>
     > ```

3. Create a *&lt;GRADLE_USER_HOME&gt;/gradle.properties* file, for details see [Gradle Documentation](https://docs.gradle.org/current/userguide/build_environment.html). 

    Add the following entries:

    ```
    repoUser = <Azure login>
    repoPassword = <Azure Personal Access Token>
    ```

__Azure Personal Access Token__

  > You need a personal access token to access Azure Artifacts of the specified Azure DevOps organization.
  > For details, see [Use personal access tokens](https://docs.microsoft.com/en-us/azure/devops/organizations/accounts/use-personal-access-tokens-to-authenticate?view=azure-devops&tabs=Windows) in the Microsoft Azure Documentation.
  > Please check the context for the used token.


## Component Configurations

For testing with ICM, additional components must be started:
- Intershop Web Adapter and Web Adapter agent
- Solr server and ZooKeeper
- Mail server
- Database

The application server must be started in the container as well.

### Web Adapter and Web Adapter Agent
#### Prerequisites
Relevant [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#WAConfiguration) are defined.

#### Starting and Stopping
See tasks `*WA`, `*WAA`, `*WebServer` at https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#DockerPluginTasks.

#### Use TLS Certificates Inside the Web Adapter

1. Obtain wildcard certificates:
    - You can use [Let's encrypt](https://letsencrypt.org).
    - Intershop recommends using wildcard certificates for an easy setup in a team. 

1. Prepare your host:
    - Determine the IP of your host inside the Intershop network:
        - On Windows execute:
          ```batch
          ipconfig /all
          ```
        - On Linux execute:
          ```shell
          ip address
          ```
        - Pick the IP assigned to your LAN or WiFi adapter (not those of virtual network adapters).
    - Map your fully qualified hostname to the IP (must be renewed each time your IP changes, e.g. to enable VPN):
        - Add/modify a line inside the `hosts`-file matching the pattern: `<IP> <fullyQualifiedHostname>`, for example:
          ```
          10.0.206.81 jmmustermannnb.domainname.com
          ```
        - Location of `hosts`-file:
            - Windows: `C:\Windows\System32\drivers\etc\hosts`
            - Linux/Mac: `/etc/hosts`


1. Modify your [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#WAConfiguration) and set the following three properties:  
      ```
      webServer.cert.path = <certificateCheckoutPath>/<ChosenDomain>
      webserver.cert.server = fullchain.pem 
      webserver.cert.privatekey = privkey.pem
      ```

### Database and Database Preparation for Local Environment

In general, the build supports a database that is started with the provided `*MSSQL` Gradle tasks (see https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#DockerPluginTasks), or alternatively an externally started database.

#### Prerequisites
- Relevant [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#DBPrepareConfiguration) are defined.
- The database is started.

#### Execution
See task `dbPrepare` at https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#CustomizationDockerPluginTasks.

### Application Server

#### Prerequisites
- Relevant [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#StartASConfiguration) are defined.
- The database is [started](#database-and-database-preparation-for-local-environment) and properly prepared.

#### Starting and Stopping
See tasks `startAS` and `stopAS` at https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#CustomizationDockerPluginTasks.

### Solr Server and ZooKeeper

> **Note**:  
> As of version `34.0.0`, no external Solr server is supported. Only the one that can be started by the build script is supported.

#### Prerequisites
Relevant [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#SolrConfiguration) are defined.

#### Starting and Stopping
See tasks `*Solr`, `*ZK` and `*SolrCloud` at https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#DockerPluginTasks.

#### Manage Search Index Content
- Clean up the indexes (requires a running Zookeeper, Solr server and AppServer):
  ```shell
  ./gradlew cleanUpSolr
  ```
- Rebuild the indexes (requires a running Zookeeper, Solr server, [Web Adapter](#web-adapter-and-web-adapter-agent) and [Application Server](#application-server)):
  ```shell
  ./gradlew rebuildSearchIndex
  ```
- Solr server back office: <br/> Navigate to `http://localhost:<solr.port>/solr/`.  

> **Note**:  
> The search index has the same life cycle as the Solr server container. When the container is stopped, the index is lost, so a rebuild is required after each Solr server (re)start.

For more information, see:
- [Cookbook - Content Search Configuration](https://support.intershop.com/kb/26U072)
- [Cookbook - Product Search Configuration](https://support.intershop.com/kb/272Q80)

### Mail Server

#### Prerequisites
Relevant [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#MailConfiguration) are defined.

#### Starting and Stopping
See tasks `*MailSrv` at https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#DockerPluginTasks.

#### Mail Server Administration
The configuration uses a container of MailHog, see [GitHub | MailHog](https://github.com/mailhog/MailHog). For more information, visit the GitHub page or [mailtrap.io | MailHog Tutorial](https://mailtrap.io/blog/mailhog-explained/).

## ICM Tests
### ISH Unit Tests
#### Prerequisites
- Relevant [`icm.properties`](https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#ISHUnitTestConfiguration) are defined.
- The [Solr server + Zookeeper](#solr-server-and-zookeeper) are started.
- The database is [started](#database-and-database-preparation-for-local-environment) and properly prepared.
- The [application server](#application-server) server is **NOT** started.

#### Execution
See tasks `ishUnitTestReport` and `*ISHUnitTest` at https://github.com/IntershopCommunicationsAG/icm-docker-plugin/blob/master/README.asciidoc#CustomizationDockerPluginTasks.

#### Define a New Test Suite

To define a new test suite, edit the file `build.gradle.kts`.

Add a new `register`-block in into `intershop_docker.ishUnitTests` defining:
   - Test suite name
   - Test cartridge name
   - The test suite class

Example:
     
  ```
  register("my_cartridge_test") {
    cartridge.set("my_cartridge_test")
     testSuite.set("tests.embedded.com.intershop.mypackage.SuiteMyTests")
  }
  ```

### REST Tests

There are currently no REST tests available in the demo store.

#### Prerequisites
* The [Solr server + Zookeeper](#solr-server-and-zookeeper) are started.
* The database is [started](#database-and-database-preparation-for-local-environment) and properly prepared.
* The [Web Adapter](#web-adapter-and-web-adapter-agent) is started and uses [TLS certificates](#use-tls-certificates-inside-the-web-adapter).
* The [application server](#application-server) is started.
* The [search index](#manage-search-index-content) has been built.

#### Execution of All Tests
  ```shell
  gradlew restTest
  ```
In case of failing tests the reports for each project containing executed REST tests can be found at:
  ```
  <buildRoot>/<projectName>/target/reports/tests/restTest
  ``` 

#### Execution of Tests Contained Inside a Certain Project
  ```shell
  gradlew <projectName>:restTest
  ```

#### Execution of Tests Specified by Pattern

The task `restTest` is of type `org.gradle.api.tasks.testing.Test` and therefore supports the [--tests](https://docs.gradle.org/current/userguide/java_testing.html#test_filtering) parameter.
For example:

  ```shell
  gradlew restTest --tests=*SuggestRestSpec*
  ```
In case of failing tests, the reports for each project containing executed REST tests can be found at:
  
  ```
  <buildRoot>/<projectName>/target/reports/tests/restTest
  ```
  
### Geb Tests
#### Prerequisites
* The [Solr server + Zookeeper](#solr-server-and-zookeeper) are started.
* The database is [started](#database-and-database-preparation-for-local-environment) and properly prepared.
* The [Web Adapter](#web-adapter-and-web-adapter-agent) is started and uses [TLS certificates](#use-tls-certificates-inside-the-web-adapter).
* The [application server](#application-server) is started.
* The [search index](#manage-search-index-content) has been built.
* A running and configured mail server (see [Mail Server](#mail-server)).

#### Execution of All Tests

Run the following command to execute all tests:
  ```shell
  gradlew gebTest
  ```
In case of failed tests, the reports for each project containing the executed tests can be found at:
  ```
  <buildRoot>/<projectName>/target/reports/tests/gebTest
  ``` 

#### Execution of Tests Contained Inside a Certain Project

Run the following command to execute tests within a certain project:
  ```shell
  gradlew <projectName>: gebTest
  ```

### Additional Demo Data Extension
Intershop provides a demodata customization for special use cases.

To enable the customization:
1. Change the gradle.properties of the project from:
    ```gradle.properties
    demoDataVersion =
    # demoDataVersion = 1.8.0
    ```
    to:
    ```gradle.properties
    demoDataVersion = 1.8.0 
    ```
1. Add `ft_demo` to the cartridge list configuration.

There are two options to extend the cartridge list configuration:
- Add the additonal configuration to the `build.gradle.kts` file:
   ```
   cartridgeList.set(setOf("ft_production", "ft_demo"))
   ```
- Add the dependency to the `ft_production` build file:
   ```
   cartridgeRuntime "com.intershop.demodata:ft_demo"
   ```

## Workflow Overview

1. Checkout the repository.
1. Adapt your configuration.
1. Start your database.
1. Run `./gradlew startServer` (`dbPrepare` is embedded).

`dbPrepare` and `startServer` combine all compile tasks.

## Gradle Tasks Overview

This is a short overview over the main tasks. For more detailed
information, refer to:
- [icm-gradle-plugin](https://github.com/IntershopCommunicationsAG/icm-gradle-plugin)
- [icm-docker-plugin](https://github.com/IntershopCommunicationsAG/icm-docker-plugin)

Tasks:

- **createServerInfoProperties**
  - The task creates a file: *$buildDir/tempConf/version.properties*.
  - This file contains version information of the assembly.
  - This is a replacement of the former *ivy.xml* of assembly.

- **installConfFiles**
  - The task collects conf files of a development environment in
    *$buildDir/conf*.
  - This is combined copy from *icm_config*.

- **createServerDirProperties**
  - The task creates a *properties* file including information about several files in the build directory.
  - The *properties* file stores information on:
    - Configuration directory path
    - Sites folder path
    - etc.
  - It replaces the former configuration in different shared and local directories.

- **dbPrepare**
  - This task executes a `dbPrepare`.
  - It collects all necessary paths and configurations for `dbPrepare`.
  - It replaces the former command `dbinit`.
  - This command runs in a container. All necessary files and
    directories will be mounted by this container.

- **ishUnitTestReport**
  - This task executes all ISH Unit tests and creates a report.

    `b2bISHUnitTest` starts the ISH Unit tests from the b2b configuration.

  - The command runs in a container. All necessary files and directories
    will be mounted by this container.

- **startServer**
  - This task starts web server, Web Adapter agent und application server.
    Web server and Web Adapter agent are started in the Docker runtime on the local machine.
  - This task starts `startAS` and `startWebServer`.
  - Add the `--debug-jvm` parameter for opening the debug port 5005.

- **startAS**
  - This task starts the application server from sources.
  - It collects all necessary paths and configuration for the start up.
  - Add the `--debug-jvm` parameter for opening the debug port 5005.

- **stopServer**
  - This task stops web server, Web Adapter agent und application server.
  - This task stops `stopAS` and `stopWebServer`.

- **stopAS**
  - This task stops the running application server.
  - It collects all necessary paths and configurations for the stop process.

- **startMailSrv / stopMailSrv**
  - These tasks start / stop the MailHog SMTP server, see [Github | MailHog](https://github.com/mailhog/MailHog).

- **startMSSQL / stopMSSQL**
  - These tasks start a containerized MSSQL server prepared for ICM.

- **startSolrCloud / stopSolrCloud**
  - These tasks start Zookeeper und Solr images for a single node SolrCloud instance.

- **rebuildSearchIndex**
  - This task calls the application server job to rebuild the search index.
  - It starts before a task to remove the old collections and configurations.
  - This task is automatically called by the `gebTest` task.

- **getTest**
  - Runs the Geb and Spock tests with the tests container framework by default.
  - If the server is not yet started, all necessary containers will be started.

- **buildImages**
  - Creates all available Docker images.

- **containerClean**
  - Removes all containers started for the current project.

## IStudio

Version >= IntershopStudio_4.17.0.30

### Preparation

- Verify that AdoptOpenJDK 17 is being used.
- Configure the directories containing the *icm.properties* before starting IStudio:

Windows:

```batch
SET CONFIGDIR=<path to dir>
 ```

Linux:

```shell
export CONFIGDIR=<path to dir>
 ```

### Debug Pipelines

The HTTP and HTTPS ports of the pipeline debug configuration must match to the
HTTP and HTTPS ports in the *icm.properties* file.

### Debug Java Code

The task `dbPrepare`, `testIshUnit`, `startAS` and `startServer` can be started with the option `--debug-icm`. 

Supported values are: 
- `true`/`yes`: Enable debugging
- `suspend`: Enable debugging in suspend-mode
- Every other value: Disable debugging

The IDE can connect via port `7746` (customizable via `icm.properties/intershop.as.debug.port`).

## Links for Local Running Applications

  * [SMC](http://localhost:8080/INTERSHOP/web/BOS/SMC/-/-/-/SMCMain-Start)
  * [Backoffice](http://localhost:8080/INTERSHOP/web/WFS/SLDSystem)
  * [inSPIRED Site](http://localhost:8080/INTERSHOP/web/WFS/inSPIRED-inTRONICS-Site/en_US/-/USD/)
  * [inSPIRED Business Site](http://localhost:8080/INTERSHOP/web/WFS/inSPIRED-inTRONICS_Business-Site/en_US/-/USD/)