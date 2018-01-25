package com.tanyayuferova.realestate.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tanyayuferova.realestate.Constants;
import com.tanyayuferova.realestate.R;
import com.tanyayuferova.realestate.databinding.ActivityRealEstateEditBinding;
import com.tanyayuferova.realestate.entity.RealEstate;
import com.tanyayuferova.realestate.utils.BindingAdaptersUtils;

public class RealEstateEditActivity extends AppCompatActivity {

    private ActivityRealEstateEditBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference realEstateReference;
    private StorageReference storageReference;

    public static final String EXTRA_REAL_ESTATE_ITEM = "extra.real_estate_item";
    private static final int RC_PHOTO_PICKER =  2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_estate_edit);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        realEstateReference = database.getReference().child(Constants.Database.REAL_ESTATES_REFERENCE);
        storageReference = FirebaseStorage.getInstance().getReference().child(Constants.Database.REAL_ESTATES_PHOTOS_STORAGE);

        RealEstate item;
        String toolBarTitle;
        if(getIntent().hasExtra(EXTRA_REAL_ESTATE_ITEM)) {
            // Update item
            item = getIntent().getParcelableExtra(EXTRA_REAL_ESTATE_ITEM);
            toolBarTitle = getString(R.string.activity_real_estate_edit_title);
            realEstateReference = realEstateReference.child(item.getKey());
        } else {
            // New item
            item = new RealEstate();
            toolBarTitle = getString(R.string.activity_real_estate_add_title);
        }
        binding.setItem(item);
        getSupportActionBar().setTitle(toolBarTitle);

        // Save item, when Done button is clicked
        binding.etFloor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    save();
                }
                return false;
            }
        });
    }

    public void onClickSave(View view) {
        save();
    }

    public void onClickCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickDelete(View view) {
        if(binding.getItem().getKey() != null) {
            realEstateReference.removeValue();
            //TODO delete photo from storage
        }
        finish();
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.complete_action_using)), RC_PHOTO_PICKER);
    }

    /**
     * Saves item in Firebase database if the data is valid and closes activity
     */
    public void save() {
        if(validateAll()) {
            setResult(RESULT_OK);
            commit();
            finish();
        }
    }

    /**
     * Saves data in Firebase database
     */
    protected void commit() {
        RealEstate item = binding.getItem();
        if(item.getKey() == null) {
            // New item
            realEstateReference.push().setValue(item);
        } else {
            //Update item
            realEstateReference.setValue(item);
        }
    }

    /**
     * Checks if all data is valid and shows errors if necessary
     * @return true if all data is valid; false - if any data is invalid
     */
    protected boolean validateAll() {
        RealEstate item = binding.getItem();
        boolean result = true;

        String nullDataError = getString(R.string.error_null_value);
        if(TextUtils.isEmpty(item.getAddress())) {
            binding.tiAddress.setError(nullDataError);
            result = false;
        } else {
            binding.tiAddress.setErrorEnabled(false);
        }
        if(item.getPrice() == 0) {
            binding.tiPrice.setError(nullDataError);
            result = false;
        } else {
            binding.tiPrice.setErrorEnabled(false);
        }
        if(item.getArea() == 0) {
            binding.tiArea.setError(nullDataError);
            result = false;
        } else {
            binding.tiArea.setErrorEnabled(false);
        }
        if(item.getRoomsCount() == 0) {
            binding.tiRooms.setError(nullDataError);
            result = false;
        } else {
            binding.tiRooms.setErrorEnabled(false);
        }
        if(item.getFloor() == 0) {
            binding.tiFloor.setError(nullDataError);
            result = false;
        } else {
            binding.tiFloor.setErrorEnabled(false);
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            StorageReference photoRef = storageReference.child(photoUri.getLastPathSegment());
            photoRef.putFile(photoUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            binding.getItem().setPhoto(downloadUrl.toString());
                            BindingAdaptersUtils.loadImage(binding.ivPhoto, binding.getItem().getPhoto());
                        }
                    });
            //TODO Load photo only when commit is launched
        }
    }
}
