package com.danielstudio.notpaid.example;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  TextView dueDateTv;

  EditText daysDeadlineEt;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.dueDateTv = findViewById(R.id.dueDateTv);
    this.daysDeadlineEt = findViewById(R.id.daysDeadlineEt);
    findViewById(R.id.dueDateBtn).setOnClickListener(this);
    findViewById(R.id.killBtn).setOnClickListener(this);

    SharedPreferences sp = getSharedPreferences("not_paid", Context.MODE_PRIVATE);
    long time = sp.getLong("date", -1);
    int days = sp.getInt("days", -1);

    showDueDate(new Date(time));
    this.daysDeadlineEt.setText(String.valueOf(days));
  }

  private void showDueDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    this.dueDateTv.setText(sdf.format(date));
    this.dueDateTv.setTag(date.getTime());
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.dueDateBtn:
        new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
        break;
      case R.id.killBtn:
        SharedPreferences sp = getSharedPreferences("not_paid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("date", (long) dueDateTv.getTag());
        String days = daysDeadlineEt.getText().toString();
        if (!TextUtils.isEmpty(days)) {
          editor.putInt("days", Integer.valueOf(days));
        }
        editor.commit();
        System.exit(0);
        break;
    }
  }

  public static class DatePickerFragment extends DialogFragment
      implements DatePickerDialog.OnDateSetListener {

    MainActivity activity;

    @Override public void onAttach(Context context) {
      super.onAttach(context);
      this.activity = (MainActivity) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      SharedPreferences sp = activity.getSharedPreferences("not_paid", Context.MODE_PRIVATE);
      long time = sp.getLong("date", -1);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date(time));
      return new DatePickerDialog(activity, this, calendar.get(Calendar.YEAR),
          calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, month, dayOfMonth);
      Date date = calendar.getTime();
      this.activity.showDueDate(date);
    }
  }
}
