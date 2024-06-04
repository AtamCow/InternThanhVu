package atamcow.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;


public class ExChap3 {
    public static final String idEmail = "mnsjhf";
    public static final String hostEmail = "grr.la";
    public static final String logEmail = idEmail + "@" + hostEmail;
    public static final String logPassword = "1234qwer";
    public static final String rePid = "12341234";
    public static String tabName;

    public static WebDriver driverChrome = new ChromeDriver();

    public static void main(String[] args) {
        //driverChrome.manage().window().maximize();

        driverChrome.get("http://saferailway.somee.com/");

        // Regíter 1 account
        register(logEmail, logPassword, rePid);

        // Confirm Acc vừa tạo
        confirmAccount(idEmail, hostEmail);

        // Lấy danh sách tab hiện có
        Set<String> windowHandles = driverChrome.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<String>(windowHandles);

        // Chuyển đổi sang tab mới
        driverChrome.switchTo().window(tabs.get(1));

        // Login với account mới
        login(logEmail, logPassword);

        // Book tickets
        // Chọn thông tin vé
        String departDate = "10/6/2024";
        String departFrom = "Nha Trang";
        String arriveAt = "Phan Thiết";
        String seatType = "Hard seat";
        String ticketAmount = "2";

        bookTicket(departDate, departFrom, arriveAt, seatType, ticketAmount);
    }

    public static void  changeTab (String tabname) {
        tabName = tabname;
        driverChrome.findElement(By.linkText(tabName)).click();
    }

    public static void register (String mail, String pass, String pid) {
        changeTab("Register");

        // Nhập thông tin
        WebElement registerEmail = driverChrome.findElement(By.id("email"));
        WebElement registerPassword = driverChrome.findElement(By.id("password"));
        WebElement registerConfirmPassword = driverChrome.findElement(By.id("confirmPassword"));
        WebElement registerPid = driverChrome.findElement(By.id("pid"));
        WebElement registerButton = driverChrome.findElement(By.xpath("//*[@id=\"RegisterForm\"]/fieldset/p/input"));

        registerEmail.sendKeys(mail);
        registerPassword.sendKeys(pass);
        registerConfirmPassword.sendKeys(pass);
        registerPid.sendKeys(pid);

        JavascriptExecutor js = (JavascriptExecutor) driverChrome;
        js.executeScript("arguments[0].scrollIntoView(true);", registerButton);

        registerButton.click();
    }

    public static void confirmAccount (String idmail, String hostmail) {
        // navigate to guerrillamail
        driverChrome.navigate().to("https://www.guerrillamail.com/inbox");

        // Chọn id Email
        driverChrome.findElement(By.id("inbox-id")).click();
        driverChrome.findElement(By.xpath("//*[@id=\"inbox-id\"]/input")).sendKeys(idmail);
        driverChrome.findElement(By.xpath("//*[@id=\"inbox-id\"]/button[1]")).click();

        // Chọn host Email
        //driverChrome.findElement(By.xpath("//*[@id=\"gm-host-select\"]")).click();
        driverChrome.findElement(By.xpath("//select[@id='gm-host-select']/option[@value='"+hostmail+"']")).click();

        // Đợi 5s
        try {
            // Chờ trong 5 giây
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Chọn email đầu tiên trong tbody
        WebElement firstRow = driverChrome.findElement(By.xpath("//*[@id=\"email_list\"]//tr[1]"));

        firstRow.findElement(By.cssSelector("#email_list tr:first-child .td2")).click();

        // Ấn vào link trong confirm mail
        // Đợi cho đến khi có link xuất hiện
        WebDriverWait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(5));
        WebElement linkConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"display_email\"]/div/div[2]/div/a")));

        driverChrome.findElement(By.xpath("//*[@id=\"display_email\"]/div/div[2]/div/a")).click();
    }

    public static void login (String email, String password) {
        changeTab("Login");

        // Nhập thông tin
        WebElement loginEmail = driverChrome.findElement(By.id("username"));
        WebElement loginPassword = driverChrome.findElement(By.id("password"));
        WebElement loginButton = driverChrome.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p/input"));

        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginButton.click();
    }

    public static void bookTicket(String departdate, String departfrom, String arriverat, String seattype, String ticketamount) {
        changeTab("Book ticket");

        // Đặt vé
        driverChrome.findElement(By.xpath("//select[@name='Date']/option[text()='" +departdate+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@name='DepartStation']/option[text()='" +departfrom+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@id='ArriveStation']/option[text()='" +arriverat+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@name='SeatType']/option[text()='" +seattype+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@name='TicketAmount']/option[text()='" +ticketamount+ "']")).click();

        driverChrome.findElement(By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/input")).click();
    }
}