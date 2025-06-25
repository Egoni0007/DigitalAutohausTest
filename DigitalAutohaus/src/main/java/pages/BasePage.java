package pages;

import com.microsoft.playwright.Page;

import java.util.Map;

public class BasePage {
    Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected final void fillFields(Map<String, String> fields) {
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String selector = entry.getKey();
            String value = entry.getValue();
            page.locator(selector).fill(value);
        }
    }
}
