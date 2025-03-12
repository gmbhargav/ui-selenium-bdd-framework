package ui.page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class MvcTodoPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MvcTodoPage.class);
    WebDriver driver;
    WebDriverWait webDriverWait;
    public int actNumberOfItems;

    public MvcTodoPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @FindBy(id = "todo-input")
    WebElement inputNewTodo;

    @FindBy(xpath = "//ul[@class='todo-list']/li")
    List<WebElement> listTasks;

    @FindBy(xpath = "//li[@data-testid='todo-item']")
    List<WebElement> listActiveTasks;

    @FindBy(xpath = "//li[contains(@data-testid,'todo')]//label")
    List<WebElement> labelsTasks;

    @FindBy(className = "todo-count")
    WebElement labelItemsLeft;

    @FindBy(className = "clear-completed")
    WebElement btnClearCompleted;

    @FindBy(xpath = "//li[@data-testid='todo-item']//input[@class='new-todo']")
    WebElement inputRenameTask;

    @FindBy(id = "toggle-all")
    WebElement btnMarkAllCompleted;

    public boolean addTask(String task) {
        boolean flag = false;
        try {
            waitTillElementIsPresent(inputNewTodo);
            inputNewTodo.sendKeys(task);
            inputNewTodo.sendKeys(Keys.ENTER);
            this.actNumberOfItems=this.getNumberOfItemsLeft();
            if (this.actNumberOfItems > 0) {
                flag = true;
            }
        } catch (Exception e) {
            LOGGER.error("Exception {} while adding task {}", e, task);
        }
        return flag;
    }

    public boolean verifyTaskDisplayed(String task, String filter) {
        boolean flag = false;
        String actTask;
        String locator = "//ul[@class='filters']//a[text()='" + filter + "']";
        try {
            this.driver.findElement(By.xpath(locator)).click();
            for (WebElement element : labelsTasks) {
                actTask = element.getText().trim();
                LOGGER.info("Actual Task name {} ", actTask);
                if (actTask.equalsIgnoreCase(task)) {
                    flag = true;
                    LOGGER.info("Actual Task name {}, expected task name {} ", actTask, task);
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception {} while verifying task {}", e, task);
        }
        return flag;
    }

    public boolean selectFilter(String filter) {
        boolean flag = false;
        String locator = "//ul[@class='filters']//a[text()='" + filter + "']";
        try {
            this.driver.findElement(By.xpath(locator)).click();
            flag = true;
        } catch (Exception e) {
            LOGGER.error("Exception {} while selecting filter {}", e, filter);
        }
        return flag;
    }

    public boolean completeTask(String task) {
        boolean flag = false;
        String locator = "//label[text()='" + task + "']/preceding-sibling::input[@type='checkbox']";
        try {
            this.driver.findElement(By.xpath(locator)).click();
            flag = true;
        } catch (Exception e) {
            LOGGER.error("Exception {} while completing task {}", e, task);
        }
        return flag;
    }

    public boolean renameTask(String task, String newTask) {
        boolean flag = false;
        String locator = "//li[@data-testid='todo-item']//label[text()='" + task + "']";
        try {
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath(locator)))
                    .doubleClick().build().perform();
            LOGGER.info("double clicked on {} ", task);
            for (int i = 0; i < task.length(); i++) {
                inputRenameTask.sendKeys(Keys.BACK_SPACE);
            }
            inputRenameTask.sendKeys(newTask);
            inputRenameTask.sendKeys(Keys.ENTER);
            flag = true;
        } catch (Exception e) {
            LOGGER.error("Exception {} while renaming task {}", e, task);
        }
        return flag;
    }

    public boolean verifyTaskAsCompleted(String task) {
        boolean flag = false;
        String locator = "//li[@class='completed']//label[text()='" + task + "']";
        try {
            WebElement element = this.driver.findElement(By.xpath(locator));
            return waitTillElementIsPresent(element);
        } catch (Exception e) {
            LOGGER.error("Exception {} while verifying task {}", e, task);
        }
        return flag;
    }

    public int getNumberOfItemsLeft() {
        waitTillElementIsPresent(labelItemsLeft);
        String itemsTxt = labelItemsLeft.getText().trim();
        LOGGER.info("items left text {}", itemsTxt);
        String[] items = itemsTxt.split(" ",0);
        LOGGER.info("items left count {}", items[0]);
        return Integer.parseInt(items[0]);
    }

    public boolean verifyNumberOfItemsLeftDisplayed() {
        waitTillElementIsPresent(labelItemsLeft);
        return (labelItemsLeft.isDisplayed());
    }

    public boolean clearCompletdTask() {
        boolean flag = false;
        try {
            btnClearCompleted.click();
            flag = true;
        } catch (Exception e) {
            LOGGER.error("Exception {} clearing completed tasks", e);
        }
        return flag;
    }


    public boolean waitTillElementIsPresent(WebElement element) {
        try {
            webDriverWait
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean markAllTaskAsCompleted() {
        boolean flag = false;
        try {
            waitTillElementIsPresent(btnMarkAllCompleted);
            btnMarkAllCompleted.click();
            flag = true;
        } catch (Exception e) {
            LOGGER.error("Exception {} marking all tasks as completed", e);
        }
        return flag;
    }

    public boolean verifyAllTaskAsCompleted() {
        boolean flag = false;
        try {
            waitTillElementIsPresent(listTasks.get(0));
            for (WebElement element : listTasks) {
                if (Objects.requireNonNull(element.getDomAttribute("class")).contains("completed")) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception {} while verifying task", e);
        }
        return flag;
    }

    public int getActiveTasks() {
        return listActiveTasks.size();
    }
}
