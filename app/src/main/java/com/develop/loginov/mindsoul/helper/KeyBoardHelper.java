package com.develop.loginov.mindsoul.helper;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public final class KeyBoardHelper {
    private KeyBoardHelper(){
    }

    public static void hideKeyBoard(final Activity activity) {
        if (activity == null)
            return;

        hideKeyBoard(activity, activity.getWindow().getDecorView().getWindowToken());
    }

    public static void hideKeyBoard(Context context, IBinder binder) {
        if (context == null)
            return;
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }
}
