package com.damasahhre.hooftrim.activities.login_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.damasahhre.hooftrim.R;

public class SignUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_sign_up, container, false);

        view.findViewById(R.id.submit).setOnClickListener(v -> {

        });

        return view;
    }
}