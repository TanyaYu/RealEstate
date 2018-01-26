package com.tanyayuferova.realestate.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        SwipeRefreshLayout.OnRefreshListener{

    private static final int RC_SIGN_IN = 1;

    private ActivityRealEstateListBinding binding;
    private RealEstateAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference realEstateReference;
    private ChildEventListener childEventListener;
    private ValueEventListener valueEventListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_estate_list);
        setSupportActionBar(binding.toolbar);

        adapter = new RealEstateAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.columns_count)));

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        realEstateReference = database.getReference().child(Constants.Database.REAL_ESTATES_REFERENCE);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    //user is signed out
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder().build(),
                            RC_SIGN_IN);
                } else {
                    // user is signed in
                    onSignedInInitialize();
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);

        binding.swipeRefresh.setOnRefreshListener(this);
    }

    private void onSignedInInitialize() {
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanUp() {
        adapter.clear();
        detachDatabaseReadListener();
    }

    @Override
    public void onClickRealEstate(View view, RealEstate realEstate) {
        List<RealEstate> data = adapter.getData();
        Intent intent = new Intent(this, RealEstateDetailsActivity.class);
        intent.putParcelableArrayListExtra(RealEstateDetailsActivity.EXTRA_REAL_ESTATE_DATA, new ArrayList<Parcelable>(data));
        intent.putExtra(RealEstateDetailsActivity.EXTRA_SELECTED_INDEX, data.indexOf(realEstate));
        startActivity(intent);
    }

    public void onClickAddBtn(View view) {
        startActivity(new Intent(this, RealEstateEditActivity.class));
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
                AuthUI.getInstance().signOut(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        detachDatabaseReadListener();
        adapter.clear();
    }

    @Override
    public void onRefresh() {
        // TODO Implement refresh data
        binding.swipeRefresh.setRefreshing(false);
    }

    protected void attachDatabaseReadListener() {
        if(childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RealEstate item = dataSnapshot.getValue(RealEstate.class);
                    item.setKey(dataSnapshot.getKey());
                    adapter.addItem(item);
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    RealEstate item = dataSnapshot.getValue(RealEstate.class);
                    item.setKey(dataSnapshot.getKey());
                    adapter.updateItem(item);
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    RealEstate item = dataSnapshot.getValue(RealEstate.class);
                    item.setKey(dataSnapshot.getKey());
                    adapter.removeItem(item);
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            realEstateReference.addChildEventListener(childEventListener);
            binding.swipeRefresh.setRefreshing(true);
        }
        if(valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //This method is called after all the onChildAdded() calls have happened
                    binding.swipeRefresh.setRefreshing(false);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            realEstateReference.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    protected void detachDatabaseReadListener() {
        if(childEventListener != null) {
            realEstateReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
        if(valueEventListener != null) {
            realEstateReference.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
    }
}
