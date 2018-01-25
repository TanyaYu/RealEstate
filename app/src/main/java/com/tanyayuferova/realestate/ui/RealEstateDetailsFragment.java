package com.tanyayuferova.realestate.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanyayuferova.realestate.databinding.FragmentRealEstateDetailsBinding;
import com.tanyayuferova.realestate.entity.RealEstate;

/**
 * Created by Tanya Yuferova on 1/24/2018.
 */

public class RealEstateDetailsFragment extends Fragment {

    private FragmentRealEstateDetailsBinding binding;
    public static final String ARGUMENT_REAL_ESTATE_ITEM = "arg.real_estate_item";

    public RealEstateDetailsFragment() {
    }

    public static RealEstateDetailsFragment newInstance(RealEstate item) {
        RealEstateDetailsFragment fragment = new RealEstateDetailsFragment();
        fragment.setArguments(new Bundle());
        fragment.getArguments().putParcelable(ARGUMENT_REAL_ESTATE_ITEM, item);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRealEstateDetailsBinding.inflate(inflater, container, false);
        binding.setItem((RealEstate) getArguments().getParcelable(ARGUMENT_REAL_ESTATE_ITEM));
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return binding.getRoot();
    }
}
