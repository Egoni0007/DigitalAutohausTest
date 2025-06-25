package pages;

import com.microsoft.playwright.Page;

public class HomePage extends NavigationTabs{

    private Page page;

    public HomePage(Page page) {
        super(page);
        this.page = page;
    }

    public String getHomePageUrl() {
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
    }



}
