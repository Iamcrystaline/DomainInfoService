# DomainInfoService

This project will help you to find registrar name and expiration Date & Time for given domain. If the application cannot find such a registered domain name, it will give you an information about prices to buy this domain.
Application gets price information from namecheap API (https://www.namecheap.com/support/api/methods/users/get-pricing/)

To access the application, just go to http://localhost:8080, print domain name to check in the text field and press "Submit" button!

### Notes
- Given prices are not real, because application gets information from sandbox api, not original
- To run the application you have to register whoisxmlapi and namecheap accounts and pass your personal data to application.properties file

### Prerequisites
- JDK 17
- Maven

### Run & Test
General:
1. Download the project
2. Go to the project main directory
3. Register whoisxmlapi account on https://whois.whoisxmlapi.com/
4. Get your api key and pass it to whoisxmlapi.api.key property in src/main/resources/application.properties
5. Register namecheapapi account on https://www.sandbox.namecheap.com/
6. Get your api key and add your IP address to whitelist
7. Pass api key, IP address and your username to the following properties in src/main/resources/application.properties

To run it locally:
1. `mvn clean install`
2. `java -jar ./target/demo-0.0.1-SNAPSHOT.jar`

To run tests with reports:
1. `mvn clean test`
