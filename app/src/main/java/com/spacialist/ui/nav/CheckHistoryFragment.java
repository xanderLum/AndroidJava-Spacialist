package com.spacialist.ui.nav;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spacialist.R;
import com.spacialist.data.Constants;
import com.spacialist.data.dto.HistoryParceable;
import com.spacialist.ui.login.ViewModelFactory;
import com.spacialist.ui.main.LifeCycleListener;
import com.spacialist.ui.main.SearchResult;
import com.spacialist.ui.main.SpaBusinessGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CheckHistoryFragment extends Fragment implements LifeCycleListener {
    private Bundle extras;
    private UserHistoryCardRecyclerViewAdapter adapter;
    private HistoryViewModel historyViewModel;
    private ProgressDialog processDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Check HISTORY INFLATER");
        final View view = inflater.inflate(R.layout.user_history_grid_fragment, container, false);
        extras = this.getArguments();
        // Set up the tool bar
//        setUpToolbar(view);

        historyViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(HistoryViewModel.class);

        historyViewModel.getHistory(extras.getString(Constants.USER_ID));

        historyViewModel.getSearchResult().observe(this, new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                if (searchResult == null && searchResult.getHistoryList() == null) {
                    return;
                }
                if (searchResult.getError() != null) {
                    showSearchFailed(searchResult.getError());
                }
                if (searchResult.getHistoryList() != null && searchResult.getHistoryList().size() > 0) {
                    System.out.println("HISTORY LIST fresh queried: " + searchResult.getHistoryList().toString());
                    setHistoryBundle(searchResult.getHistoryList());
                    onCreatedView(view);
                }
            }
        });

        historyViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
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

        return view;
    }

    @Override
    public void onCreatedView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.history_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<HistoryParceable> historyList = extras.getParcelableArrayList(Constants.HISTORY_PARCEL);
        if (historyList != null) {
            Log.i("History Fragment", historyList.toString());
        }

        adapter = new UserHistoryCardRecyclerViewAdapter(historyList);
        adapter.setBundle(extras);

        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small);
        recyclerView.addItemDecoration(new SpaBusinessGridItemDecoration(largePadding, smallPadding));
    }

    private void showSearchFailed(@StringRes Integer errorString) {
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void setHistoryBundle(List<HistoryParceable> historyList) {
        ArrayList<HistoryParceable> historyParcel = new ArrayList<>();
        historyParcel.addAll(historyList);
        System.out.println("HISTORY PARCEL SIZE: " + historyParcel.size());
        extras.putParcelableArrayList(Constants.HISTORY_PARCEL, historyParcel);
    }
}
