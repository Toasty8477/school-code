package edu.msoe.swe2721.lab7;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.testng.Assert.*;

/**
 * This is the initial tests for the word changer class within Lab 5.
 * @author wws
 *
 */
public class HortonTests {

    private WordChanger wc;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        wc = new WordChanger();
        addSynonyms();
    }

    @Test(groups = {"all", "constructor"})
    /**
     * This method will verify that the constructor is working properly. It will
     * check that an object is created and that the internal state of the object
     * is correct when instantiated.
     */
    public void testDefaultConstructor() {
        // Arrange
        wc = null;

        // Act
        wc = new WordChanger();

        // Assert
        assertNotNull(wc, "Constructor did not create a valid object.");
        assertEquals(wc.synonymList.values().size(), 0);
    }


    @DataProvider(name = "constructorDataProvider")
    public Object[][] constructorDataProvider() {
        return new Object[][]{
                // Parameters: Hashmap synonyms list, whether or not an exception is expected.
                {null, true},                            // Path: 30, 32, 34
                {new HashMap<String, String>(), false}}; // Path: 30, 32, 36, 37
    }


    /**
     * This method will test the constructor which takes in a synonym map as a parameter.
     * @param synList This is the list of synonyms.
     * @param exceptionExpected This will be true if an exception is expected to be thrown.
     * @throws WCException If something goes wrong inside of this test method.  Should never be thrown.
     */
    @Test(groups = {"all", "constructor"}, dataProvider = "constructorDataProvider")
    public void testParameterConstructor(Map<String, String> synList, boolean exceptionExpected) throws WCException {
        // Arrange
        wc = null;

        // Act
        if (exceptionExpected) {
            assertThrows(WCException.class, () -> new WordChanger(synList));
        } else {
            wc = new WordChanger(synList);
            assertSame(wc.synonymList, synList);
        }
    }

    @DataProvider(name = "findSynonymDP")
    public Object[][] findSynonymDP() {
        return new Object[][] {
            {"Has Whitespace", "", true}, // Whitespace
            {"five5", "", true}, // Non-alphabetical
            {"h", "", true}, // Too short
            {"jazz", null, false}, // No match
            {"deduce", "infer", false} // Match
        };
    }

    @Test(groups = {"all", "findSynonym"}, dataProvider = "findSynonymDP")
    public void testFindSynonym(String word, String expected, boolean exceptionExpected) throws WCException {
        // Arrange
        String actual;

        if (exceptionExpected) {
            assertThrows(WCException.class, () -> wc.findSynonym(word));
        } else {
            // Act
            actual = wc.findSynonym(word);
            // Assert
            assertEquals(actual, expected);
        }
    }

    @Test(groups = {"all", "findSynonym"})
    public void testFindSynonymEmptyMap() throws WCException {
        // Arrange
        wc = new WordChanger();
        String actual;
        // Act
        actual = wc.findSynonym("foobar");
        // Assert
        assertNull(actual);
    }

    @DataProvider(name = "toLowerCaseDP")
    public Object[][] toLowerCaseDP() {
        return new Object[][] {
            {"The", "the"}, // With uppercase letters
            {"the", "the"}, // without uppercase letters
            {null, null} // null
        };
    }

    @Test(groups = {"all", "toLowerCase"}, dataProvider = "toLowerCaseDP")
    public void testToLowerCase(String word, String expected) {
        // Arrange
        String actual;
        // Act
        actual = wc.toLowerCase(word);
        // Assert
        assertEquals(actual, expected);
    }

    private void addSynonyms() {
        try (Scanner scanner = new Scanner(new File("SynonymList.txt"))) {
            while (scanner.hasNext()) {
                String[] words = scanner.nextLine().split("\t");
                try {
                    wc.addSynonymPair(words[0], words[1]);
                } catch (WCException e) {
                    System.err.println("Could not add synonym pair");
                }
            }
        } catch (IOException e) {
            System.err.println("Could not open list file");
            System.err.println(e.getMessage());
        }
    }
}
