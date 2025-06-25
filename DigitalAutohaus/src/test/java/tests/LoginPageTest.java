package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageURLTest() {
        String actualUrl = loginPage.getLoginPageURL();
        Assert.assertEquals(actualUrl, "https://werkstattplanung.net/demo/api/kic/da/index.html#/");
    }
}
