package transcriptAnalyzer;

import org.testng.annotations.Test;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import static org.testng.Assert.*;

/**
 * This class will provide testNG tests that can be used with the Transcript class.
 * @author schilling
 *
 */
public class TestTranscript {

    /**
     * This is a transcript example that will be used for many cases.
     */
    private Transcript validTranscript;

    private PrintStream stdOut;

    @BeforeGroups(groups = {"ioTest"})
    public void rerouteStandardOut() throws FileNotFoundException {
        // Make a new file output stream
        PrintStream o = new PrintStream(new File("out.txt"));
        // Save the console output to variable for resetting earlier
        stdOut = System.out;
        // Set output to our new file output stream
        System.setOut(o);
    }

    @AfterGroups(groups = {"ioTest"})
    public void resetStandardOut() {
        // Close the file we were routing to
        System.out.close();
        // Set the output back to the console
        System.setOut(stdOut);
    }

    @BeforeMethod(onlyForGroups = {"needsTranscript"})
    /**
     * This method will be called before each test method and will instantiate a new instance of the class.
     */
    public void beforeMethod() throws BadParameter {
        validTranscript = new Transcript("Albert Einstein");
    }

    @DataProvider (name="ConstructorInstancesDP")
    public Object[][] validConsttructorInstanceDP() {
        return new Object[][] {
                {"B", true, "The student name must be at least 3 characters in length"},
                {"Bo", true, "The student name must be at least 3 characters in length"},
                {"Bob", false, ""},
                {"Bobbie-Jean", false, ""},
                {"Bobbie-Long-Name-And-Lots-Of_hyphens", false, ""},
                {null, true, "The student name can not be null."}
        };
    }

    @Test(groups = {"TestTranscript"}, dataProvider = "ConstructorInstancesDP")
    public void testConstructor(String name, boolean exceptionExpected, String errorMessage) throws BadParameter {
        // Arrange
        validTranscript = null;

        if (exceptionExpected) {
            Throwable t = expectThrows(BadParameter.class, ()->new Transcript(name));
            assertEquals(t.getMessage(), errorMessage);
        } else {
            // Act
            try {
                validTranscript = new Transcript(name);
            } catch (BadParameter e) {
                fail();
            }

            // Assert
            assertNotNull(validTranscript);
            assertEquals(validTranscript.getStudentName(), name);
        }
    }


	@DataProvider
	/**
	 * This data provider will be used to test GPA and other operations by combining terms and adding GPA's as is applicable.
	 * @return
	 */
	public Object[][] AddTermDataProvider() throws BadParameter {
		Term t1 = new Term(AcademicQuarter.FALL, 2010);
		Term t2 = new Term(AcademicQuarter.WINTER, 2010);


		return new Object[][] { new Object[] { new Term[] { t1 }, t1, false },
				                new Object[] { new Term[] { t1 }, t2, true },
		};
	}

	@Test(groups = { "TestTranscript", "needsTranscript" }, dataProvider = "AddTermDataProvider")
	/**
	 * This method will ensure that the addTerm method is working properly.
	 */
	public void testAddTerm(Term[] Initialterms, Term termToAdd, boolean expectedValue) {
		double expectedGPA = 0;
		int credits = 0;

		// Add the initial terms.
		for (Term t : Initialterms) {
			validTranscript.addTerm(t);
		}

		// Now try to add the next term.
		boolean result = validTranscript.addTerm(termToAdd);


		assertEquals(result, expectedValue);
	}



	@DataProvider
	/**
	 * This data provider will be used to test GPA and other operations by combining terms and adding GPA's as is applicable.
	 * @return
	 */
	public Object[][] TermDataProvider() throws BadParameter {
		Term t1 = new Term(AcademicQuarter.FALL, 2010); // Will have a GPA of
														// 3.19
		Term t2 = new Term(AcademicQuarter.WINTER, 2010); // Will have a GPA of
															// 3.20
		Term t3 = new Term(AcademicQuarter.FALL, 2011); // GPA of just below 3.7
		Term t4 = new Term(AcademicQuarter.SPRING, 2012); // GPA of just above
															// 3.7

		t1.addCourse(
				new CompletedCourse("SE1011 Programming One in Java and C++ and C# and Ruby and Assembly All in One",
						39, LetterGradeEnum.AB));
		t1.addCourse(new CompletedCourse("All the rest we need to know about software in one course", 61,
				LetterGradeEnum.B));

		t2.addCourse(new CompletedCourse("SE2832 Everything that is important about testing", 41, LetterGradeEnum.AB));
		t2.addCourse(
				new CompletedCourse("SE2833 Everything that is not important about testing", 59, LetterGradeEnum.B));

		t3.addCourse(new CompletedCourse("SDL", 39, LetterGradeEnum.A));
		t3.addCourse(new CompletedCourse("More SDL", 61, LetterGradeEnum.AB));

		t4.addCourse(new CompletedCourse("Senior Design", 41, LetterGradeEnum.A));
		t4.addCourse(new CompletedCourse("One Big Happy HUSS Course", 59, LetterGradeEnum.AB));

		return new Object[][] { 
                new Object[] { new Term[] { t1 }, false, false },
				new Object[] { new Term[] { t2 }, false, true }, 
                new Object[] { new Term[] { t3 }, false, true },
				new Object[] { new Term[] { t4 }, true, false },
				new Object[] { new Term[] { t1, t2, t3, t4 }, false, true },
				new Object[] { new Term[] { t1, t4 }, false, true }, };
	}

