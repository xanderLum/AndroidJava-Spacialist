package com.spacialist.ui.nav;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.History;
import com.spacialist.data.dto.HistoryParceable;
import com.spacialist.data.dto.UserHistoryDTO;
import com.spacialist.service.HttpConnectionService;
import com.spacialist.ui.main.SearchFilter;
import com.spacialist.ui.main.SearchResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HistoryViewModel extends ViewModel {
    private int success = 0;

    private MutableLiveData<SearchFilter> searchFilter = new MutableLiveData<>();
    private MutableLiveData<SearchResult> searchResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private UserHistoryDTO userHistoryDTO;

    LiveData<SearchFilter> getSearchFilter() {
        return searchFilter;
    }

    public LiveData<SearchResult> getSearchResult() {
        return searchResult;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void getHistory(String userId) {
        //Async call
        new ServiceStubAsyncTask(userId).execute();
    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {
        String response = "";
        HashMap<String, String> postDataParams;
        String userId;

        public ServiceStubAsyncTask(String userId) {
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.setValue(Boolean.TRUE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpConnectionService service = new HttpConnectionService();
            String getAllUserActivitiesAPIPath = String.format(Constants.RETRIEVE_USER_ACTIVITIES, userId);
            System.out.println("fPath = " + getAllUserActivitiesAPIPath);
            response = service.sendRequest(getAllUserActivitiesAPIPath, postDataParams);
            System.out.println("RESPONSE: " + response);
            ObjectMapper mapper = new ObjectMapper();
            try {
                userHistoryDTO = mapper.readValue(response, UserHistoryDTO.class);
                success = 1;
                System.out.println("Query Response" + response);
                System.out.println("Display JSON list: " + userHistoryDTO.toString());
                System.out.println("Success Query");
            } catch (IOException e) {
                success = 0;
                System.out.println("Failed Query");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (success == 1 && null != userHistoryDTO) {
                System.out.println("Success == 1 true");
                System.out.println("recordsDTO is NOT NULL");

                List<HistoryParceable> results = new LinkedList<>();
                if (userHistoryDTO.getHistory() != null) {
                    HistoryParceable parcel;
                    for (History s : userHistoryDTO.getHistory()) {
                        parcel = new HistoryParceable(userId, s.getTransactionId(), s.getPaymentId(), s.getAmount(),
                                s.getTransactionDate(), s.getAppointmentId(), s.getDtiNo(), s.getBusName(),
                                s.getBus_address(), s.getStartTime(), s.getEndTime(), s.getAppointmentDate(),
                                s.getServiceName(), s.getFirstname(), s.getStatus());

                        /*parcel = new SpaBusinessEntry(s.getBusId(), s.getBus_name(),
                                s.getContactNo(), s.getLogo(), s.getAddress(), null);*/
                        Log.i("History item", parcel.toString());
                        results.add(parcel);
                    }
//                    results = userHistoryDTO.getHistory();
                }

                Log.i("HISTORY list", results.toString());
                SearchResult historyResults = new SearchResult();
                historyResults.setHistoryList(results);
                searchResult.setValue(historyResults);
                isLoading.setValue(Boolean.FALSE);
            } else {
                System.out.println("HSTORY list is empty for the user");
                searchResult.setValue(new SearchResult(R.string.no_activities_yet));
                isLoading.setValue(Boolean.FALSE);
            }
        }
    }//end of async task
}
