package com.spacialist.ui.nav;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.SpaBusinessEntry;
import com.spacialist.service.Utility;
import com.spacialist.ui.login.ViewModelFactory;
import com.spacialist.ui.main.BookViewModel;
import com.spacialist.ui.main.CalendarFragment;
import com.spacialist.ui.main.NavigationHost;
import com.spacialist.ui.main.SearchFilter;
import com.spacialist.ui.main.SearchResult;
import com.spacialist.ui.main.SpaBusinessGridFragment;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookSpaFragment extends Fragment {

    //    private BookViewModel bookViewModel;
    private int LOCATION_REQUEST_CODE = 100;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LatLng userCurrentLocation;

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;

    private String dayOfTheWeek;
    private String day;
    private String monthString;
    private String year;

    private TextView textViewDay;
    private TextView textViewDayOfTheWeek;
    private TextView textViewMonth;
    private TextView textViewTime;

    private CardView cardView;

    private Button searchButton;

    private String selectedDateTime;
    private Date selectedDate;
    private String selectedTimeString;

    private Bundle extras;

    private BookViewModel bookViewModel;
    private List<SpaBusinessEntry> spaList;
    private ProgressDialog processDialog;

    //String time and date
    private String timeString;
    private String dateString;

    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_book_spa, container, false);
