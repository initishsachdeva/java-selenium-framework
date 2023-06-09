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
* under project/ directory create screenshots(folder) - the screenshots captured during execution are stored under this
  directory
* under project/ directory create reports(folder) - reports configured under framework will trigger under this directory
* under project/directory create log4j.properties file - will include the details regrading logs
* under project/directory create config.properties file - will include details of baseURL, username and password
* under project/directory create testng.xml file

## Reporting Tool used in framework to trigger the execution report :

```
 Extent Report
 Allure Report
 ReportPortal Report
```

## To Configure and Trigger Extent Reports in the framework

* include the dependency in pom.xml file
   ```
   <dependency>
     <groupId>com.aventstack</groupId>
     <artifactId>extentreports</artifactId>
     <version>5.0.0</version>
  </dependency>
  ```
* Add below details under your Base Class
  ```
   @BeforeClass
    @Parameters("browser")
    public void setup(String browserName, ITestContext context) {
        log = Logger.getLogger("java-selenium-framework");
        PropertyConfigurator.configure("log4j.properties");
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } 
        driver.manage().window().maximize();
        driver.get(baseUrl);
        extentTest = extentReports.createTest(context.getName());
    }

    @BeforeSuite
    public void initializeExtentReports() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/" + "Automation_Test_Report.html");

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Java Selenium Framework Test Project"); // Tile of report
        sparkReporter.config().setReportName("Functional Test Automation Report"); // name of the report
        sparkReporter.config().setTimeStampFormat("dd-MM-yy");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Host name", "localhost");
        extentReports.setSystemInfo("Environment", "Automation Testing");
        extentReports.setSystemInfo("user", "Nitish");
    }

    @AfterSuite
    public void generateExtentReports() throws IOException {
        extentReports.flush();
        Desktop.getDesktop().browse(new File("reports/Automation_Test_Report.html").toURI());
    }


    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = null;
            String testName = result.getTestContext().getName() + "  " + result.getMethod().getMethodName();
            screenshotPath = takeScreenshot(result.getTestContext().getName() + "  " + result.getMethod().getMethodName());
            extentTest.addScreenCaptureFromPath(screenshotPath);
            extentTest.fail(result.getThrowable());
         } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(result.getMethod().getMethodName());
        }

    }
  ```

* Run the test using testng.xml file, and you will see the reports folder created which include the extent report html
  file

## To Configure and Trigger Allure Reports in the framework

* include the dependency in pom.xml file
    ```
  <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-testng</artifactId>
            <version>2.21.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectj.version}</version>
        </dependency>

  <!-- include plugin details under build section in pom.xml file-->
   <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                    <argLine>
                        -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
                    </argLine>
                </configuration>
            </plugin>
    
   ```
* Then run your test using command -
   ```
   mvn clean test
  ```
* The json file will be generated under folder allure-results in the project directory. To view them as a HTML content
  type command -
   ```
   allure generate allure-results --clean -o allure-report
  ```

* OR you can create a bat file OpenReports.bat which will have below command. Once allure-results is created just run
  the bat file,
  it will open the Allure Report
  ```
  allure serve
  ```
* allure-report folder will be created, expand the folder, and you will see index.html file which is generated by Allure
  Report.
* Open the index.html file and compare your results.
* To attach screenshots to the Allure Report for the failed test cases, add below details under your BaseClass having a
  method with @AfterMethod Annotation
  ```
   @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getTestContext().getName() + "  " + result.getMethod().getMethodName();
            // to attach screenshots to allure report
            Allure.addAttachment(UUID.randomUUID().toString() + " " + testName, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }
  ```

## To Configure and Trigger ReportPortal in the framework

* include the dependency in pom.xml file
  ```
    <dependency>
            <groupId>com.epam.reportportal</groupId>
            <artifactId>agent-java-testng</artifactId>
            <version>5.2.0</version>
        </dependency>
  ```

* Under src/main/resources folder create reportportal.properties file with followingdetails
  ```
  rp.endpoint=http://example.com
  rp.uuid=api-key
  rp.launch=launch-name
  rp.project=project-name
  ```
* Add below listener in testng.xml class
  ```
  <listeners>
        <listener class-name="com.epam.reportportal.testng.ReportPortalTestNGListener"/>
    </listeners>
  ```
* Run your test using testng.xml and you will see execution status on the server of report portal
* To see report portal execution status using maven command , add below plugin details in your pom.xml file
  ```
    <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <properties>
                        <property>
                            <name>usedefaultlisteners</name>
                            <value>false</value> <!-- disabling default listeners is optional -->
                        </property>
                        <property>
                            <name>listener</name>
                            <value>com.epam.reportportal.testng.ReportPortalTestNGListener</value>
                        </property>
                    </properties>
                </configuration>
            </plugin>
  ```
* Once plugin is configured, just run your test with `mvn clean test`

## To run the whole suite, type below command on terminal

```
mvn clean test
```

