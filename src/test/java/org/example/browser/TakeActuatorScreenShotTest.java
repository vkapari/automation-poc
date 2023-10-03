package org.example.browser;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class TakeActuatorScreenShotTest {

    private ChromeDriver chromeDriver;
    String baseLoc = "base-location-where-screenshot-will-be-saved";

    @BeforeEach
    void setUp() {
        chromeDriver = new ChromeDriver();
    }

    @AfterEach
    public void quit() {
        chromeDriver.quit();
    }

    private static Stream<Arguments> provideNameAndUrl() {
        return Stream.of(
                //Arguments.of("NAME", "URL")
                Arguments.of("google-home", "http://www.google.com/"),
                Arguments.of("the-lotr", "https://openlibrary.org/search.json?title=the+lord+of+the+rings/"),
                Arguments.of("the-alchemist", "https://openlibrary.org/search.json?title=The%20Alchemist")
        );
    }

    @ParameterizedTest
    @MethodSource("provideNameAndUrl")
    void takeScreenShot(String name, String url) throws IOException {
        chromeDriver.get(url);
        File screenshotFile = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.FILE);
        String screenshotBase64 = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.BASE64);
        String pathName = baseLoc+name+".png";
        FileUtils.copyFile(screenshotFile, new File(pathName));
    }

}
