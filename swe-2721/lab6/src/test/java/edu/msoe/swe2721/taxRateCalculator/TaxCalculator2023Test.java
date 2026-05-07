package edu.msoe.swe2721.taxRateCalculator;

import org.testng.annotations.Test;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

public class TaxCalculator2023Test {
    
    TaxCalculator2023 tc;

    @BeforeGroups(groups = {"getterSetterTest"})
    public void getterSetterTestSetup() {
        try {
            tc = new TaxCalculator2023("John Doe", "Jane Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42);
        } catch (TaxFilingException e) {
            fail("Bad Constructor", e);
        }
    }

    @AfterGroups(groups = {"getterSetterTest"})
    public void getterSetterTestTeardown() {
        tc = null;
    }

    @DataProvider
    public Object[][] fullConstructorDP() {
        return new Object[][] {
            {"John Doe", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, false}, // Good Data
            {"John", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // No Last Name
            {"John Doe", "Mary", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // No Spouse Last Name
            {"J Doe", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // Name Too Short
            {"John Doe", "M Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // Spouse name too short
            {"John D", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // last name too short
            {"John Doe", "Mary D", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // spouse last name too short
            {"John Doe", "Mary Doe", FilingStatus.SINGLE, 45, 42, true}, // invalid filing status
            {"John Doe", null, FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // no spouse provided but married filing
            {"John \t", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, 42, true}, // last name is tab character 
            {"John Doe", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, -1, 42, true}, // Invalid age
            {"John Doe", "Mary Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, -1, true}, // Invalid spouse age
        };
    }

    @Test(groups = {"student"}, dataProvider = "fullConstructorDP")
    public void testFullConstructor(String name, String spouseName, FilingStatus filingStatus, int age, int spouseAge, boolean exception) {
        // All three "A"s at once
        if (exception) {
            assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, spouseName, filingStatus, age, spouseAge));
        } else {
            try {
                tc = new TaxCalculator2023(name, spouseName, filingStatus, age, spouseAge);  
            } catch (Exception e) {
                fail("Exception when not expected", e);
            } 
        }
    }

    @DataProvider
    public Object[][] singleConstructorDP() {
        return new Object[][] {
            {"John Doe", FilingStatus.SINGLE, 45, false}, // Good Data
            {"John", FilingStatus.SINGLE, 45, true}, // No Last Name
            {"J Doe", FilingStatus.SINGLE, 45, true}, // Name Too Short
            {"John D", FilingStatus.SINGLE, 45, true}, // last name too short
            {"John Doe", FilingStatus.MARRIED_FILING_JOINTLY, 45, true}, // invalid filing status
            {"John \t", FilingStatus.SINGLE, 45, true}, // last name is tab character 
            {"John Doe", FilingStatus.SINGLE, -1, true}, // Invalid age
        };
    }

    @Test(groups = {"student"}, dataProvider = "singleConstructorDP")
    public void testSingleConstructor(String name, FilingStatus filingStatus, int age, boolean exception) {
        // All three "A"s at once
        if (exception) {
            assertThrows(TaxFilingException.class, () -> new TaxCalculator2023(name, filingStatus, age));
        } else {
            try {
                tc = new TaxCalculator2023(name, filingStatus, age);  
            } catch (Exception e) {
                fail("Exception when not expected", e);
            } 
        }
    }

    @DataProvider(name = "agiDP")
    public Object[][] agiDP() {
        return new Object[][] {
            {0, false}, // Test get works before 
            {12345.67, false}, // Valid
            {0, false}, // Valid
            {-1, true}, // Invalid
            {12345.67, false} // Valid
        };
    }

    @Test(groups = {"student", "getterSetterTest"}, dataProvider = "agiDP")
    public void testAGI(double ammount, boolean exception) throws TaxFilingException {
        // Arrange
        double actual = 0;
        // Act & Assert
        if (exception) {
            assertThrows(TaxFilingException.class, () -> tc.setAdjustedGrossIncome(ammount));
        } else {
            tc.setAdjustedGrossIncome(ammount);
            actual = tc.getAdjustedGrossIncome();
            assertEquals(actual, ammount);
        }
    }

    @Test(groups = {"student", "getterSetterTest"})
    public void testAge() {
        assertEquals(tc.getAge(), 45);
        assertEquals(tc.getSpouseAge(), 42);
    }

    @DataProvider(name = "filingStatusDP")
    public FilingStatus[] filingStatusDP() {
        return new FilingStatus[] {
            FilingStatus.SINGLE,
            FilingStatus.HEAD_OF_HOUSEHOLD,
            FilingStatus.MARRIED_FILING_JOINTLY,
            FilingStatus.MARRIED_FILING_SEPARATELY
        };
    }

    @Test(groups = {"student", "getterSetterTest"}, dataProvider = "filingStatusDP", dependsOnMethods = {"testAGI", "testFullConstructor", "testSingleConstructor"})
    public void testFilingStatus(FilingStatus status) throws TaxFilingException {

        FilingStatus actual;

        // Arrange
        tc = makeNewTaxCalculator(status);
        // Act
        actual = tc.getFilingStatus();
        // Assert
        assertEquals(actual, status);
    }

    @Test(groups = {"student", "getterSetterTest"})
    public void testName() {
        assertEquals(tc.getName(), "John Doe");
        assertEquals(tc.getSpouseName(), "Mary Doe");
    }

    @DataProvider(name = "filingNeedDP")
    public Object[][] filingNeedDP() {
        return new Object[][] {
            {15701, FilingStatus.SINGLE, 69, 0, true}, // Single, >65, needs file
            {15700, FilingStatus.SINGLE, 69, 0, true}, // Single, >65, needs file
            {15699, FilingStatus.SINGLE, 69, 0, false}, // Single, >65, does not need file
            {15701, FilingStatus.SINGLE, 65, 0, true}, // Single, =65, needs file
            {15700, FilingStatus.SINGLE, 65, 0, true}, // Single, =65, needs file
            {15699, FilingStatus.SINGLE, 65, 0, false}, // Single, =65, does not need file
            {13851, FilingStatus.SINGLE, 64, 0, true}, // Single, <65, needs file
            {13850, FilingStatus.SINGLE, 64, 0, true}, // Single, <65, needs file
            {13849, FilingStatus.SINGLE, 64, 0, false}, // Single, <65, does not need file
            {22651, FilingStatus.HEAD_OF_HOUSEHOLD, 69, 0, true}, // Head, >65, needs file
            {22650, FilingStatus.HEAD_OF_HOUSEHOLD, 69, 0, true}, // Head, >65, needs file
            {22649, FilingStatus.HEAD_OF_HOUSEHOLD, 69, 0, false}, // Head, >65, does not need file
            {22651, FilingStatus.HEAD_OF_HOUSEHOLD, 65, 0, true}, // Head, =65, needs file
            {22650, FilingStatus.HEAD_OF_HOUSEHOLD, 65, 0, true}, // Head, =65, needs file
            {22499, FilingStatus.HEAD_OF_HOUSEHOLD, 65, 0, false}, // Head, =65, does not need file
            {20801, FilingStatus.HEAD_OF_HOUSEHOLD, 64, 0, true}, // Head, <65, needs file
            {20800, FilingStatus.HEAD_OF_HOUSEHOLD, 64, 0, true}, // Head, <65, needs file
            {20799, FilingStatus.HEAD_OF_HOUSEHOLD, 64, 0, false}, // Head, <65, does not need file
            {29201, FilingStatus.MARRIED_FILING_JOINTLY, 69, 64, true}, // Married Joint, one >65, needs file
            {29200, FilingStatus.MARRIED_FILING_JOINTLY, 69, 64, true}, // Married Joint, one >65, needs file
            {29199, FilingStatus.MARRIED_FILING_JOINTLY, 69, 64, false}, // Married Joint, one >65, does not need file
            {29201, FilingStatus.MARRIED_FILING_JOINTLY, 65, 64, true}, // Married Joint, one =65, needs file
            {29200, FilingStatus.MARRIED_FILING_JOINTLY, 65, 64, true}, // Married Joint, one =65, needs file
            {29199, FilingStatus.MARRIED_FILING_JOINTLY, 65, 64, false}, // Married Joint, one =65, does not need file
            {27701, FilingStatus.MARRIED_FILING_JOINTLY, 64, 64, true}, // Married Joint, both <65, needs file
            {27700, FilingStatus.MARRIED_FILING_JOINTLY, 64, 64, true}, // Married Joint, both <65, needs file
            {27699, FilingStatus.MARRIED_FILING_JOINTLY, 64, 64, false}, // Married Joint, both <65, does not need file
            {30701, FilingStatus.MARRIED_FILING_JOINTLY, 69, 69, true}, // Married Joint, both >65, needs file
            {30700, FilingStatus.MARRIED_FILING_JOINTLY, 69, 69, true}, // Married Joint, both >65, needs file
            {30699, FilingStatus.MARRIED_FILING_JOINTLY, 69, 69, false}, // Married Joint, both >65, does not need file
            {29201, FilingStatus.MARRIED_FILING_JOINTLY, 64, 69, true}, // Married Joint, spouse >65, needs file
            {29200, FilingStatus.MARRIED_FILING_JOINTLY, 64, 69, true}, // Married Joint, spouse >65, needs file
            {29199, FilingStatus.MARRIED_FILING_JOINTLY, 64, 69, false}, // Married Joint, spouse >65, does not need file
            {29201, FilingStatus.MARRIED_FILING_JOINTLY, 64, 65, true}, // Married Joint, spouse =65, needs file
            {29200, FilingStatus.MARRIED_FILING_JOINTLY, 64, 65, true}, // Married Joint, spouse =65, needs file
            {29199, FilingStatus.MARRIED_FILING_JOINTLY, 64, 65, false}, // Married Joint, spouse =65, does not need file
            {6, FilingStatus.MARRIED_FILING_SEPARATELY, 64, 64, true}, // Married Separate, needs file
            {5, FilingStatus.MARRIED_FILING_SEPARATELY, 64, 64, true}, // Married Separate, needs file
            {4, FilingStatus.MARRIED_FILING_SEPARATELY, 64, 64, false}, // Married Separate, does not need file
        };
    }

    @Test(groups = {"student"}, dependsOnMethods = {"testAGI", "testFullConstructor", "testSingleConstructor"}, dataProvider = "filingNeedDP")
    public void testDetermineFilingNeed(double agi, FilingStatus filingStatus, int age, int spouseAge, boolean expected) throws TaxFilingException {
        // Arrange
        boolean actual;
        tc = makeNewTaxCalculator(filingStatus, age, spouseAge);
        // Act
        tc.setAdjustedGrossIncome(agi);
        actual = tc.determineFilingNeed();
        // Assert
        assertEquals(actual, expected);
    }

    @DataProvider(name = "standardDeductionDP")
    public Object[][] standardDeductionDP() {
        return new Object[][] {
            {69, -1, FilingStatus.SINGLE, 15700}, // Single >65
            {65, -1, FilingStatus.SINGLE, 15700}, // Single =65
            {64, -1, FilingStatus.SINGLE, 13850}, // Single <65
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22650}, // Head >65
            {65, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22650}, // Head =65
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 20800}, // Head <65
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15350}, // Married separate >65
            {65, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15350}, // Married separate =65
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 13850}, // Married separate <65
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29200}, // Married joint you >65
            {65, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29200}, // Married joint you =65
            {64, 69, FilingStatus.MARRIED_FILING_JOINTLY, 29200}, // Married joint spouse >65
            {64, 65, FilingStatus.MARRIED_FILING_JOINTLY, 29200}, // Married joint spouse =65
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 27700}, // Married joint both <65
            {65, 65, FilingStatus.MARRIED_FILING_JOINTLY, 30700}, // Married joint both =65
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 30700}, // Married joint both >65
        };
    }

