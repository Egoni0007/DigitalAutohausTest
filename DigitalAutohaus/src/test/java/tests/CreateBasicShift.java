package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CreateBasicShift extends BaseTest {

    private Properties testProps;

    @BeforeMethod
    public void loadTestData() {
        testProps = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config/shiftProperties.properties" + "");
            testProps.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createBasicShift() {
        homePage = loginPage.fillLoginForm(properties.getProperty("username"), properties.getProperty("password"));
        shiftsPage = homePage.goToShiftsPage().createShift().fillShiftForm(testProps);
        shiftsPage.checkShiftCreation();
    }

}
