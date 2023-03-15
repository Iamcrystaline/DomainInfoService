# DomainInfoService

This project will help you to find registrar name and expiration Date & Time for given domain. If the application cannot find such a registered domain name, it will give you an information about prices to buy this domain.
Application gets price information from namecheap API (https://www.namecheap.com/support/api/methods/users/get-pricing/)

To access the application, just go to http://localhost:8080, print domain name to check in the text field and press "Submit" button!

### Prerequisites
- JDK 17
- Maven

### Run & Test
General:
1. Download the project
2. Go to the project main directory

To run it locally:
1. `mvn clean install`
2. `java -jar ./target/demo-0.0.1-SNAPSHOT.jar`

To run tests with reports:
1. `mvn clean test`