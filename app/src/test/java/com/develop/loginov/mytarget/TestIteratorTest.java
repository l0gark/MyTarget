package com.develop.loginov.mytarget;

import com.develop.loginov.mytarget.model.TestIterator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

public class TestIteratorTest {
    @Test
    public void convertTest() {
        final String[][] matrix = new String[][]{{"2", "6", "10", "14", "18"},
                                                 {"0", "4", "8", "12", "16"},
                                                 {"3", "7", "11", "15", "19"},
                                                 {"1", "5", "9", "13", "17"}};

        final Iterator<String> iterator = new TestIterator(matrix);
        for (int i = 0; i < 20; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(iterator.next(), Integer.toString(i));
        }
        assertFalse(iterator.hasNext());
    }
}