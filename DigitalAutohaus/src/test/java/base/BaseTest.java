package base;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.HomePage;
import pages.LoginPage;
import pages.ShiftsPage;

import java.util.Properties;

public class BaseTest {

    PlaywrightFactory playwrightFactory;
    Page page;
    protected Properties properties;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected ShiftsPage shiftsPage;

    @BeforeTest
    public void setUp() {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.init_prop();
        page = playwrightFactory.initBrowser(properties);
        loginPage = new LoginPage(page);
    }

    @AfterTest
    public void tearDown() {
        if (page != null) {
            page.context().browser().close();
        }
    }
}
