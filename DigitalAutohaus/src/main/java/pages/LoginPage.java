package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    private String username = "//input[@name='username']";
    private String password = "//input[@name='password']";
    private String remainSignedIn = "//input[@name='remember']";
    private String forgotPassword = "//span[contains(text(), 'Passwort vergessen')]";
    private String register = "//span[contains(text(), 'Anmelden')]";

    public LoginPage(Page page) {
        this.page = page;
    }

    public String getLoginPageURL() {
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
    }

    public LoginPage goToLoginPage() {
        return new LoginPage(page);
    }

    public HomePage fillLoginForm(String getUsername, String getPassword) {
        page.waitForSelector(username).isVisible();
        page.fill(username, getUsername);
        page.fill(password, getPassword);
        page.click(register);
        return new HomePage(page);
    }


}
