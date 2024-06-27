package com.wposs.apirickmortyfragment.Utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class ContextUtils {
    public static void showDialogueGenerico(Context context) {
        if (!(context instanceof AppCompatActivity)) {
            return;
        }
        AppCompatActivity activity = (AppCompatActivity) context;
        DialogueGenerico.getInstance().showDialogue(activity.getSupportFragmentManager(), "DialogoGenerico");
    }
}