    @Test(groups = {"student"}, dataProvider = "standardDeductionDP")
    public void testObtainStandardDeduction(int age, int spouseAge, FilingStatus filingStatus, double expected) {
        // Arrange
        double actual;
        tc = makeNewTaxCalculator(filingStatus, age, spouseAge);
        // Act
        actual = tc.obtainStandardDeduction();
        // Assert
        assertEquals(actual, expected);
    }

    @DataProvider(name = "obtainTaxableDP")
    public Object[][] obtainTaxableDP() {
        return new Object[][] {
            {69, -1, FilingStatus.SINGLE, 15701, 1}, // Single >65, above limit
            {65, -1, FilingStatus.SINGLE, 15701, 1}, // Single =65, above limit
            {64, -1, FilingStatus.SINGLE, 13851, 1}, // Single <65, above limit
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22651, 1}, // Head >65, above limit
            {65, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22651, 1}, // Head =65, above limit
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 20801, 1}, // Head <65, above limit
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15351, 1}, // Married separate >65, above limit
            {65, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15351, 1}, // Married separate =65, above limit
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 13851, 1}, // Married separate <65, above limit
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29201, 1}, // Married joint you >65, above limit
            {65, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29201, 1}, // Married joint you =65, above limit
            {64, 69, FilingStatus.MARRIED_FILING_JOINTLY, 29201, 1}, // Married joint spouse >65, above limit
            {64, 65, FilingStatus.MARRIED_FILING_JOINTLY, 29201, 1}, // Married joint spouse =65, above limit
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 27701, 1}, // Married joint both <65, above limit
            {65, 65, FilingStatus.MARRIED_FILING_JOINTLY, 30701, 1}, // Married joint both =65, above limit
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 30701, 1}, // Married joint both >65, above limit
            {69, -1, FilingStatus.SINGLE, 15700, 0}, // Single >65, at limit
            {65, -1, FilingStatus.SINGLE, 15700, 0}, // Single =65, at limit
            {64, -1, FilingStatus.SINGLE, 13850, 0}, // Single <65, at limit
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22650, 0}, // Head >65, at limit
            {65, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22650, 0}, // Head =65, at limit
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 20800, 0}, // Head <65, at limit
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15350, 0}, // Married separate >65, at limit
            {65, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15350, 0}, // Married separate =65, at limit
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 13850, 0}, // Married separate <65, at limit
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29200, 0}, // Married joint you >65, at limit
            {65, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29200, 0}, // Married joint you =65, at limit
            {64, 69, FilingStatus.MARRIED_FILING_JOINTLY, 29200, 0}, // Married joint spouse >65, at limit
            {64, 65, FilingStatus.MARRIED_FILING_JOINTLY, 29200, 0}, // Married joint spouse =65, at limit
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 27700, 0}, // Married joint both <65, at limit
            {65, 65, FilingStatus.MARRIED_FILING_JOINTLY, 30700, 0}, // Married joint both =65, at limit
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 30700, 0}, // Married joint both >65, at limit
            {69, -1, FilingStatus.SINGLE, 15699, 0}, // Single >65, below limit
            {65, -1, FilingStatus.SINGLE, 15699, 0}, // Single =65, below limit
            {64, -1, FilingStatus.SINGLE, 13699, 0}, // Single <65, below limit
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22649, 0}, // Head >65, below limit
            {65, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 22649, 0}, // Head =65, below limit
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 20799, 0}, // Head <65, below limit
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15349, 0}, // Married separate >65, below limit
            {65, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 15349, 0}, // Married separate =65, below limit
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 13849, 0}, // Married separate <65, below limit
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29199, 0}, // Married joint you >65, below limit
            {65, 64, FilingStatus.MARRIED_FILING_JOINTLY, 29199, 0}, // Married joint you =65, below limit
            {64, 69, FilingStatus.MARRIED_FILING_JOINTLY, 29199, 0}, // Married joint spouse >65, below limit
            {64, 65, FilingStatus.MARRIED_FILING_JOINTLY, 29199, 0}, // Married joint spouse =65, below limit
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 27699, 0}, // Married joint both <65, below limit
            {65, 65, FilingStatus.MARRIED_FILING_JOINTLY, 30699, 0}, // Married joint both =65, below limit
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 30699, 0}, // Married joint both >65, below limit
        };
    }

    @Test(groups = {"student"}, dependsOnMethods = {"testObtainStandardDeduction", "testAGI"}, dataProvider = "obtainTaxableDP")
    public void testObtainTaxableIncome(int age, int spouseAge, FilingStatus status, double agi, double expected) throws TaxFilingException {
        // Arrange
        double actual;
        tc = makeNewTaxCalculator(status, age, spouseAge);
        tc.setAdjustedGrossIncome(agi);
        // Act
        actual = tc.obtainTaxableIncome();
        // Assert
        assertEquals(actual, expected);
    }

    @Test(groups = {"student"}, dependsOnMethods = {"testGetTaxDue"}, dataProvider = "taxDueDP")
    public void testGetNetTaxRate(int age, int spuseAge, FilingStatus status, double agi, double expectedTax) throws TaxFilingException {
        // Arrange
        double expectedRate = (expectedTax / agi) * 100;
        double actualRate;
        tc = makeNewTaxCalculator(status, age, spuseAge);
        tc.setAdjustedGrossIncome(agi);
        // Act
        actualRate = tc.getNetTaxRate();
        // Assert
        assertEquals(actualRate, expectedRate);
    }

    @DataProvider(name = "taxDueDP")
    public Object[][] taxDueDP() {
        return new Object[][] {
            // Single
            {69, -1, FilingStatus.SINGLE, 26700, 1100}, // Single >65, 10% bracket
            {64, -1, FilingStatus.SINGLE, 24850, 1100}, // Single <65, 10% bracket
            {69, -1, FilingStatus.SINGLE, 60425, 5147}, // Single >65, 12% bracket
            {64, -1, FilingStatus.SINGLE, 58575, 5147}, // Single <65, 12% bracket
            {69, -1, FilingStatus.SINGLE, 111075, 16290}, // Single >65, 22% bracket
            {64, -1, FilingStatus.SINGLE, 109225, 16290}, // Single <65, 22% bracket
            {69, -1, FilingStatus.SINGLE, 197800, 37104}, // Single >65, 24% bracket
            {64, -1, FilingStatus.SINGLE, 195950, 37104}, // Single <65, 24% bracket
            {69, -1, FilingStatus.SINGLE, 246950, 52832}, // Single >65, 32% bracket
            {64, -1, FilingStatus.SINGLE, 245100, 52832}, // Single <65, 32% bracket
            {69, -1, FilingStatus.SINGLE, 593825, 174238.25}, // Single >65, 35% bracket
            {64, -1, FilingStatus.SINGLE, 591975, 174238.25}, // Single <65, 35% bracket
            {69, -1, FilingStatus.SINGLE, 593826, 174238.62}, // Single >65, 37% bracket
            {64, -1, FilingStatus.SINGLE, 591976, 174238.62}, // Single <65, 37% bracket
            // Married separate file
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 26350, 1100}, // Married separate >65, 10% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 24850, 1100}, // Married separate <65, 10% bracket
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 60075, 5147}, // Married separate >65, 12% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 58575, 5147}, // Married separate <65, 12% bracket
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 110725, 16290}, // Married separate >65, 22% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 109225, 16290}, // Married separate <65, 22% bracket
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 197450, 37104}, // Married separate >65, 24% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 195950, 37104}, // Married separate <65, 24% bracket
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 246600, 52832}, // Married separate >65, 32% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 245100, 52832}, // Married separate <65, 32% bracket
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 593475, 174238.25}, // Married separate >65, 35% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 591975, 174238.25}, // Married separate <65, 35% bracket
            {69, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 593476, 174238.62}, // Married separate >65, 37% bracket
            {64, 64, FilingStatus.MARRIED_FILING_SEPARATELY, 591976, 174238.62}, // Married separate <65, 37% bracket
            // Head of household
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 38350, 1570}, // Head >65, 10% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 36500, 1570}, // Head <65, 10% bracket
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 82500, 6868}, // Head >65, 12% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 80650, 6868}, // Head <65, 12% bracket
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 118000, 14678}, // Head >65, 22% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 116150, 14678}, // Head <65, 22% bracket
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 204750, 35498}, // Head >65, 24% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 202900, 35498}, // Head <65, 24% bracket
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 253900, 51226}, // Head >65, 32% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 252050, 51226}, // Head <65, 32% bracket
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 600750, 172623.5}, // Head >65, 35% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 598900, 172623.5}, // Head <65, 35% bracket
            {69, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 600751, 172623.87}, // Head >65, 37% bracket
            {64, -1, FilingStatus.HEAD_OF_HOUSEHOLD, 598901, 172623.87}, // Head <65, 37% bracket
            // Married joint file
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 51200, 2200}, // Married joint one >65, 10% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 49700, 2200}, // Married joint both <65, 10% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 52700, 2200}, // Married joint both >65, 10% bracket
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 118650, 10294}, // Married joint one >65, 12% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 117150, 10294}, // Married joint both <65, 12% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 120150, 10294}, // Married joint both >65, 12% bracket
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 219950, 32580}, // Married joint one >65, 22% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 218450, 32580}, // Married joint both <65, 22% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 221450, 32580}, // Married joint both >65, 22% bracket
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 393400, 74208}, // Married joint you >65, 24% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 391900, 74208}, // Married joint both <65, 24% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 394900, 74208}, // Married joint both >65, 24% bracket
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 491700, 105664}, // Married joint you >65, 32% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 490200, 105664}, // Married joint both <65, 32% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 493200, 105664}, // Married joint both >65, 32% bracket
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 722950, 186601.5}, // Married joint you >65, 35% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 721450, 186601.5}, // Married joint both <65, 35% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 724450, 186601.5}, // Married joint both >65, 35% bracket
            {69, 64, FilingStatus.MARRIED_FILING_JOINTLY, 722951, 186601.87}, // Married joint you >65, 37% bracket
            {64, 64, FilingStatus.MARRIED_FILING_JOINTLY, 721451, 186601.87}, // Married joint both <65, 37% bracket
            {69, 69, FilingStatus.MARRIED_FILING_JOINTLY, 724451, 186601.87}, // Married joint both >65, 37% bracket
        };
    }

    @Test(groups = {"student"}, dataProvider = "taxDueDP", dependsOnMethods = {"testObtainTaxableIncome"})
    public void testGetTaxDue(int age, int spuseAge, FilingStatus status, double agi, double expected) throws TaxFilingException {
        // Arrange
        tc = makeNewTaxCalculator(status, age, spuseAge);
        double actual;
        tc.setAdjustedGrossIncome(agi);
        // Act
        actual = tc.getTaxDue();
        // Assert
        assertEquals(actual, expected);
    }

    private TaxCalculator2023 makeNewTaxCalculator(FilingStatus status) {
        return makeNewTaxCalculator(status, 45, 42);
    }

    private TaxCalculator2023 makeNewTaxCalculator(FilingStatus status, int age, int spouseAge) {
       TaxCalculator2023 tc = null;
        try {
            if (status == FilingStatus.SINGLE || status == FilingStatus.HEAD_OF_HOUSEHOLD) {
                tc = new TaxCalculator2023("John Doe", status, age);
            } else if (status == FilingStatus.MARRIED_FILING_JOINTLY || status == FilingStatus.MARRIED_FILING_SEPARATELY) {
                tc = new TaxCalculator2023("John Doe", "Mary Doe", status, age, spouseAge);
            } else {
                fail("Invalid Filing Status");
            }
        } catch (TaxFilingException e) {
            fail("Could not make new instance of tax calculator");
        }
        return tc;
    }

}