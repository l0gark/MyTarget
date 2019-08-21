package com.develop.ain.mindsoul.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public final class FragmentHelper {

    private FragmentHelper() {
    }

    public static void changeFragment(@NonNull final Fragment fragment,
                                      @NonNull final FragmentManager fragmentManager,
                                      final int containerId) {
        fragmentManager.beginTransaction().
                setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(
                containerId,
                fragment).commit();
    }
}
