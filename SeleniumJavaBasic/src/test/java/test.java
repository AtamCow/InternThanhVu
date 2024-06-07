
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class test {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    String checkedAmountDate = LocalDate.now().plusDays(7).format(formatter);

}