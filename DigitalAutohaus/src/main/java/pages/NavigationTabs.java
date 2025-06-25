package pages;

import com.microsoft.playwright.Page;

public class NavigationTabs extends BasePage{
    private Page page;

    private String capacitiesTab = "//div[contains(text(), 'Kapazitätsplanung')]";
    private String shiftTab = "//div[contains(text(), 'Schichten')]";
    private String shiftTitle = "//div[contains(text(), 'api - Schichten')]";

    public NavigationTabs(Page page) {
        super(page);
        this.page = page;
    }

    public ShiftsPage goToShiftsPage() {
        page.waitForSelector(capacitiesTab).isVisible();
        page.click(capacitiesTab);
        page.waitForSelector(shiftTab).isVisible();
        page.click(shiftTab);
        page.waitForSelector(shiftTitle).isVisible();
        return new ShiftsPage(page);
    }


}
