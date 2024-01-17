package java.de.lwv.MetadatenTool.controller.validation;


import org.junit.Test;

import static junit.framework.Assert.*;

class ValidationTest {

    // Function: isDigit
    @Test
    public void testIsDigit_WhenAllDigits_ReturnsTrue() {
        // Given
        String[] allDigits = {"123", "456", "789"};

        // When
        boolean result = DigitChecker.isDigit(allDigits);

        // Then
        assertEquals(true, result);
    }

    @Test
    public void testIsDigit_WhenContainsNonDigit_ReturnsFalse() {
        // Given
        String[] containsNonDigit = {"123", "abc", "456"};

        // When
        boolean result = DigitChecker.isDigit(containsNonDigit);

        // Then
        assertFalse(result);
    }

    @Test
    public void testIsDigit_WhenEmptyInput_ReturnsTrue() {
        // Given
        String[] emptyInput = {};

        // When
        boolean result = DigitChecker.isDigit(emptyInput);

        // Then
        assertTrue(result);
    }


    // Function: isIDNull(int ID)
    @Test
    public void testIsIDNull_WhenIDIsZero_ReturnsFalse() {
        // Given
        int ID = 0;

        // When
        boolean result = IDChecker.isIDNull(ID);

        // Then
        assertFalse(result);
    }

    @Test
    public void testIsIDNull_WhenIDIsNotZero_ReturnsTrue() {
        // Given
        int ID = 10;

        // When
        boolean result = IDChecker.isIDNull(ID);

        // Then
        assertTrue(result);
    }


    // Function: isIDNull(String ID)
    @Test
    public void testIsIDNull_WhenIDIsEmpty_ReturnsTrue() {
        // Given
        String emptyID = "";

        // When
        boolean result = IDChecker2.isIDNull(emptyID);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsIDNull_WhenIDIsNotNull_ReturnsFalse() {
        // Given
        String nonEmptyID = "123";

        // When
        boolean result = IDChecker2.isIDNull(nonEmptyID);

        // Then
        assertFalse(result);
    }

    @Test
    public void testIsIDNull_WhenIDIsNull_ReturnsTrue() {
        // Given
        String nullID = null;

        // When
        boolean result = IDChecker2.isIDNull(nullID);

        // Then
        assertTrue(result);
    }



// Function: isSixNummer
@Test
public void testIsSixNummer_WhenAllSixDigit_ReturnsTrue() {
    // Given
    String[] sixDigitNumbers = {"123456", "987654", "111111"};

    // When
    boolean result = SixNumberChecker.isSixNummer(sixDigitNumbers);

    // Then
    assertTrue(result);
}

    @Test
    public void testIsSixNummer_WhenNotAllSixDigit_ReturnsFalse() {
        // Given
        String[] mixedNumbers = {"123456", "12345", "1234567"};

        // When
        boolean result = SixNumberChecker.isSixNummer(mixedNumbers);

        // Then
        assertFalse(result);
    }

    @Test
    public void testIsSixNummer_WhenEmptyInput_ReturnsTrue() {
        // Given
        String[] emptyInput = {};

        // When
        boolean result = SixNumberChecker.isSixNummer(emptyInput);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsSixNummer_WhenNullInput_ReturnsTrue() {
        // Given
        String[] nullInput = null;

        // When
        boolean result = SixNumberChecker.isSixNummer(nullInput);

        // Then
        assertTrue(result);
    }

    @Test
    public void testIsSixNummer_WhenMixedNullAndSixDigit_ReturnsFalse() {
        // Given
        String[] mixedNullAndSixDigit = {"123456", null, "111111"};

        // When
        boolean result = SixNumberChecker.isSixNummer(mixedNullAndSixDigit);

        // Then
        assertFalse(result);
    }

/*
    //deleteExistingFile
    @Test
    public void testDeleteExistingFile_FileExists_DeleteCalled() {
        // Create a mocked File object
        File mockedFile = mock(File.class);
        // Stub the exists method to return true
        when(mockedFile.exists()).thenReturn(true);

        // Call the method under test
        Validation.deleteExistingFile(mockedFile);

        // Verify that delete() method was called
        verify(mockedFile, times(1)).delete();
    }

    @Test
    public void testDeleteExistingFile_FileDoesNotExist_DeleteNotCalled() {
        // Create a mocked File object
        File mockedFile = mock(File.class);
        // Stub the exists method to return false
        when(mockedFile.exists()).thenReturn(false);

        // Call the method under test
        Validation.deleteExistingFile(mockedFile);

        // Verify that delete() method was not called
        verify(mockedFile, never()).delete();
    }*/
}

 class DigitChecker {
    public static boolean isDigit(String... text) {
        for (String s : text) {
            if (!s.matches("[0-9]+")) {
                return false;
            }
        }
        return true;
    }
}

 class IDChecker {
    public static boolean isIDNull(int ID) {
        if (ID == 0) {
            return false;
        } else {
            return true;
        }
    }
}

 class IDChecker2 {
    public static boolean isIDNull(String ID) {
        if (ID == null || ID.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}

 class SixNumberChecker {
    public static boolean isSixNummer(String... text) {
        if (text == null) {
            return true;
        }

        for (String s : text) {
            if (s == null || s.length() != 6) {
                return false;
            }
        }
        return true;
    }
}