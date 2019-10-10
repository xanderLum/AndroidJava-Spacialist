package com.spacialist.ui.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.service.Utility;
import com.spacialist.ui.nav.BookSpaFragment;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private String selectedDateTime;

    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    private Button selectButton;
    private CalendarPickerView calendarView;
    private TimePicker timePicker;
    private NumberPicker minutePicker;
    private Toolbar calendarToolbar;

    private Bundle calendarBundle;

    private Date selectedDate;
    private int selectedHour;
    private int selectedMin;
    private Date dateNow;
    private Date finalSelected;

    private Bundle extras;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_calendar, container, false);
        initializeViews(view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(calendarToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = this.getArguments();
        if (extras != null) {
            selectedDateTime = extras.getString(Constants.SELECTED_DATE_TIME);
            //The key argument here must match that used in the other activity
        }

        // Get selectedDateTime from bundle and convert to date
        if (selectedDateTime == null || selectedDateTime.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            selectedDate = cal.getTime();
        } else {
            selectedDate = Utility.convertDate(selectedDateTime);
        }

        //===INITIALIZE DATES===
        Calendar now = Calendar.getInstance();
        dateNow = now.getTime();

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendarView.init(dateNow, nextYear.getTime()).withSelectedDate(selectedDate);
        setMinutePicker();
        setSelectedDateTime(selectedDate, selectedHour, selectedMin);
        //=== END OF INIT ===

        calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                selectedDate = date;
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(selectedDate);
                setSelectedDateTime(selectedDate, selectedHour, selectedMin);
            }

            @Override
            public void onDateUnselected(Date date) {
                // no event
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                String selectedTime = String.valueOf(hourOfDay).toString() + ":" + String.valueOf(minute).toString();
                selectedHour = hourOfDay;
                selectedMin = minute * Constants.INTERVAL;
                setSelectedDateTime(selectedDate, selectedHour, selectedMin);

                finalSelected = Utility.convertDate(selectedDateTime);
                if (finalSelected.compareTo(dateNow) < 0) {
                    Toast.makeText(getActivity(), "Please select future date and time!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Selected: " + selectedDateTime, Toast.LENGTH_SHORT).show();

                finalSelected = Utility.convertDate(selectedDateTime);
                if (finalSelected.compareTo(dateNow) < 0) {
                    Toast.makeText(getActivity(), "Please select future date and time!", Toast.LENGTH_SHORT).show();
                } else {
                    extras.putString(Constants.SELECTED_DATE_TIME, selectedDateTime);
                    Fragment bookSpaFragment = new NavigationFragment();
                    bookSpaFragment.setArguments(extras);

                    ((NavigationHost) getActivity()).navigateTo(bookSpaFragment, false);
                }
            }
        });

        return view;
    }

    public void setMinutePicker() {
        int numValues = 60 / Constants.INTERVAL;
        String[] displayedValues = new String[numValues];
        for (int i = 0; i < numValues; i++) {
            displayedValues[i] = FORMATTER.format(i * Constants.INTERVAL);
        }

        View minute = timePicker.findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));

        if ((minute != null) && (minute instanceof NumberPicker)) {
            minutePicker = (NumberPicker) minute;
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(numValues - 1);
            minutePicker.setDisplayedValues(displayedValues);

            timePicker.setMinute(selectedMin);
            timePicker.setHour(selectedHour);
            if (selectedDate.compareTo(dateNow) < 0) {
                selectedHour = timePicker.getHour();
                selectedMin = timePicker.getMinute() * Constants.INTERVAL;
            } else {
                Calendar setHrMin = Calendar.getInstance();
                setHrMin.setTime(selectedDate);
                selectedHour = setHrMin.get(Calendar.HOUR);
                selectedMin = setHrMin.get(Calendar.MINUTE);
                timePicker.setMinute(selectedMin);
                timePicker.setHour(selectedHour);
            }
        }
    }

    private void initializeViews(View view) {
        selectButton = (Button) view.findViewById(R.id.selectButton);
        calendarView = (CalendarPickerView) view.findViewById(R.id.calendarPicker);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        calendarToolbar = (Toolbar) view.findViewById(R.id.calendarToolbar);
    }

    private void setSelectedDateTime(Date date, int hour, int min) {
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Calendar selectedDateTimeCal = Calendar.getInstance();
        selectedDateTimeCal.set(year, month, day, hour, min);

        SimpleDateFormat sd = new SimpleDateFormat("dd/MMM/yyyy hh:mma");
        this.selectedDateTime = sd.format(selectedDateTimeCal.getTime());
//        Toast.makeText(getActivity(), "Selected: " + selectedDateTime, Toast.LENGTH_LONG).show();
    }
}
