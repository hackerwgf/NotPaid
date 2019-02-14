package com.danielstudio.notpaid;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotPaid implements Application.ActivityLifecycleCallbacks {

  private static volatile NotPaid mInstance;

  private Date mDueDate;

  private int mDaysDeadline;

  private NotPaid(Application application, Date dueDate, int daysDeadline) {
    this.mDueDate = dueDate;
    this.mDaysDeadline = daysDeadline;
    application.registerActivityLifecycleCallbacks(this);
  }

  public static void init(Application application, String dueDate, int daysDeadline) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    try {
      init(application, sdf.parse(dueDate), daysDeadline);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public static void init(Application application, Date dueDate, int daysDeadline) {
    if (null == mInstance) {
      synchronized (NotPaid.class) {
        if (null == mInstance) mInstance = new NotPaid(application, dueDate, daysDeadline);
      }
    }
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
  }

  @Override
  public void onActivityStarted(Activity activity) {
  }

  @Override
  public void onActivityResumed(Activity activity) {
    if (null != mDueDate && mDaysDeadline > 0) {
      Date mCurrentDate = new Date();
      int days = (int) ((mCurrentDate.getTime() - mDueDate.getTime()) / ((1000 * 60 * 60 * 24)));
      if (days > 0) {
        int daysLate = mDaysDeadline - days;
        float opacity = daysLate / (float) mDaysDeadline;
        if (opacity < 0) opacity = 0;

        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = opacity;
        window.setAttributes(layoutParams);
      }
    }
  }

  @Override
  public void onActivityPaused(Activity activity) {
  }

  @Override
  public void onActivityStopped(Activity activity) {
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
  }
}
