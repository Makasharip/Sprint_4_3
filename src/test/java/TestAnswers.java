import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import praktikum.MainPageScooterService;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static praktikum.MainPageScooterService.URL_YANDEX;


public class TestAnswers {
    private WebDriver driver;


    // Подстовляем все 8 вопросов и ответов (через индексы массивов)


    @BeforeEach
    public void testSetup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(URL_YANDEX);
        MainPageScooterService mainPageScooterService = new MainPageScooterService(driver);
        mainPageScooterService.closeCookieButton();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);

    }

    private static Stream<Arguments> answersNumbers() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5),
                Arguments.of(6),
                Arguments.of(7)
                );
    }

    @ParameterizedTest
    @MethodSource("answersNumbers")
    public void testAccordionButton(int number) {
        MainPageScooterService mainPageScooterService = new MainPageScooterService(driver);

        mainPageScooterService.scrollAndClickToAccordionButton(number);

        assertEquals(mainPageScooterService.ANSWERS[number], mainPageScooterService.clickToAccordionButton(number)
                , "Text not found or doesn't match");
    }

    @AfterEach
    public void teardown(){
        driver.quit();
    }
}


