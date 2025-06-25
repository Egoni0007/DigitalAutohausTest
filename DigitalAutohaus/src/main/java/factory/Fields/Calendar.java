package factory.Fields;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Calendar {

    public void selectDateFromCalendar(String dateStr, Page page) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate targetDate = LocalDate.parse(dateStr, formatter);

        // Extract parts
        int targetDay = targetDate.getDayOfMonth();
        int targetMonth = targetDate.getMonthValue();
        int targetYear = targetDate.getYear();

        // Locators (you may want to adjust these based on your app’s structure)
        Locator monthButton = page.locator("//div[@class='v-date-picker-header__value']//button");
        Locator nextMonthButton = page.locator("//button[@aria-label='Monat vor']");
        Locator prevMonthButton = page.locator("//button[@aria-label='Monat zurück']");

        // Loop until we reach the correct month/year
        while (true) {
            String currentMonthYear = monthButton.textContent().trim(); // e.g. "Juni 2025"
            YearMonth currentYM = parseMonthYear(currentMonthYear);

            if (currentYM.getYear() == targetYear && currentYM.getMonthValue() == targetMonth) {
                break;
            } else if (currentYM.isBefore(YearMonth.of(targetYear, targetMonth))) {
                nextMonthButton.click();
            } else {
                prevMonthButton.click();
            }

            page.waitForTimeout(300); // small wait after clicking
        }

        // Click the correct day
        Locator dayButton = page.locator("//div[@class='v-btn__content' and text()='" + targetDay + "']");
        dayButton.first().click();
    }

    private YearMonth parseMonthYear(String monthYearText) {
        // Map German month names to numbers
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
