package com.tao.utilslib.os.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class BootUtil {

    private static List<BootCall> calls = new ArrayList<>();

    public static void registerBoot(BootCall call) {
        calls.add(call);
    }

    public static class BootCompletedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for (BootCall call : calls)
                call.onBoot(context);
        }
    }

    interface BootCall {
        void onBoot(Context context);
    }
}
