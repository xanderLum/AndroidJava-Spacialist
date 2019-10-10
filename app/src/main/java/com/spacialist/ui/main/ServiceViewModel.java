package com.spacialist.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.ServiceDTO;
import com.spacialist.data.dto.ServiceList;
import com.spacialist.data.dto.SpaServiceEntry;
import com.spacialist.data.dto.StaffDTO;
import com.spacialist.service.HttpConnectionService;
import com.spacialist.service.Utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServiceViewModel extends ViewModel {
    private int success = 0;

    private MutableLiveData<SearchResult> searchResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private ServiceList serviceList;
    private ServiceList serviceFilteredList;

    public LiveData<SearchResult> getSearchResult() {
        return searchResult;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void search(SearchFilter searchFilter) {
        //Async call
        new ServiceStubAsyncTask(searchFilter).execute();
    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Activity mActivity;
        String response = "";
        String serviceFilterResponse = "";
        HashMap<String, String> postDataParams;
        SearchFilter searchFilter;

        public ServiceStubAsyncTask(SearchFilter searchFilter) {
            this.searchFilter = searchFilter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.setValue(Boolean.TRUE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpConnectionService service = new HttpConnectionService();

            String searchServicesByBusAPIPath = String.format(Constants.READ_ALL_SERVICES_BY_BUS_ID,
                    searchFilter.getBusId());

            System.out.println("searchServicesByBusAPIPath = " + searchServicesByBusAPIPath);
            response = service.sendRequest(searchServicesByBusAPIPath, null);
            System.out.println("Service Response List: " + response);

            HttpConnectionService service2 = new HttpConnectionService();
            String searchServicesByBusAndDateAndTime = String.format(Constants.SEARCH_SERVICES_BY_BUS_ID_AND_TIME_AND_DATE,
                    searchFilter.getBusId(), searchFilter.getTimeString(), searchFilter.getDateString());
            serviceFilterResponse = service2.sendRequest(searchServicesByBusAndDateAndTime, null);
            System.out.println("ServiceFilter Response List: " + serviceFilterResponse);

            ObjectMapper mapper = new ObjectMapper();
            try {
                serviceList = mapper.readValue(response.trim(), ServiceList.class);
                serviceFilteredList = mapper.readValue(serviceFilterResponse.trim(), ServiceList.class);
                success = 1;
                System.out.println("Service Main list size: " + serviceList.getServices().size());
                System.out.println("Service Filtered list size: " + serviceFilteredList.getServices().size());
//                System.out.println("Display JSON list: " + serviceList.toString());
                System.out.println("Success services Query");
            } catch (IOException e) {
                success = 0;
                System.out.println("Failed services Query");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (success == 1 && null != serviceList) {
                System.out.println("Success == 1 true");
                System.out.println("recordsDTO is NOT NULL");

                List<ServiceDTO> results = new LinkedList<>();
                List<SpaServiceEntry> spaServiceEntries = new LinkedList<>();
                List<StaffDTO> serviceIdsAvailableByBookingTime = new LinkedList<>();
                if (serviceList.getServices() != null) {
                    ServiceDTO serviceDTO;
                    SpaServiceEntry spaServiceEntry;
                    for (ServiceDTO s : serviceList.getServices()) {
                        spaServiceEntry = new SpaServiceEntry(s.getServiceId(), s.getServiceName(),
                                Utility.convertTimeToDate(s.getDuration()),
                                s.getServiceDesc(), s.getServiceType(), new BigDecimal(s.getPrice()));
                        System.out.println("Service: " + spaServiceEntry.toString());
                        spaServiceEntries.add(spaServiceEntry);
                    }
                }

                if (serviceFilteredList.getServices() != null) {
                    System.out.println("Services Filtered List size: " + serviceFilteredList.getServices().size());
                    for (ServiceDTO s : serviceFilteredList.getServices()) {
                        if (s.getStaff() != null) {
                            serviceIdsAvailableByBookingTime.add(s.getStaff());
                            System.out.println("Added staff!");
                        } else {
                            System.out.println("No staff for service: " + s.getServiceId());
                        }
                    }
                }

                SearchResult serviceSearchResults = new SearchResult();
                serviceSearchResults.setServiceList(spaServiceEntries);
                serviceSearchResults.setServiceIdsAvailableByBookingTime(serviceIdsAvailableByBookingTime);
                searchResult.setValue(serviceSearchResults);
                isLoading.setValue(Boolean.FALSE);
            } else {
                isLoading.setValue(Boolean.FALSE);
                System.out.println("Services list for the spa business is EMPTY");
                searchResult.setValue(new SearchResult(R.string.no_services_available));
            }
        }
    }//end of async task

}
