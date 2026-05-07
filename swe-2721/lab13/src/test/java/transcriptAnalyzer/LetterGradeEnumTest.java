package transcriptAnalyzer;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * This testng test class will verify the operation of the Letter Grade
 * Enumeration.
 */
public class LetterGradeEnumTest {

    @DataProvider(name = "QualityPointsDataProvider")
    public static Object[][] QualityPointsDataProvider() {
        return new Object[][] {
                { LetterGradeEnum.A, 4 },
                { LetterGradeEnum.AB, 3.5 },
                { LetterGradeEnum.B, 3 },
                { LetterGradeEnum.BC, 2.5 },
                { LetterGradeEnum.C, 2 },
                { LetterGradeEnum.CD, 1.5 },
                { LetterGradeEnum.D, 1 },
                { LetterGradeEnum.F, 0 }
        };
    }

    /**
     * This test verifies the operation of the
     * 
     * @param myEnum        This is the enumeration value.
     * @param expectedValue This is the value of the enumeration in terms of quality
     *                      points.
     **/
    @Test(groups = { "LetterGradeEnumTests" }, dataProvider = "QualityPointsDataProvider")
    public void testGetQualityPoints(LetterGradeEnum myEnum, double expectedValue) throws Exception {
        // No setup
        // // No action

        // Just an assert here.
        assertEquals(myEnum.getQualityPoints(), expectedValue, 0.01);
    }
}
