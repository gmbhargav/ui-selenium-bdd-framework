package ui.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.config.Browsers;
import ui.page.MvcTodoPage;
import ui.utils.LoggerUtil;

public class ToDoStepDef {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoStepDef.class);
    WebDriver driver;
    LoggerUtil loggerUtil;
    MvcTodoPage mvcTodoPage;


    public ToDoStepDef() {
        this.driver = Browsers.getDriver();
        this.mvcTodoPage = new MvcTodoPage(driver);
        this.loggerUtil = new LoggerUtil();
    }

    @Before
    public void setUp(Scenario scenario) {
        loggerUtil.configureLogging();
        LOGGER.info("-------Session Started-------");
    }

    @Given("I launch the MVC site")
    public void iLaunchTheMVCSite() {
        Browsers.launchUrl("https://todomvc.com/examples/react/dist");
    }

    @When("I Add {string} Todo task")
    public void iAddTodoTask(String task) {
        Assert.assertTrue("Unable to add task", mvcTodoPage.addTask(task));
        LOGGER.info("Task {} added successfully", task);
    }

    @Then("I should see {string} task available under {string} todo list")
    public void iShouldSeeTaskAvailableUnderTodoList(String task, String filter) {
        Assert.assertTrue("Unable to verify task", mvcTodoPage.verifyTaskDisplayed(task, filter));
        LOGGER.info("Task {} displayed as expected under {} filter", task, filter);
    }

    @When("I select {string} filter")
    public void iSelectFilter(String filter) {
        Assert.assertTrue("Unable to select filter", mvcTodoPage.selectFilter(filter));
        LOGGER.info("Filter {} selected as expected ", filter);
    }

    @And("I should see number of items left count is matching with active tasks")
    public void iShouldSeeNumberOfItemsLeftCountIsMatchingWithActiveTasks() {
        int numOfItemLeft = mvcTodoPage.getNumberOfItemsLeft();
        int numOfActiveTasks=mvcTodoPage.getActiveTasks();
        Assert.assertEquals("number of items left count not matching", numOfItemLeft, numOfActiveTasks);
        LOGGER.info("Number of items left count matching expected {} actual {}", numOfActiveTasks, numOfItemLeft);

    }

    @When("I select {string} Todo task as completed")
    public void iSelectTodoTaskAsCompleted(String task) {
        Assert.assertTrue("Unable to mark task as completed", mvcTodoPage.completeTask(task));
        LOGGER.info("Task {} marked as completed as expected", task);
    }

    @Then("I should see {string} task marked as completed")
    public void iShouldSeeTaskMarkedAsCompleted(String task) {
        Assert.assertTrue("Unable to see task as completed", mvcTodoPage.verifyTaskAsCompleted(task));
        LOGGER.info("Task {} marked as completed as expected", task);
    }

    @Then("I should see number of items left displayed")
    public void iShouldSeeNumberOfItemsLeftDisplayed() {
        Assert.assertTrue("Number of items left count not displayed ", mvcTodoPage.verifyNumberOfItemsLeftDisplayed());
        LOGGER.info("Number of items left count displayed as expected ");
    }

    @Then("I should see number of items left count {string} by {int}")
    public void iShouldSeeNumberOfItemsLeftCountBy(String type, int cnt) {
        int numOfItemLeft = mvcTodoPage.getNumberOfItemsLeft();
        int expectedNumberOfItems;
        if (type.toLowerCase().contains("increase")) {
            expectedNumberOfItems = mvcTodoPage.actNumberOfItems + cnt;
        } else {
            expectedNumberOfItems = mvcTodoPage.actNumberOfItems - cnt;
        }
        Assert.assertEquals("number of items left count not matching", numOfItemLeft, expectedNumberOfItems);
        LOGGER.info("Number of items left count matching expected {} actual {}", expectedNumberOfItems, numOfItemLeft);

    }

    @When("I rename {string} Todo task as {string}")
    public void iRenameTodoTaskAs(String task, String newTask) {
        Assert.assertTrue("Unable to rename task", mvcTodoPage.renameTask(task,newTask));
        LOGGER.info("Task {} renamed as expected", task);
    }

    @And("I should see {string} task not available under {string} todo list")
    public void iShouldSeeTaskNotAvailableUnderTodoList(String task, String filter) {
        Assert.assertFalse("Unable to verify task", mvcTodoPage.verifyTaskDisplayed(task, filter));
        LOGGER.info("Task {} not displayed as expected under {} filter", task, filter);
    }

    @When("I mark all tasks as completed")
    public void iMarkAllTasksAsCompleted() {
        Assert.assertTrue("Unable to mark tasks as completed", mvcTodoPage.markAllTaskAsCompleted());
        LOGGER.info("All Tasks marked as completed as expected");
    }

    @Then("I should see all tasks marked as completed")
    public void iShouldSeeAllTasksMarkedAsCompleted() {
        Assert.assertTrue("All tasks not marked as completed ", mvcTodoPage.verifyAllTaskAsCompleted());
        LOGGER.info("All tasks marked as completed as expected ");
    }

    @And("I should see {int} number of items left displayed")
    public void iShouldSeeNumberOfItemsLeftDisplayed(int count) {
        int numOfItemLeft = mvcTodoPage.getNumberOfItemsLeft();
        Assert.assertEquals("number of items left count not matching", numOfItemLeft, count);
        LOGGER.info("Number of items left count matching expected {} actual {}", count, numOfItemLeft);
    }

    @When("I clear all completed tasks")
    public void iClearAllCompletedTasks() {
        Assert.assertTrue("Unable to clear completed tasks", mvcTodoPage.clearCompletdTask());
        LOGGER.info("Cleared all completed tasks as expected ");
    }

    @After
    public void tearDown(Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
        String screenShotName=scenario.getName().replaceAll(" ", "_");
        scenario.attach(screenshot, "image/png",screenShotName); // ... and embed it in the report.
        LOGGER.info("-------Screen shot added-------");
    }
}
