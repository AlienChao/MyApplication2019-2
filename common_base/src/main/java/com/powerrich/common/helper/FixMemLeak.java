package com.powerrich.common.helper;

import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * @author AlienChao
 * date 2019/08/27 11:28
 */
public class FixMemLeak {
    private static Field field;
    private static boolean hasField = true;

    /**
     * 华为emui9.1 这个问题被修复掉
     * param context
     */
    public static void fixLeak(Context context) {

        if(!"HUAWEI".equals(Build.MANUFACTURER)){
            return;
        }


        if (!hasField) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mLastSrvView"};
        for (String param : arr) {
            try {
                if (field == null) {
                    field = imm.getClass().getDeclaredField(param);
                }
                if (field == null) {
                    hasField = false;
                }
                if (field != null) {
                    field.setAccessible(true);
                    field.set(imm, null);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
