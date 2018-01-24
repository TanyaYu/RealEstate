package com.tanyayuferova.realestate.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tanyayuferova.realestate.Constants;
import com.tanyayuferova.realestate.R;
import com.tanyayuferova.realestate.databinding.ActivityRealEstateListBinding;
import com.tanyayuferova.realestate.entity.RealEstate;

import java.util.ArrayList;
import java.util.List;

public class RealEstateListActivity extends AppCompatActivity
        implements RealEstateAdapter.OnClickRealEstateHandler,
        ChildEventListener,
        ValueEventListener ,
        SwipeRefreshLayout.OnRefreshListener{

    private ActivityRealEstateListBinding binding;
    private RealEstateAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference realEstateReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_estate_list);
        setSupportActionBar(binding.toolbar);

        adapter = new RealEstateAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        realEstateReference = database.getReference().child(Constants.Database.REAL_ESTATES_REFERENCE);
        realEstateReference.addChildEventListener(this);
        realEstateReference.addListenerForSingleValueEvent(this);

        binding.swipeRefresh.setRefreshing(true);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onClickRealEstate(View view, RealEstate realEstate) {
        List<RealEstate> data = adapter.getData();
        Intent intent = new Intent(this, RealEstateDetailsActivity.class);
        intent.putParcelableArrayListExtra(RealEstateDetailsActivity.EXTRA_REAL_ESTATE_DATA, new ArrayList<Parcelable>(data));
        intent.putExtra(RealEstateDetailsActivity.EXTRA_SELECTED_INDEX, data.indexOf(realEstate));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.real_estate_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // to do logout
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        // TODO Implement refresh data
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //This method is called after all the onChildAdded() calls have happened
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        RealEstate item = dataSnapshot.getValue(RealEstate.class);
        item.setKey(dataSnapshot.getKey());
        adapter.addItem(item);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }
}
