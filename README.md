# Bitcoin Mini Project

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

# Run

## Prerequisites
1. Make sure you have Java 8 JRE installed (version 1.8.0_151 or higher <http://www.oracle.com/technetwork/java/javase/downloads/index.html>)

## Installation
Perform the following steps:
1. copy *bmp.jar* to target directory
1. if standard configuration is not sufficient then create a file '*application.properties*' in the target directory and change the 'application.properties' accordingly.

## Configuration

The following properties can be set as application configuration properties (file *application.properties*). 
The notation in the file is: <property>=<value'

Property | Description | Default
--- | --- | ---
fee.per.byte | Fee per byte in satoshis | 100

## Run Application
### First Run
The first time the application creates a wallet which needs to be filled. So perform the following steps:

1. Make sure that a local testnet node is running
1. start application:


    java -jar bmp.jar
1. Check the file 'logs/bmp.log' and look for the text: 


    Wallet address is: <address>

1. Use the given *address* to send some BTC to it (for example via <https://testnet.manu.backend.hamburg/faucet>)
1. The application waits till the transaction to the given *address* has at least 1 confirmation (for example via <https://testnet.blockexplorer.com/>)

### Next Runs
1. Make sure that a local testnet node is running
1. start application:


    java -jar bmp.jar <value>
    
If the are no problems, the application stops normally and the *value* is added to the testnet blockchain.
The logging of th application is send to the file 'logs/bmp.log' if the flow of the application has to be followed.

# Design
Java is used as 'programming language of choice', in combination with the industry standard framework *Spring Boot* and 
the standard bitcoin java libary BitcoinJ.

- java 8 (1.8.0_151 or higher)
- Spring Boot (<http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-documentation>)
- BitcoinJ (<https://bitcoinj.github.io>)




# Development
## Prerequisites
1. Make sure you have Java 8 SDK installed (version 1.8.0_151 or higher)
1. Add the Lombok plugin to your IDE
1. Use IntelliJ to clone the code at: (replace ``{username}`` with your *username*)


    git clone https://github.com/markwigmans/miniproject.git
    
The software is tested with 'crypto.policy=unlimited' to support (unlimited strength JCE). See 
<http://www.oracle.com/technetwork/java/javase/8all-relnotes-2226344.html#R180_151>    
    
## Build en Run

1. Checkout the project
1. Create a new config directory in: **{project-root}**/config/**{user.name}**/
1. Create a properties.properties file in: **{project-root}**/config/**{user.name}**/
1. Build your project with:
        
        
    mvnw clean install

1. Create a Springboot configuration and start your project main class *Application*
1. If you want to use external files use properties add the following to your VM options (we follow the spring boot conventions)


    --spring.config.location=config/${user.name}/ --logging.config=config/${user.name}/logback-spring.xml