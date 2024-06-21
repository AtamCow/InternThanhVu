package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TimetablePage {
    private WebDriver driver;
    private PageBase pageBase;

    public TimetablePage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Timetable']");
        driver.findElement(tabNameChange).click();
    }

    private String checkTicketPrice = "//tbody//tr[td[2][text()='%s'] and td[3][text()='%s']]//a[contains(@href, 'TicketPrice')]";
    private String bookTicket = "//tbody//tr[td[2][text()='%s'] and td[3][text()='%s']]//a[contains(@href, 'BookTicket')]";

    private String tableSeattypePath = "//div[@class='DivTable']//tbody";
    private String tableSeattypeRows = "//div[@class='DivTable']//tbody//tr";
    private String tableSeattypeCell = "//div[@class='DivTable']//tbody//tr//td";

    private String[] seatTypeid = {"HS", "SS", "SSC", "HB", "SB", "SBC"};
    private String[] seatTypePriceDNtoSG = {"310000", "335000", "360000", "410000", "460000", "510000"};

    public void checkPrice(String departFrom, String arriveAt) {
        WebElement checkPriceLink = driver.findElement(By.xpath(String.format(checkTicketPrice, departFrom, arriveAt)));
        pageBase.scrollView(checkPriceLink);
        checkPriceLink.click();
    }

    public boolean checkPriceSeattypeTable() {
        List<WebElement> rows = driver.findElements(By.xpath(tableSeattypeRows));

        List<WebElement> seatTypeCells = rows.get(1).findElements(By.xpath(".//td"));
        //List<WebElement> seatTypeCells = driver.findElements(By.xpath(tableSeattypeCell));
        List<WebElement> priceCells = rows.get(2).findElements(By.xpath(".//td"));

        boolean found = true;

        // Check all cells in first row
        for (int i = 0; i < seatTypeCells.size() ; i++) {
            WebElement seatTypeCell = seatTypeCells.get(i);

            if (seatTypeCell.getText().trim().equals(seatTypeid[i])) {
                // Check cell in second row
                WebElement priceCell = priceCells.get(i);
                if (!priceCell.getText().trim().equals(seatTypePriceDNtoSG[i])) {
                    System.out.println(seatTypeCell.getText());
                    System.out.println(priceCell.getText());
                    found = false;
                }
            }
            else found = false;
        }
        return found;
    }

    public void bookTicketInTimetable(String departFrom, String arriveAt) {
        WebElement bookTicketLink = driver.findElement(By.xpath(String.format(bookTicket, departFrom, arriveAt)));
        pageBase.scrollView(bookTicketLink);
        bookTicketLink.click();
    }
}
