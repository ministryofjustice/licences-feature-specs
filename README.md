# licences-feature-specs
End-to-end tests for the Licences Application

## Pre-Requisites

### Environment
The following environment variables must be set:

* LICENCES_URI - root URI for the Licences application. Defaults to `http://localhost:3000`
* TEST_DB_SERVER - eg localhost
* TEST_DB_USER - eg SA
* TEST_DB_PASS - eg password
* TEST_DB - eg licences

Note that you can set values for the Database environment variables in a config.properties file in the project root.

The Licences application must be running at Licences_URI, and must be connected to a database and a nomis api or
suitable mocks.

## Execution

Run using `./gradlew test` or execute a specific test using your IDE

In src.test/resources/GebConfig.groovy you can change from headless mode (Phantom JS)
to browser mode with ChromeDriver. If not using the bundled Linux ChromeDriver, set the
webdriver.chrome.driver property with your ChromeDriver path.