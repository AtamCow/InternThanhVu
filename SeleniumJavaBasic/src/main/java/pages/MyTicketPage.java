package pages;

import models.Ticket;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyTicketPage {
    private WebDriver driver;

    public MyTicketPage(WebDriver driver) {
        this.driver = driver;
    }

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='My ticket']");
        driver.findElement(tabNameChange).click();
    }

    public String trTicketCancel = "//div[@id='content']//table[@class='MyTable']//tr[td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s']]";
    public String buttonFirstTicketCancel = "//div[@id='content']//table[@class='MyTable']//tr[td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s'] and td[text()='%s']][1]//input";

    private int ticketNum;
    public void cancelTicket(Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkDate = LocalDate.now().plusDays(Integer.parseInt(ticket.getDepartDate())).format(formatter);

        // Get ticket number has the same information
        List<WebElement> ticketBefore = driver.findElements(By.xpath(String.format(trTicketCancel, ticket.getDepartStation(), ticket.getDepartDate(), ticket.getSeatType(), checkDate, ticket.getTicketAmount())));
        ticketNum = ticketBefore.size();

        WebElement cancelButton = driver.findElement(By.xpath(String.format(buttonFirstTicketCancel, ticket.getDepartStation(), ticket.getDepartDate(), ticket.getSeatType(), checkDate, ticket.getTicketAmount())));
        cancelButton.click();

        // Wait for Alert item
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public boolean checkTicketCanceled(Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkDate = LocalDate.now().plusDays(Integer.parseInt(ticket.getDepartDate())).format(formatter);

        List<WebElement> ticketAfter = driver.findElements(By.xpath(String.format(trTicketCancel, ticket.getDepartStation(), ticket.getDepartDate(), ticket.getSeatType(), checkDate, ticket.getTicketAmount())));
        boolean check = ticketAfter.size() == (ticketNum - 1);

        return check;
    }
}
