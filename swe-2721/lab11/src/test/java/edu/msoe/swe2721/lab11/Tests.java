package edu.msoe.swe2721.lab11;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import edu.msoe.swe2721.lab11.exceptions.InvalidAnalysisState;
import edu.msoe.swe2721.lab11.exceptions.InvalidStockSymbolException;
import edu.msoe.swe2721.lab11.exceptions.StockTickerConnectionError;
import edu.msoe.swe2721.lab11.exceptions.WebsiteConnectionError;

public class Tests {
    private File out;
    private PrintStream originalStream;
    private StockQuoteAnalyzer sga;

    @Mock
    private StockQuoteGeneratorInterface sqg;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws FileNotFoundException, InvalidStockSymbolException, StockTickerConnectionError {
        originalStream = System.out;
        out = new File("./out.txt");

        System.setOut(new PrintStream(out));
        sga = new StockQuoteAnalyzer("F", new MockStockQuoteGenerator(), new MockAudioErrorPlayer());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        System.setOut(originalStream);
        out.delete();
        sga = null;
    }

    @Test(groups = { "all" })
    public void testPlayErrorMusic() throws IOException, InvalidStockSymbolException, StockTickerConnectionError {
        sga = new StockQuoteAnalyzer("F", new MockStockQuoteGenerator(0, 0, 0.0), new MockAudioErrorPlayer());
        sga.playAppropriateAudio();

        String actual = Files.readString(out.toPath());
        assertEquals(actual, "playing apollo-failureisnotanoption.wav\n");
    }

    @DataProvider(name = "musicDP")
    public Object[][] musicDP() {
        return new Object[][] {
                { 100.0, 100.0, 100.0, "playing money.wav\n" },
                { 100.0, 100.0, 2.0, "playing money.wav\n" },
                { 100.0, 100.0, 0.01, "" },
                { 100.0, 100.0, -0.01, "" },
                { 100.0, 100.0, 0.0, "" },
                { 0.0, 0.0, 0.0, "" },
                { 100.0, 100.0, -2.0, "playing GRR.WAV\n" },
                { 100.0, 100.0, -100.0, "playing GRR.WAV\n" },
        };
    }

    @Test(groups = { "all" }, dataProvider = "musicDP")
    public void testPlayMusic(double previousClose, double lastTradingPrice, double change, String expected)
            throws IOException, StockTickerConnectionError, InvalidStockSymbolException {
        sga = new StockQuoteAnalyzer("F", new MockStockQuoteGenerator(previousClose, lastTradingPrice, change),
                new MockAudioErrorPlayer());
        sga.refresh();
        sga.playAppropriateAudio();

        String actual = Files.readString(out.toPath());
        assertEquals(actual, expected);
    }

    @Test
    public void testGetSymbol() {
        // Arrange
        String expectedSymbol = "F";
        String actualSymbol;
        // Act
        actualSymbol = sga.getSymbol();
        // Assert
        assertEquals(actualSymbol, expectedSymbol);
    }

    @DataProvider(name = "obtainDP")
    public Object[] obtainDP() {
        return new Object[] {
                false,
                true
        };
    }

    @Test(dataProvider = "obtainDP")
    public void testObtainChangeSincePreviousClose(boolean exception)
            throws StockTickerConnectionError, InvalidAnalysisState {
        // Arrange
        if (!exception) {
            sga.refresh();
        }
        double expected = -9.325;
        double actual;

        // Act
        if (exception) {
            assertThrows(() -> sga.obtainChangeSincePreviousClose());
        } else {
            actual = sga.obtainChangeSincePreviousClose();
            // Assert
            assertEquals(actual, expected);
        }
    }

    @Test(dataProvider = "obtainDP")
    public void testObtainCurrentPrice(boolean exception) throws StockTickerConnectionError, InvalidAnalysisState {
        // Arrange
        if (!exception) {
            sga.refresh();
        }
        double expected = 256.48;
        double actual;

        // Act
        if (exception) {
            assertThrows(() -> sga.obtainCurrentPrice());
        } else {
            actual = sga.obtainCurrentPrice();
            // Assert
            assertEquals(actual, expected);
        }
    }

