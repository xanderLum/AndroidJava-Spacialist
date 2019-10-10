package com.spacialist.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.BusinessRecordsDTO;
import com.spacialist.data.dto.SpaBusinessEntry;
import com.spacialist.data.dto.SpaBusinessJSON;
import com.spacialist.service.HttpConnectionService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private int success = 0;

    private MutableLiveData<SearchFilter> searchFilter = new MutableLiveData<>();
    private MutableLiveData<SearchResult> searchResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private BusinessRecordsDTO recordsDTO;

    LiveData<SearchFilter> getSearchFilter() {
        return searchFilter;
    }

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
            String searchBusinessAPIPath = String.format(Constants.SEARCH_BUSINESS_BY_TIME, searchFilter.getTimeString());
            System.out.println("fPath = " + searchBusinessAPIPath);
            response = service.sendRequest(searchBusinessAPIPath, postDataParams);
            System.out.println("RESPONSE: " + response);
            ObjectMapper mapper = new ObjectMapper();
            try {
                recordsDTO = mapper.readValue(response, BusinessRecordsDTO.class);
                success = 1;
                System.out.println("Query Response" + response);
                System.out.println("Display JSON list: " + recordsDTO.toString());
                System.out.println("Success Query");
            } catch (IOException e) {
                success = 0;
                System.out.println("Failed Query");
//                isLoading.setValue(Boolean.FALSE);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (success == 1 && null != recordsDTO) {
                System.out.println("Success == 1 true");
                System.out.println("recordsDTO is NOT NULL");

                List<SpaBusinessEntry> results = new LinkedList<>();
                if (recordsDTO.getSpaBusinessDTOs() != null) {
                    SpaBusinessEntry spaBusiness;
                    for (SpaBusinessJSON s : recordsDTO.getSpaBusinessDTOs()) {
                        spaBusiness = new SpaBusinessEntry(s.getBusId(), s.getBus_name(),
                                s.getContactNo(), s.getLogo(), s.getAddress(), null);
                        Log.i("SpaBusiness", spaBusiness.toString());
                        results.add(spaBusiness);
                    }
                }

                searchResult.setValue(new SearchResult(results));
                isLoading.setValue(Boolean.FALSE);
            } else {
                System.out.println("BusinessList is EMPTY for the selected Date and Time");
                searchResult.setValue(new SearchResult(R.string.no_spa_available));
                isLoading.setValue(Boolean.FALSE);
            }
        }
    }//end of async task
}
