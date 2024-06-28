package pages;

import models.Ticket;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.SecureRandom;
import java.time.Clock;
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

    public String selectDepartStationFilter = "//div[@class='Filter']//tbody//td[1]//option[text()='%s']";
    public String inputDepartDate = "//div[@class='Filter']//tbody//td[3]//input";
    public String applyFilterButton = "//div[@class='Filter']//input[@type='submit']";
    public String ticketFiltered = "//div[@id='content']//table[@class='MyTable']//tr[td[text()='%s'] and td[text()='%s']]";

    private int ticketNum;

    public void cancelTicket(Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkDate = LocalDate.now().plusDays(Integer.parseInt(ticket.getDepartDate())).format(formatter);

        // Get ticket number has the same information
        List<WebElement> ticketBefore = driver.findElements(By.xpath(String.format(trTicketCancel, ticket.getDepartStation(), ticket.getArriveAt(), ticket.getSeatType(), checkDate, ticket.getTicketAmount())));
        ticketNum = ticketBefore.size();

        WebElement cancelButton = driver.findElement(By.xpath(String.format(buttonFirstTicketCancel, ticket.getDepartStation(), ticket.getArriveAt(), ticket.getSeatType(), checkDate, ticket.getTicketAmount())));
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

    public String changeDate(String dateChange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String date = LocalDate.now().plusDays(Integer.parseInt(dateChange)).format(formatter);

        return date;
    }

    public void selectDepartStationInFilter(String departStation) {
        By ticketDepartStationSelect = By.xpath(String.format(selectDepartStationFilter, departStation));
        driver.findElement(ticketDepartStationSelect).click();
    }

    public void selectDepartDateInFilter(String departDate) {
        WebElement DepartDate = driver.findElement(By.xpath(inputDepartDate));

        DepartDate.sendKeys(changeDate(departDate));
    }

    public void submitFilter(){
        WebElement submitButton = driver.findElement(By.xpath(applyFilterButton));
        submitButton.click();
    }

    public boolean isManageTicketShowCorrectTicket(String departDate, String departStation) {
        String date = changeDate(departDate);
        List<WebElement> ticketFilter = driver.findElements(By.xpath(String.format(ticketFiltered, departStation, date)));

        if (ticketFilter.size() != 0) {
            return true;
        }
        else return false;
    }
}
