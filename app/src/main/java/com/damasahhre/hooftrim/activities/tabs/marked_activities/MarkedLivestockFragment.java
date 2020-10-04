package com.damasahhre.hooftrim.activities.tabs.marked_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.adapters.GridViewAdapterHomeFarm;
import com.damasahhre.hooftrim.database.DataBase;
import com.damasahhre.hooftrim.database.dao.MyDao;
import com.damasahhre.hooftrim.database.models.Farm;
import com.damasahhre.hooftrim.database.models.FarmWithCowCount;
import com.damasahhre.hooftrim.database.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MarkedLivestockFragment extends Fragment {


    private TextView notFound;
    private GridView markedGridView;
    private GridViewAdapterHomeFarm adapterHomeFarm;
    private String TAG = "MARKED FARM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_marked_livestock, container, false);
        notFound = view.findViewById(R.id.no_marked_livestroke_text);
        markedGridView = view.findViewById(R.id.marked_livestocks_grid);

        adapterHomeFarm = new GridViewAdapterHomeFarm(requireContext(), new ArrayList<>());
        markedGridView.setAdapter(adapterHomeFarm);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyDao dao = DataBase.getInstance(requireContext()).dao();
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<FarmWithCowCount> farms = dao.getMarkedFarmWithCowCount();
            List<Farm> farmList = dao.getMarkedFarm();
            requireActivity().runOnUiThread(() -> {
                ArrayList<FarmWithCowCount> addition = new ArrayList<>();
                main:
                for (Farm farm : farmList) {
                    for (FarmWithCowCount farmWithCowCount : farms) {
                        if (farm.id.equals(farmWithCowCount.farmId)) {
                            continue main;
                        }
                    }
                    FarmWithCowCount temp = new FarmWithCowCount();
                    temp.farmName = farm.name;
                    temp.farmId = farm.id;
                    temp.cowCount = 0;
                    addition.add(temp);
                }
                farms.addAll(addition);
                if (farms.isEmpty()) {
                    notFound.setVisibility(View.VISIBLE);
                    markedGridView.setVisibility(View.INVISIBLE);
                } else {
                    notFound.setVisibility(View.INVISIBLE);
                    markedGridView.setVisibility(View.VISIBLE);
                    adapterHomeFarm.setFarms(farms);
                    adapterHomeFarm.notifyDataSetChanged();
                }
            });
        });
    }
}