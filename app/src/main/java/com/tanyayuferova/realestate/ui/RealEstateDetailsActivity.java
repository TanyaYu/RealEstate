package com.tanyayuferova.realestate.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tanyayuferova.realestate.R;
import com.tanyayuferova.realestate.databinding.ActivityRealEstateDetailsBinding;
import com.tanyayuferova.realestate.entity.RealEstate;

import java.util.List;

public class RealEstateDetailsActivity extends AppCompatActivity {

    private ActivityRealEstateDetailsBinding binding;
    private List<RealEstate> data;
    public static final String EXTRA_REAL_ESTATE_DATA = "extra.real_estate_data";
    public static final String EXTRA_SELECTED_INDEX = "extra.selected_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_estate_details);

        data = getIntent().getParcelableArrayListExtra(EXTRA_REAL_ESTATE_DATA);
        int selectedIndex = getIntent().getIntExtra(EXTRA_SELECTED_INDEX, 0);

        binding.pager.setAdapter(new RealEstateDetailsPagerAdapter(getSupportFragmentManager()));
        binding.pager.setCurrentItem(selectedIndex);
    }

    public class RealEstateDetailsPagerAdapter extends FragmentPagerAdapter {

        public RealEstateDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RealEstateDetailsFragment.newInstance(data.get(position));
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}
