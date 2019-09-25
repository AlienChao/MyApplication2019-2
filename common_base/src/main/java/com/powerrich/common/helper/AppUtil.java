package com.powerrich.common.helper;

import android.app.Activity;

import com.powerrich.common.base.MyActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author AlienChao
 * date 2019/08/13 11:05
 */
public class AppUtil {
    public static List<Activity> activityList = new LinkedList<Activity>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (activityList != null) {
            boolean bResult = activityList.remove(activity);
            while (bResult) {
                bResult = activityList.remove(activity);
            }
        }
    }

    public static void exic(){
        if (activityList.size() > 0) {
            for (Activity activitys :activityList) {
                try {
                    activitys.finish();
                } catch (Exception e) {
                }
            }
        }
        System.exit(0);
    }

}
