package com.develop.ain.mindsoul.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestIterator implements Iterator<String> {
    private static final int[] indexes = new int[]{1, 3, 0, 2};
    private static final int SIZE = 20;

    private int next;

    private final String[] list;

    public TestIterator(final String[][] matrix) {
        if (matrix.length != 4) {
            throw new IllegalArgumentException("Matrix size is 4x5 !");
        }

        for (final String[] row : matrix) {
            if (row.length != 5) {
                throw new IllegalArgumentException("Matrix size is 4x5 !");
            }
        }

        list = new String[SIZE];

        for (int i = 0, next = 0; i < 5; i++) {
            for (int j = 0; j < indexes.length; j++, next++) {
                int questionIndex = indexes[j];
                list[next] = matrix[questionIndex][i];
            }
        }
        next = 0;
    }

    @Override
    public boolean hasNext() {
        return next < SIZE;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iterator is empty !");
        }
        return list[next++];
    }

    public int getCurrentIndex() {
        return next % indexes.length;
    }

    public int getCurrentIndex(final String s) {
        for (int i = 0; i < next; i++) {
            if (list[i].equals(s)) {
                return i % indexes.length;
            }
        }
        return next % indexes.length;
    }
}
