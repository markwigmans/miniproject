# Bitcoin mini Project

## Requirements
Using the programming language of your choice implement a program that takes one string argument and 
publishes/stores that string on Bitcoin's blockchain.

The program should:

- publish on testnet
- assume a local Bitcoin testnet node is running
- calculate the appropriate fees

### Notes
- you will submit max 2 files, the source code (ZIP or TGZ for multiple source files) and an 
  executable (e.g. for static languages)
- If an executable is submitted it should be compiled for x86_64 (if on linux) or x86_32 (on Windows). 
  Unfortunately, I cannot check executables on Mac so make sure you use an interpreted language if you work on a Mac.
- the source code is your main submission and it should contain everything you want to share with me. 
  It should include detailed comments and everything else you think I should be aware of.

# Development

## Prerequisites
1. Make sure you have Java 8 SDK installed (version 1.8.0_151 or higher)
1. Add the Lombok plugin to your ide
1. Use IntelliJ to clone the code at: (replace ``{username}`` with your *username*)


    git clone https://github.com/markwigmans/miniproject.git


The software is tested with 'crypto.policy=unlimited' to support (unlimited strength JCE). See 
http://www.oracle.com/technetwork/java/javase/8all-relnotes-2226344.html#R180_151

## Build en Run

1. Checkout the project
1. Create a new config directory in: **{project-root}**/config/**{username}**/
1. Create a properties.properties file in: **{project-root}**/config/**{username}**/
1. Build your project with:
        
        
    mvnw clean install

1. Create a Springboot configuration and start your project main class *Application*

1. If you want to use external files use properties add the following to your VM options (we follow the spring boot conventions)


    --spring.config.location=config/${user.name}/ --logging.config=config/${user.name}/logback-spring.xml
  

## Configuration

The following properties can be set as application configuration properties (file application.properties)

Property | Description | Default
--- | --- | ---
fee.per.byte | Fee per byte in satoshis | 100

  
## Design

- java 8 (1.8.0_151 or higher)
- Spring Boot (http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-documentation)