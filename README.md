# Routing-integration-tests

**API Test Automation Framework: Tips**

_The objective of this project is to provide API tests to Trips endpoint._

**Setup**

_The test framework is built using cucumber BDD (https://cucumber.io/). The texts are written in Gherkin format. The build tool used is Gradle. Test are implemented in Kotlin.
Open source library Rest Assured is used to perform rest operations._

**Test Approach**

Cucumber is used for BDD-style test scenarios.

Example: Get trip details with valid trip Id
Feature File (src/test/resources/features/Trip.feature):
```
Scenario: Get trip details with valid trip Id
    Given user had booked a trip with id "a12c34e5-678d-90ef-1234-56789abcdefg"
    When check that service returned 200
    Then validate the response
```
**Prerequisite**

- Install JDK version greater than 11: https://java.com/en/download/help/java_mac.html

- Install gradle: https://gradle.org/install/

- Preferred IDE is intelliJ. Install cucumber for java and Gherkin plugin to view test in BDD syntax.

**Setting up the repository on local machine**

- makedir workspace
- cd workspace
- git clone git@github.com:mobimeo/routing-integration-tests.git

**Run the tests:**

Syntax: *gradle cucumber -Ptag=@<tag_name>*

To run `tripCount`, navigate to the directory where you clone the git repository.
Run the command: 
```
gradle cucumber -Ptag=@tripCount
```
To run script: activeTrips
```
gradle cucumber -Ptag=@activeTrip
```
**Reports**

On completion of the tests, cucumber genereate HTML and JSON reports. The test results get stored on /<project_directory>/cucumberReports. Select index.html to view the
test results.

