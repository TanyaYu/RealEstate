package com.tanyayuferova.realestate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tanyayuferova.realestate.Constants;
import com.tanyayuferova.realestate.databinding.FragmentRealEstateDetailsBinding;
import com.tanyayuferova.realestate.entity.RealEstate;

/**
 * Created by Tanya Yuferova on 1/24/2018.
 */

public class RealEstateDetailsFragment extends Fragment {

    private FragmentRealEstateDetailsBinding binding;
    private DatabaseReference realEstateReference;
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
                Intent intent = new Intent(getContext(), RealEstateEditActivity.class);
                intent.putExtra(RealEstateEditActivity.EXTRA_REAL_ESTATE_ITEM, binding.getItem());
                startActivity(intent);
            }
        });
        realEstateReference = FirebaseDatabase.getInstance().getReference().child(Constants.Database.REAL_ESTATES_REFERENCE);
        realEstateReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RealEstate item = dataSnapshot.getValue(RealEstate.class);
                binding.setItem(item);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(getActivity() != null) {
                    getActivity().finish();
                }
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return binding.getRoot();
    }
}