//        View view = inflater.inflate(R.layout.fragment_book_spa_update, container, false);

        String userDisplayName = null;
        extras = this.getArguments();
        if (extras != null) {
            userDisplayName = extras.getString(Constants.USER_DISPLAY_NAME);
            selectedDateTime = extras.getString(Constants.SELECTED_DATE_TIME);
            //The key argument here must match that used in the other activity
        }

        final TextView userHeading = view.findViewById(R.id.user_heading);
        userHeading.append(" " + userDisplayName + "?");
        initializeViews(view);
        getLocation();

        // Get selectedDateTime from bundle and convert to date
        if (selectedDateTime == null || selectedDateTime.isEmpty()) {
            //UPDATE HERE TO SET INITIAL TIME TO THE NEXT 30MINS TIME
            Calendar cal = Calendar.getInstance();
            selectedDate = cal.getTime();
            updateSelectedDate(selectedDate);
        } else {
            selectedDate = Utility.convertDate(selectedDateTime);
        }

        setDateFilter(selectedDate);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarFragment calendarFragment = new CalendarFragment();
                Bundle bundle = extras;
                bundle.putString(Constants.SELECTED_DATE_TIME, selectedDateTime);
                calendarFragment.setArguments(bundle);
                ((NavigationHost) getActivity()).navigateTo(calendarFragment, true);
            }
        });

        bookViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(BookViewModel.class);

        bookViewModel.getSearchResult().observe(this, new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                if (searchResult == null) {
                    return;
                }
                if (searchResult.getError() != null) {
                    showSearchFailed(searchResult.getError());
                }
                if (searchResult.getSpaList() != null) {
                    spaList = searchResult.getSpaList();
                    updateUiWithBusinesses(searchResult.getSpaList());
                }
            }
        });

        bookViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    processDialog = new ProgressDialog(getActivity());
                    processDialog.setMessage("Please  Wait ...");
                    processDialog.setCancelable(false);
                    processDialog.show();
                } else {
                    processDialog.dismiss();
                }
            }
        });

        SimpleDateFormat timeStringFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateStringFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        try {
            Date date = parseFormat.parse(selectedDateTime);
            timeString = timeStringFormat.format(date);
            dateString = dateStringFormat.format(date);
            System.out.println("(TimeString) " + parseFormat.format(date) + " = " + timeString);
            System.out.println("(DateString) " + parseFormat.format(date) + " = " + dateString);
        } catch (ParseException ex) {
            Log.i("Exception", ex.getMessage());
        }

        extras.putString(Constants.SELECTED_DATE_STRING, dateString);
        extras.putString(Constants.SELECTED_TIME_STRING, timeString);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFilter searchFilter = new SearchFilter();
                searchFilter.setTimeString(timeString);
                bookViewModel.search(searchFilter);
            }
        });

        return view;
    }

    private void setDateFilter(Date date) {
        dayOfTheWeek = (String) DateFormat.format("E", date); // Thursday
        day = (String) DateFormat.format("dd", date); // 20
        monthString = (String) DateFormat.format("MMM", date); // Jun
        year = (String) DateFormat.format("yyyy", date); //2019

        SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.setAmPmStrings(new String[]{"am", "pm"});
        sdf.setDateFormatSymbols(symbols);
        String st = sdf.format(date);
        System.out.println("TIME: " + st);

        textViewDay.setText("" + day);
        textViewDayOfTheWeek.setText("" + dayOfTheWeek);
        textViewMonth.setText("" + monthString);
        textViewTime.setText("" + st);
    }

    private void initializeViews(View view) {
        textViewDay = (TextView) view.findViewById(R.id.textView_day);
        textViewDayOfTheWeek = (TextView) view.findViewById(R.id.textView_dayOfWeek);
        textViewMonth = (TextView) view.findViewById(R.id.textView_month);
        textViewTime = (TextView) view.findViewById(R.id.textView_time);
        searchButton = (Button) view.findViewById(R.id.searchButton);
        cardView = (CardView) view.findViewById(R.id.cardView);
        textView = view.findViewById(R.id.user_current_location);
    }

    private void updateSelectedDate(Date selectedDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);
        int hr = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        String min;
        boolean isSixty = false;

        int minAdd, remainder = minute % Constants.INTERVAL;
        if (remainder > 0) {
            minAdd = Constants.INTERVAL - remainder;
            minute += minAdd;
            isSixty = minute == (Constants.INTERVAL * 2) ? true : false;
        }

        Calendar updateDate = Calendar.getInstance();
        String day = (String) DateFormat.format("dd", selectedDate); // 20
        String monthString = (String) DateFormat.format("MMM", selectedDate); // Jun
        String year = (String) DateFormat.format("yyyy", selectedDate); //2019
        String am = (String) DateFormat.format("a", selectedDate); //2019

        if (isSixty) {
            min = "00";
            hr += 1;
            if (String.valueOf(hr).equals("12")) {
                am = am.equals("am") ? "pm" : "am";
            }
            System.out.println("AM VALUE = " + am);
        } else {
            min = "" + minute;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(day + Constants.BACKSLASH + monthString + Constants.BACKSLASH + year + " " + hr + Constants.COLON + min + am);
        System.out.println("Updated Date sb: " + sb.toString());
        updateDate.setTime(Utility.convertDate(sb.toString()));
        this.selectedDate = updateDate.getTime();
        this.selectedDateTime = sb.toString();
        System.out.println("Updated Date ff: " + selectedDate.toString());
    }

    protected void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestLocationPermission();
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    wayLatitude = location.getLatitude();
                    wayLongitude = location.getLongitude();
                    userCurrentLocation = new LatLng(wayLatitude, wayLongitude);
                    textView.setText("" + userCurrentLocation.toString());
                }
            }
        });
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to access user location...")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUiWithBusinesses(List<SpaBusinessEntry> spaList) {
        SpaBusinessGridFragment spaBusinessGridFragment = new SpaBusinessGridFragment();
        Bundle bundle = extras;
        bundle.putString(Constants.SELECTED_DATE_TIME, selectedDateTime);
        bundle.putDouble(Constants.SELECTED_LOCATION_LONGITUDE, wayLongitude);
        bundle.putDouble(Constants.SELECTED_LOCATION_LATITUDE, wayLatitude);

        ArrayList<SpaBusinessEntry> arrayList = new ArrayList<>();
        arrayList.addAll(spaList);
        bundle.putParcelableArrayList(Constants.SEARCH_BUSINESS_LIST_RESULTS, arrayList);
        spaBusinessGridFragment.setArguments(bundle);

        ((NavigationHost) getActivity()).navigateTo(spaBusinessGridFragment, true); // Navigat
    }

    private void showSearchFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    //display the location marker on map
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(15.0f);
        mMap.setMaxZoomPreference(20.0f);

        LatLng userLoc = userCurrentLocation;

        mMap.addMarker(new MarkerOptions().position(userLoc).title("User current location marker"));
        mMap.isMyLocationEnabled();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(userLoc);
        LatLngBounds bounds = builder.build();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 15));
    }


}
