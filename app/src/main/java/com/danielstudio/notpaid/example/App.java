package com.danielstudio.notpaid.example;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.danielstudio.notpaid.NotPaid;
import java.util.Calendar;
import java.util.Date;

public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();

    SharedPreferences sp = getSharedPreferences("not_paid", Context.MODE_PRIVATE);
    long time = sp.getLong("date", -1);
    int days = sp.getInt("days", -1);
    if (time == -1) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, -3);
      sp.edit().putLong("date", calendar.getTimeInMillis())
          .putInt("days", 5).apply();
      NotPaid.init(this, calendar.getTime(), 5);
    } else {
      NotPaid.init(this, new Date(time), days);
    }
  }
}