	@Test(groups = { "TestTranscript", "needsTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that the GPA calculation works right for multiple terms.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testGPA(Term[] terms, boolean highHonors, boolean deansList) {
		double expectedGPA = 0;
		int credits = 0;

		for (Term t : terms) {
			for (CompletedCourse cc : t.getCourses()) {
				expectedGPA += cc.getQualityPoints();
				credits += cc.getCourseCredits();
			}
			validTranscript.addTerm(t);
		}

		expectedGPA = expectedGPA / credits;


	}

	@Test(groups = { "TestTranscript", "needsTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that high honors are calculated properly.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testHighHonors(Term[] terms, boolean highHonors, boolean deansList) {
		// Arrange
        for (Term t : terms) {
			validTranscript.addTerm(t);
		}
        boolean result;

        // Act
        result = validTranscript.isHighHonors();

        // Assert
        assertEquals(result, highHonors);

	}

	@Test(groups = { "TestTranscript", "needsTranscript" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that deans list is processed properly.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testDeansList(Term[] terms, boolean highHonors, boolean deansList) {
		// Arrange
        for (Term t : terms) {
			validTranscript.addTerm(t);
		}
        boolean result;

        // Act
        result = validTranscript.isDeansList();

        // Assert
        assertEquals(result, deansList);

	}

	@Test(groups = { "TestTranscript", "needsTranscript", "ioTest" }, dataProvider = "TermDataProvider")
	/**
	 * This method will ensure that a transcript can be printed.
	 * @param terms These are the terms with classes.
	 * @param highHonors This is true if the GPA should be on high honors.
	 * @param deansList This si true if the GPA should be on the deans list.
	 */
	public void testPrintTranscript(Term[] terms, boolean highHonors, boolean deansList) throws IOException {
		// Arrange

        // Make sure output file is clear
        PrintWriter writer = new PrintWriter(new File("out.txt"));
        writer.print("");
        writer.close();

        String delimiter = "===============================================================================================";
        String transcript = null;
        int numDelims = terms.length*2; // Each term should print a delimiter before and after
        int delimsRemoved = 0;
        for (Term t : terms) {
			validTranscript.addTerm(t);
		}
        // Act
		validTranscript.printTranscript();

        // More Arrange because we need output to to get the output of the program from the file so we can test it
        try {
            transcript = Files.readString(Path.of("out.txt"));
        } catch (IOException e) {
            // If we can't open the file the test will throw an exception later
            fail("Could not open output file");
        }

        // Assert

        // Student name should be there. Must be "Albert Einstein" because of the way it is
        assertTrue(transcript.contains("Albert Einstein"));

        // There should be at least one delimiter in here
        assertTrue(transcript.contains(delimiter));
        
        // Check that all terms have all courses
        for (Term term : terms) {
            for (CompletedCourse course : term.getCourses()) {
                String courseName = course.getCourseName();
                assertTrue(transcript.contains(courseName));
            }
        }

        // Check that each term has the correct academic term and year
        for (Term term : terms) {
            String termAndYear = term.getAcademicTerm().name() + " " + term.getYear();
            assertTrue(transcript.contains(termAndYear));
        }

        // Check GPA is in transcript for each term
        for (Term term : terms) {
            String standing = "GPA: " + term.calculateTermGPA();
            assertTrue(transcript.contains(standing));
        }

        // Check standing is in transcript for each term
        for (Term term : terms) {
            String standing = "Good standing: " + term.inGoodStanding();
            assertTrue(transcript.contains(standing));
        }

        // Check that student is or is not on the deans list
        if (deansList) {
            assertTrue(transcript.contains("Dean's List: true"));
        } else {
            assertTrue(transcript.contains("Dean's List: false"));
        }

        // Check that student does or does not have high honors
        if (highHonors) {
            assertTrue(transcript.contains("High Honors: true"));
        } else {
            assertTrue(transcript.contains("High Honors: false"));
        }

        // Start removing delimiters to check for the correct ammount
        for (int i = 0; i < terms.length; i++) {
            // Remove two delimiters for each term
            if (transcript.contains(delimiter)) {
                transcript = transcript.replaceFirst(delimiter, "");
                transcript = transcript.replaceFirst(delimiter, "");
                delimsRemoved += 2;
            }
        }

        assertFalse(transcript.contains(delimiter));
        assertEquals(delimsRemoved, numDelims);


	}
}
