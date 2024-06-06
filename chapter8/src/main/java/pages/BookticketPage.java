package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookticketPage {
    private WebDriver driver;
    private ConfigPage configPage;

    public BookticketPage(WebDriver driverChrome) {
        this.driver = driverChrome;
        this.configPage = new ConfigPage(driver);
    }

    private String dateSelectPath = "//select[@name='Date']//option[@value='%s']" ;
    private String ticketAmountSelectPath = "//select[@name='TicketAmount']//option[text()='%s']" ;
    private String bookTicketButtonPath = "//div[@id='content']//form//input[@value='Book ticket']";

    public void bookTicket(String departdate, String ticketamounts) {
        By dateSelect = By.xpath(String.format(dateSelectPath, departdate));
        By ticketAmountSelect = By.xpath(String.format(ticketAmountSelectPath, ticketamounts));
        By bookTicketButton = By.xpath(String.format(bookTicketButtonPath));

        driver.findElement(dateSelect).click();

        WebElement amountTicketOption = driver.findElement(ticketAmountSelect);
        configPage.scrollView(amountTicketOption);
        amountTicketOption.click();

        WebElement bookTicketBtn = driver.findElement(bookTicketButton);
        configPage.scrollView(bookTicketBtn);
        bookTicketBtn.click();

    }

}
