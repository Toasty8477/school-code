package edu.msoe.swe2721.lab7;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DinanTests {
    private WordChanger wc;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws WCException {
        wc = new WordChanger();
        wc.addSynonymPair("potato", "spud");
    }

    @DataProvider(name = "testDetermineIfStringisSpaceFreeDP")
    public Object[][] dp() {
        return new Object[][] {
                // assert false
                { null, false },
                { " ", false },

                // assert true
                { "a", true },
                { "", true },
                { "banana", true },
        };
    }

    @Test(groups = { "all", "determineIfStringisSpaceFree" }, dataProvider = "testDetermineIfStringisSpaceFreeDP")
    public void testDetermineIfStringisSpaceFree(String text, boolean returnValue) {
        assertEquals(wc.determineIfStringisSpaceFree(text), returnValue);
    }

    @DataProvider(name = "testAddSynonymPairDP")
    public Object[][] dp1() {
        return new Object[][] {
                // test invalid word length
                { "", "aa", false, true },
                { "a", "aa", false, true },

                // test valid word length
                { "aa", "aa", true, false },
                { "aaa", "aa", true, false },

                // test word with whitespace
                { "a a", "aa", true, true },

                // test invalid synonym length
                { "aa", "a", false, true },

                // test valid synonym length
                { "aa", "aa", true, false },
                { "aa", "aaa", true, false },

                // test synonym with whitespace
                { "aa", "a a", true, true },

                // not unique word
                { "potato", "yam", false, false },
                
                // both not unique
                { "potato", "spud", false, false },

                // both unique
                { "chair", "seat", true, false },
        };
    }

    @Test(groups = { "all", "addSynonymPair" }, dataProvider = "testAddSynonymPairDP")
    public void testAddSynonymPair(String word, String synonym, boolean returnValue, boolean exceptionExpected)
            throws WCException {
        if (exceptionExpected) {
            assertThrows(WCException.class, () -> wc.addSynonymPair(word, synonym));
        } else {
            assertEquals(wc.addSynonymPair(word, synonym), returnValue);
        }
    }
}
