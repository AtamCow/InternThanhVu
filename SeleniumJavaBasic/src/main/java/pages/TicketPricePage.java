package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TicketPricePage {
    private WebDriver driver;
    private PageBase pageBase;

    public TicketPricePage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Ticket price']");
        driver.findElement(tabNameChange).click();
    }

    private String ticketWithSeat = "//table[@class='NoBorder']//tr[td[2][text()='%s']]//td[a]";
    private String checkPriceTicket = "//tr[td[1][contains(.,'%s') and contains(.,'%s')]//li[contains(text(),'%s')]/following::li[contains(text(),'%s')]]//td[a]";

    private String headerCellPath = "//tr[th[contains(text(), 'Seat type')]]//td";
    private String noteSign = "//tr[td[contains(text(),'%s')]]//td[1]";
    private String seatPricePath = "//tr[th[contains(text(), 'Price (VND)')]]//td[%d]";

    public String ticketPrice;

    public void bookTicketInTicketPrice(String seatType) {
        ticketPrice = getSeatPrice(seatType);

        By bookTicketLink = By.xpath(String.format(ticketWithSeat, seatType));
        driver.findElement(bookTicketLink).click();

    }

    public void checkPriceTicket(String departStation, String arriveStation){
        By checkPrice = By.xpath(String.format(checkPriceTicket, departStation, arriveStation, departStation, arriveStation));

        WebElement checkTicket = driver.findElement(checkPrice);
        pageBase.scrollView(checkTicket);
        checkTicket.click();

    }

    public String getSeatPrice(String seatType) {
        WebElement seatSign = driver.findElement(By.xpath(String.format(noteSign, seatType)));
        String sign = seatSign.getText();
        String newText = sign.substring(0, sign.length() - 1);

        List<WebElement> headerCells = driver.findElements(By.xpath(headerCellPath));
        int hbColumnIndex = -1;
        for (int i = 0; i < headerCells.size(); i++) {
            if (headerCells.get(i).getText().trim().equals(newText)) {
                hbColumnIndex = i + 1;
                break;
            }
        }

        String price = driver.findElement(By.xpath(String.format(seatPricePath, hbColumnIndex))).getText();
        return price;
    }

}
