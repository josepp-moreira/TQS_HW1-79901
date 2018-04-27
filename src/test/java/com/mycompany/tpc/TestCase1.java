package com.mycompany.tpc;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCase1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver","/usr/bin/chromium-browser");
    driver = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCase1() throws Exception {
    driver.get("http://localhost:8080/TPC-1.0-SNAPSHOT/");
    driver.findElement(By.id("inputNum")).click();
    driver.findElement(By.id("inputNum")).clear();
    driver.findElement(By.id("inputNum")).sendKeys("12");
    driver.findElement(By.name("from")).click();
    new Select(driver.findElement(By.name("from"))).selectByVisibleText("USD");
    driver.findElement(By.name("from")).click();
    driver.findElement(By.name("to")).click();
    new Select(driver.findElement(By.name("to"))).selectByVisibleText("EUR");
    driver.findElement(By.name("to")).click();
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.id("output")).click();
    assertEquals("9.90451728 EUR", driver.findElement(By.id("output")).getAttribute("value"));
    driver.findElement(By.id("inputNum")).click();
    driver.findElement(By.id("inputNum")).clear();
    driver.findElement(By.id("inputNum")).sendKeys("1");
    driver.findElement(By.name("from")).click();
    new Select(driver.findElement(By.name("from"))).selectByVisibleText("EUR");
    driver.findElement(By.name("from")).click();
    driver.findElement(By.name("to")).click();
    new Select(driver.findElement(By.name("to"))).selectByVisibleText("USD");
    driver.findElement(By.name("to")).click();
    driver.findElement(By.id("submit")).click();
    assertEquals("1.21112426 USD", driver.findElement(By.id("output")).getAttribute("value"));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
