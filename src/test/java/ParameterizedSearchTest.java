import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class ParameterizedSearchTest {

    @BeforeEach
     void precondition() {
        Configuration.browserSize = "1920x1080";
        open("https://gamemag.ru/");

    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();

    }

    @ValueSource(strings = {"Cult of the Lamb", "Elden Rin"})
    @ParameterizedTest(name = "Проверка получения в заголовке поиска \"{0}\"")
    void searchTitleSite(String testData) {
        $("#gm-btn-search").click();
        $("[class='search__input']").setValue(testData).pressEnter();
        $("[class=news-header__description]").shouldHave(text(testData)).shouldBe(visible);

    }

    @CsvSource (value = {
            "Cult of the Lamb| Поклонитесь одержимому ягненку: Анонсирован стильный экшен Cult of the Lamb",
            "Elden Ring| Для Elden Ring вышел новый патч, устраняющий ряд проблем на ПК и PS5"
    }, delimiter = '|')

    @ParameterizedTest(name = "поиск статьи с загаловком \"{0}\"")
    void searchTitleArticle(String testData, String expectedText) {
        $("#gm-btn-search").click();
        $("[class='search__input']").setValue(testData).pressEnter();
        $("[class=gm-container]").shouldHave(text(expectedText)).shouldBe(visible);

    }

    static Stream<Arguments> mixSearchTitleArg(){
        return Stream.of(
                Arguments.of("Cult of the Lamb","Поклонитесь одержимому ягненку: Анонсирован стильный экшен Cult of the Lamb"),
                Arguments.of("Elden Ring","Для Elden Ring вышел новый патч, устраняющий ряд проблем на ПК и PS5")
        );
    }

    @MethodSource(value = "mixSearchTitleArg")
    @ParameterizedTest(name = "поиск статьи с загаловком \"{0}\"")
    void mixedTitleArticle(String testData,String expectedText ){
        $("#gm-btn-search").click();
        $("[class='search__input']").setValue(testData).pressEnter();
        $("[class=gm-container]").shouldHave(text(expectedText)).shouldBe(visible);


    }

}




