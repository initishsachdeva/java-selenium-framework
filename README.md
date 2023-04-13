# java-selenium-framework

This Selenium framework is created using java as a language and testng as a testing framework.

## Follow below Steps to create a similar project
Step 1 : Create a new maven project and install/import required packages/plugins in pom.xml.

* Selenium : Selenium libraries
* WebDriverManager : Browser extensions
* Testng : Unit Test Framework
* Extent Reports : Reporting purpose
* Apache POI : Read/write data from excel

Step 2 : Create a folder structure

* Project Name
* under src/test/java create components(package) - will contain details of unique features
* under src/test/java create pageObject(package)
* under src/test/java create tests(package) - will contain all the test files
* under src/test/java create services(package) - will contain all the utilities files
* under project/ directory create logs(folder) - the logs generated during execution are store under this directory
* under project/ directory create screenshots(folder) - the screenshots captured during execution are stored under this directory
* under project/ directory create reports(folder) - reports configured under framework will trigger under this directory

## Reporting Tool used in framework to trigger the execution report :

```
 Extent Report
 Allure Report
 ReportPortal Report
```
