package com.develop.loginov.mytarget.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Entity()
public class Target {
    @PrimaryKey
    private String name;
    private int probability;
    private long time;

    private String[][] answers;

    public Target(String name, int probability, long time, String[][] answers) {
        this.name = name;
        this.probability = probability;
        this.time = time;
        this.answers = answers;
    }

    public static Target of(final String name, final int probability, final long time) {
        return new Target(name, probability, time, new String[0][0]);
    }

    public static List<Target> createTargets() {
        long time = System.currentTimeMillis();
        final Random random = new Random();
        return Arrays.asList(Target.of("Target1", 100, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target2", 80, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target3", -10, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target4", 50, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target5", 40, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target6", 50, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target7", -20, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target8", -20, time + random.nextInt(1 << 20) - (1 << 10)),
                             Target.of("Target9", 100, time + random.nextInt(1 << 20) - (1 << 10)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[][] getAnswers() {
        return answers;
    }

    public void setAnswers(String[][] answers) {
        this.answers = answers;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
