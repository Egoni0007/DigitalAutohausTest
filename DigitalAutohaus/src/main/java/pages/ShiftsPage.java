package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

public class ShiftsPage {

    Page page;
    BasePage basePage;
    protected Properties properties;
    HomePage homePage;
    ShiftsPage shiftsPage;

    private String createShiftIcon = "//i[contains(@class, 'mdi-plus')]";
    private String shiftFormTitle = "//a[contains(text(),'Details')]";
    private String shiftTitle = "//label[contains(text(),'Titel')]";
    private String shiftDescription = "//label[contains(text(),'Beschreibung')]";
    private String shiftTemplate = "//label[contains(text(),'Vorlage')]";
    private String shiftTemplateDropdownOption = "//div[contains(text(),'Werkstatt Schicht')]";
    private String shiftEmployee = "//label[contains(text(),'Mitarbeiter')]";
    private String shiftEmployeeDropdownOption = "//div[contains(text(),'Artur Gjonaj')]";
    private String shiftDate = "//label[contains(text(),'Datum')]";
    private String chooseDate = "(//div[@class='v-btn__content'][normalize-space()='28'])[4]";
    private String shiftStartTime = "//label[contains(text(),'Startzeit')]";
    private String shiftEndTime = "//label[contains(text(),'Endzeit')]";
    private String shiftSaveButton = "//label[contains(text(),'Speichern')]";

    public ShiftsPage(Page page) {
        this.page = page;
    }

    public ShiftsPage createShift() {
        page.waitForSelector(createShiftIcon).isVisible();
        page.click(createShiftIcon);
        return new ShiftsPage(page);
    }

    public ShiftsPage fillShiftForm(Properties properties) {
        page.waitForSelector(shiftTitle).isVisible();
        Map<String, String> fieldMap = Map.of(
                shiftTemplate , properties.getProperty("shiftTemplate"),
                shiftTitle, properties.getProperty("shiftTitle"),
                shiftDescription, properties.getProperty("shiftDescription"),
                shiftEmployee, properties.getProperty("shiftEmployee"),
                shiftDate, properties.getProperty("shiftDate"),
                shiftStartTime, properties.getProperty("shiftStartTime"),
                shiftEndTime, properties.getProperty("shiftEndTime")
        );
        basePage.fillFields(fieldMap);

        return new ShiftsPage(page);
    }

    public ShiftsPage fillCalendar(String date){
        page.waitForSelector(shiftTitle).isVisible();
        page.click("//button[@aria-label='$vuetify.input.prependAction']");
        page.waitForSelector("//div[@class='v-picker__title']").isVisible();
        selectDateFromCalendar(date);
        return new ShiftsPage(page);
    }

    public ShiftsPage checkShiftCreation(){
        // Check if the shift is shown for the employee
        return new ShiftsPage(page);
    }

    public void selectDateFromCalendar(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate targetDate = LocalDate.parse(dateStr, formatter);

        int targetDay = targetDate.getDayOfMonth();
        int targetMonth = targetDate.getMonthValue();
        int targetYear = targetDate.getYear();

        Locator monthHeader = page.locator("div.menuable__content__active div.v-date-picker-header__value button").first();
        Locator nextButton = page.locator("div.menuable__content__active button[aria-label='Monat vor']").first();
        Locator prevButton = page.locator("div.menuable__content__active button[aria-label='Monat zurück']").first();


        while (true) {
            YearMonth current = parseMonthYear(monthHeader.first().textContent().trim());
            if (current.getYear() == targetYear && current.getMonthValue() == targetMonth) break;
            if (current.isBefore(YearMonth.of(targetYear, targetMonth))) {
                nextButton.click();
            } else {
                prevButton.click();
            }
            page.waitForTimeout(300);
        }

        Locator dayButton = page.locator(
                "//div[contains(@class,'menuable__content__active')]//button[not(@disabled)]//div[@class='v-btn__content' and text()='" + targetDay + "']"
        );

        dayButton.first().click();
    }

    private YearMonth parseMonthYear(String monthYearText) {
        Map<String, Integer> months = Map.ofEntries(
                Map.entry("Januar", 1), Map.entry("Februar", 2), Map.entry("März", 3),
                Map.entry("April", 4), Map.entry("Mai", 5), Map.entry("Juni", 6),
                Map.entry("Juli", 7), Map.entry("August", 8), Map.entry("September", 9),
                Map.entry("Oktober", 10), Map.entry("November", 11), Map.entry("Dezember", 12)
        );

        String[] parts = monthYearText.split(" ");
        int month = months.getOrDefault(parts[0], 0);
        int year = Integer.parseInt(parts[1]);

        return YearMonth.of(year, month);
    }
}
