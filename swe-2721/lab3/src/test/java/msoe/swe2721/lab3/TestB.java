package msoe.swe2721.lab3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.InvalidParameterException;

public class TestB {
    private NumericStringConverterPartB b;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            b = new NumericStringConverterPartB();
        } catch (Exception ex) {
            fail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        b = null;
    }

    @Test(groups = {"all","testb"})
    public void failToReach() {
        assertThrows(InvalidParameterException.class, () -> b.convertTextToNumbers(null));
    }

    @Test(groups = {"all","testb"})
    public void reachButNoInfect() {
        b.convertTextToNumbers("aaaaaa");
        assertEquals(b.getDigitCount(), 0);
    }   

    @Test(groups = {"all","testa"})
    public void reachAndInfect() {
        b.convertTextToNumbers("trespuntounocuatro"); // 3.14
        assertEquals(b.getDigitCount(), -3); // three digits
    }   

    @Test(groups = {"all","testb"})
    public void reachInfectReveal() {
        b.convertTextToNumbers("uno"); // 67
        assertEquals(b.getDigitCount(), -1); // 2 digits
        //
        b.convertTextToNumbers("punto"); // 67
        assertEquals(b.getDigitCount(), 0); // 2 digits
    }   
}
