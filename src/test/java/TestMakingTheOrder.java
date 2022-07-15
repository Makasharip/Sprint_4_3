import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import praktikum.MainPageScooterService;
import praktikum.OrderInformationPage;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static praktikum.MainPageScooterService.URL_YANDEX;


public class TestMakingTheOrder {

    private WebDriver driver;

    @BeforeEach
    public void setPropAndStartBrowser() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(URL_YANDEX);
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.get(URL_YANDEX);
        MainPageScooterService mainPageScooterService = new MainPageScooterService(driver);
        mainPageScooterService.closeCookieButton();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    public void clickButton(MainPageScooterService mainPageScooterService, String position){
        if (position.equals("upButton")){
            mainPageScooterService.clickToOrderButtonUp();
        }
        else if (position.equals("lowButton")){
            mainPageScooterService.clickToOrderButtonLow();
        }
    }

    private static Stream<Arguments> twoButtonClick() {
        return Stream.of(
                Arguments.of("upButton", "Макашарип", "Муртазалиев", "Москва", "Сокольники",
                        "79889974565", "07.07.2022", "сутки", "Позвонить перед приездом"),
                Arguments.of("lowButton", "Тимур", "Атаев", "Москва", "Сокольники",
                        "79889974565", "07.07.2022", "сутки", "Позвонить перед приездом"));
    }


    @ParameterizedTest
    @MethodSource("twoButtonClick")
    public void testOrder( String parameter, String firstName, String secondName, String adress,
                           String metroStation, String phoneNumber, String date, String time,
                           String comm) {
        //String parameter = "upButton";

        MainPageScooterService mainPageScooterService = new MainPageScooterService(driver);
        clickButton(mainPageScooterService, parameter);

        OrderInformationPage orderInformationPage = new OrderInformationPage(driver);
        orderInformationPage.orderPageFirstInput(firstName,secondName,adress
                ,metroStation,phoneNumber);
        orderInformationPage.clickOnBlackCheckBox();
        orderInformationPage.orderPageSecondInput(date,time,comm);
        orderInformationPage.clickYesButton();

        assertTrue(orderInformationPage.getNotificationOfOrderCreation());
    }

    @AfterEach
    public void teardown(){

        driver.quit();

    }

}