package com.example.lookfordev;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CompanyAddGig extends Fragment {


    public CompanyAddGig() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_add_gig, container, false);
        final Spinner dropdown = v.findViewById(R.id.spinner2);
        String[] items = new String[]{"Web Developer", "App Developer", "Hybrid Developer", "UI/UX Developer", "Backend Developer"};
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        return v;
    }
}