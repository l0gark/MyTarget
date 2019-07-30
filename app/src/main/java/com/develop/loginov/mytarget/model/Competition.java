package com.develop.loginov.mytarget.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Competition {
    private int counter;
    private final int[] results;
    private final Set<String> winners;

    private String answer1;
    private String answer2;

    public Competition(final int countMembers, final String answer1, final String answer2) {
        if (countMembers <= 0 || TextUtils.isEmpty(answer1) || TextUtils.isEmpty(answer2)) {
            throw new IllegalArgumentException();
        }
        this.answer1 = answer1;
        this.answer2 = answer2;

        results = new int[countMembers];
        winners = new HashSet<>(countMembers * 7);
        Arrays.fill(results, 0);

        counter = 0;
    }

    public void winFirst(final String nextAnswer, final int memberIndex) {
        if (TextUtils.isEmpty(nextAnswer) || memberIndex < 0 || memberIndex >= results.length) {
            throw new IllegalArgumentException();
        }
        answer2 = nextAnswer;

        counter++;
        if (counter % 4 == 0 || counter == 19) {
            winners.add(answer1);
            results[memberIndex]++;
        }
    }

    public void winSecond(final String nextAnswer, final int memberIndex) {
        if (TextUtils.isEmpty(nextAnswer) || memberIndex < 0 || memberIndex >= results.length) {
            throw new IllegalArgumentException();
        }
        answer1 = nextAnswer;

        counter++;
        if (counter % 4 == 0 || counter == 19) {
            winners.add(answer2);
            results[memberIndex]++;
        }
    }

    public int getWinnerIndex() {
        Log.d("TEST_TAG", "COUNTER = " + counter);
        int maxIndex = 0;
        for (int i = 1; i < results.length; i++) {
            if (results[maxIndex] < results[i]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public Set<String> getWinners() {
        return winners;
    }
}
