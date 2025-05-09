Add read me file
# cognizant-mvc-coding-test

## Prerequisites
- ** >=JDK8 installed      **
## TechStack Used:
1. Selenium WebDriver
2. Programming : Java
3. Junit
4. BDD: Cucumber feature file
5. Build Tool- Maven
6. Logging: log4j

## Usage
- Clone the repo      :
  ```git clone git@github.com:gmbhargav/ui-selenium-bdd-framework.git```
- Run Below command to execute automation suite on different browsers:
    - Chrome (default): ```mvn verify -Dbrowser=chrome -Dcucumber.filter.tags=@todo_tasks```
    - Chrome Headless : ```mvn verify -Dbrowser=chrome-headless -Dcucumber.filter.tags=@todo_tasks```
    - Firefox         : ```mvn verify -Dbrowser=firefox -Dcucumber.filter.tags=@todo_tasks```

- Test Scenarios available under                : ```src/test/resources/features/001-todo-mvc.feature```
- Test Steps(step definitions) available under  : ```src/test/java/ui/steps/ToDoStepDef.java```
- Page Locators & Action methods available under: ```src/main/java/ui/page/MvcTodoPage.java```

## Test Results Report
- Refer to the below path for cucumber reports:
  ``` reports/cucumber-report.html```
    - Open ```reports/cucumber-advanced-reports/cucumber-html-reports/overview-features.html```  for the detailed report
    - Screen shots captured and attached to the html report after every scenario.
## Logs
- Refer to ```logger.log``` file for the logs
