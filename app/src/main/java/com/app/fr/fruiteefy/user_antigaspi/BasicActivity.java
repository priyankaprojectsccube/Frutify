package com.app.fr.fruiteefy.user_antigaspi;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.fr.fruiteefy.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAccessor;

//import org.threeten.bp.format.DateTimeFormatter;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Shows off the most basic usage
 */
public class BasicActivity extends AppCompatActivity
        implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    MaterialCalendarView widget;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        widget = findViewById(R.id.calendarView);
        textView = findViewById(R.id.textView);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);

        //Setup initial text
        textView.setText("No Selection");
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        Log.d("DAtes", "" + widget.getSelectedDates());

        textView.setText(selected ? FORMATTER.format((TemporalAccessor) date.getDate()) : "No Selection");
    }


    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format((TemporalAccessor) date.getDate()));
    }
}
