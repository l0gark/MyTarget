package com.develop.loginov.mytarget.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.develop.loginov.mytarget.database.LongConverter;
import com.develop.loginov.mytarget.database.TargetDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Entity(tableName = "targets")
@TypeConverters(LongConverter.class)
public class Target {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int probability;
    private long time;

    public Target(@NonNull final String name, final int probability, final long time) {
        if (TextUtils.isEmpty(name) || Math.abs(probability) > 100 || time < 0) {
            throw new IllegalArgumentException("Invalid data for Target");
        }
        this.name = name;
        this.probability = probability;
        this.time = time;
    }

    public static long save(@NonNull final Target target, @NonNull final TargetDAO dao) {
        return dao.insertOrUpdate(target);
    }

    public static void getData(@NonNull final List<Target> targets, @NonNull final TargetDAO dao) {
        targets.clear();
        targets.addAll(dao.getTargets());
    }

    public static void getData(@NonNull final List<Target> targets, @NonNull final TargetDAO dao,
                               @NonNull final RecyclerView.Adapter adapter) {
        getData(targets, dao);
        adapter.notifyDataSetChanged();
    }

    public static Target of(@NonNull final String name, final int probability, final long time) {
        return new Target(name, probability, time);
    }

    public static Target of(@NonNull final String name, final long time) {
        return new Target(name, 0, time);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