    @Test(dataProvider = "obtainDP")
    public void testObtainLastUpdateTimestamp(boolean exception)
            throws StockTickerConnectionError, InvalidAnalysisState {
        // Arrange
        if (!exception) {
            sga.refresh();
        }
        String expected = "2025-10-07 13:30:00";
        String actual;

        // Act
        if (exception) {
            assertThrows(() -> sga.obtainLastUpdateTimestamp());
        } else {
            actual = sga.obtainLastUpdateTimestamp();
            // Assert
            assertEquals(actual, expected);
        }
    }

    @Test(dataProvider = "obtainDP")
    public void testObtainPreviousClose(boolean exception) throws StockTickerConnectionError, InvalidAnalysisState {
        // Arrange
        if (!exception) {
            sga.refresh();
        }
        double expected = 265.805;
        double actual;

        // Act
        if (exception) {
            assertThrows(() -> sga.obtainPreviousClose());
        } else {
            actual = sga.obtainPreviousClose();
            // Assert
            assertEquals(actual, expected);
        }
    }

    @Test(dataProvider = "obtainDP")
    public void testObtainPercentChangeSincePreviousClose(boolean exception)
            throws StockTickerConnectionError, InvalidAnalysisState {
        // Arrange
        if (!exception) {
            sga.refresh();
        }
        // (256.48 - 265.805) / 265.805 * 100
        double expected = -3.51;
        double actual;

        // Act
        if (exception) {
            assertThrows(() -> sga.obtainPercentChangeSincePreviousClose());
        } else {
            actual = sga.obtainPercentChangeSincePreviousClose();
            // Assert
            assertEquals(actual, expected);
        }
    }

    @DataProvider(name = "statusDP")
    public Object[][] statusDP() {
        return new Object[][] {
                { "RISING", 44.01, 45 },
                { "RISING", 98.5, 100 },
                { "FALLING", 45, 44.01 },
                { "FALLING", 100, 98.5 },
                { "STABLE", 100, 100.5 },
                { "UNKNOWN", 0, 0 }
        };
    }

    @Test(dataProvider = "statusDP", dependsOnMethods = { "testObtainChangeSincePreviousClose",
            "testObtainPercentChangeSincePreviousClose" })
    public void testObtainStatus(String expected, double previousClose, double currentPrice)
            throws StockTickerConnectionError, InvalidStockSymbolException {
        // Arrange
        double change = currentPrice - previousClose;
        sga = new StockQuoteAnalyzer("F", new MockStockQuoteGenerator(previousClose, currentPrice, change),
                new MockAudioErrorPlayer());
        if (!expected.equals("UNKNOWN")) {
            sga.refresh();
        }
        String actual;
        // Act
        actual = sga.obtainStatus();
        // Assert
        assertEquals(actual, expected);
    }

    @Test
    public void testRefreshExcept() throws InvalidStockSymbolException, StockTickerConnectionError {
        // Arrange
        sga = new StockQuoteAnalyzer("THR", new MockStockQuoteGenerator(), new MockAudioErrorPlayer());
        // Act & Assert
        try {
            sga.refresh();
            // Exception should be thrown so fail if there is one
            fail("Exception was not thrown");
        } catch (StockTickerConnectionError e) {
            // Make the test pass if exception is thrown
            assertEquals(1, 1);
        }
    }

    @Test
    public void testRefreshNoExcpet()
            throws InvalidStockSymbolException, StockTickerConnectionError, WebsiteConnectionError {
        // Arrange
        sqg = Mockito.mock(MockStockQuoteGenerator.class);
        sga = new StockQuoteAnalyzer("F", sqg, new MockAudioErrorPlayer());
        // Act
        sga.refresh();
        // Assert
        verify(sqg, times(1)).getCurrentQuote("F");
    }
}
