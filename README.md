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
1. copy *bmp.jar* to a target directory
1. if the standard configuration is not sufficient then create a file '*application.properties*' in the target directory and change the file accordingly.

## Configuration

The following properties can be set as application configuration properties (file *application.properties*). 

Property | Description | Default
--- | --- | ---
fee.per.byte | Fee per byte in satoshis | 100

## Run Application
### First Run
The first time the application creates a wallet which needs to be filled. So perform the following steps:

1. Make sure that a local testnet node is running;
1. start the application:


    java -jar bmp.jar
1. Check the file 'logs/bmp.log' and look for the text: 


    Wallet address is: <address>

1. Use the given *address* to send some BTC to it (for example via <https://testnet.manu.backend.hamburg/faucet>)
1. The application waits till the transaction to the given *address* has at least 1 confirmation (for example via <https://testnet.blockexplorer.com/>)

### Next Runs
1. Make sure that a local testnet node is running
1. start the application:


    java -jar bmp.jar <value>

If there are no problems, the application stops normally and the *value* is added to the testnet blockchain.
The logging of the application is sent to the file 'logs/bmp.log' if the flow of the application has to be followed.

# Design
Java is used as 'programming language of choice,' in combination with the industry standard framework *Spring Boot*
and the standard bitcoin java library *BitcoinJ*. As build tool '*maven*' is used (a Java industry standard as well).

- java 8 (1.8.0_151 or higher)
- Spring Boot (<http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-documentation>)
- BitcoinJ (<https://bitcoinj.github.io>)
- Maven (<https://maven.apache.org>)

The starting point is the class '*com.ximedes.bitcoin.miniproject.Application*' and the actual work is being done in the 3 service classes:

Class | Description
--- | ---
FeeService | calculate the required fee for a given transaction
StoreService | store a given value in the blockchain
WalletService | handles the wallet (keys) and transaction management (creating, sending)

Most of the actual Bitcoin 'heavy lifting' is done by BitcoinJ. So a BitcoinJ wallet is used, an SPV client, for wallet handling, persisting
it to a file and the actual transaction handling (fee, creating  inputs and outputs, etc.)

## StoreService
For storing of a given value on the chain, the following is done:

- the Bitcoin operation *OP_RETURN* is used to store the given value;
- As the room for OP_RETURN is limited, and the length of the *value* parameter is not known in advance, we need to handle the case where the *value* needs more room than is available.

To guarantee that the *value* always fits, an SHA-256 hash of the *value* is stored, so the size of the 'value to be stored' is fixed and independent
of the *value*. The code is prepared to store *value* directly into the blockchain (parameter 'hashed' in method StoreService.store()).

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

1. Create a Spring-boot configuration and start your project main class *Application*
1. If you want to use external files for properties, add the following to your VM options (we follow the spring boot conventions)


    --spring.config.location=config/${user.name}/ --logging.config=config/${user.name}/logback-spring.xml