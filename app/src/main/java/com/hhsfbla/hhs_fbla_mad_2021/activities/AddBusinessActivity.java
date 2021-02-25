package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.Business;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;
import com.hhsfbla.hhs_fbla_mad_2021.util.ImageRotator;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBusinessActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseFirestore db;
    private CircleImageView logo;
    private Button doneButton, cancelButton;
    private TextInputEditText name, website, about, vision, csrLink, esg;
    private ProgressDialog progressDialog;

    private static final int RESULT_LOAD_IMAGE = 1;
    private Bitmap bitmap;
    private Uri imageUri;
    private boolean hasImageChanged;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private ImageRotator rotator;

    /**
     * Creates the page and initializes all page components, such as textviews, image views, buttons, and dialogs,
     *
     * @param savedInstanceState the save state of the activity or page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        db = FirebaseFirestore.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        // logo stuff
        storageReference = FirebaseStorage.getInstance().getReference("images").child("logos");
        hasImageChanged = false;
        rotator = new ImageRotator(this);

        // pairing objects with their views
        logo = findViewById(R.id.add_biz_logo);
        doneButton = findViewById(R.id.add_biz_done);
        cancelButton = findViewById(R.id.add_biz_cancel);
        name = findViewById(R.id.add_biz_name);
        website = findViewById(R.id.add_biz_website);
        about = findViewById(R.id.add_biz_about);
        vision = findViewById(R.id.add_biz_vision);
        csrLink = findViewById(R.id.add_biz_csr_link);
        esg = findViewById(R.id.add_biz_esg);

        // autofilling text fields & logo
        db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).get().addOnSuccessListener(documentSnapshot -> {
            Business b = documentSnapshot.toObject(Business.class);

            name.setText(b.getName());
            website.setText(b.getWebsite());
            about.setText(b.getAbout());
            vision.setText(b.getCSRVision());
            csrLink.setText(b.getCSRLink());

            if (b.getESGScore() != -1.0)
                esg.setText("" + b.getESGScore());

            if (b.getLogo() != null)
                Picasso.get().load(Uri.parse(b.getLogo())).into(logo);
        });

        logo.setOnClickListener(v -> openFileChooser());

        doneButton.setOnClickListener(v -> {
            if (uploadTask != null && uploadTask.isInProgress())
                Toast.makeText(AddBusinessActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            else
                uploadFile(fuser.getUid());
        });

        cancelButton.setOnClickListener(v -> {
            if (getIntent().getStringExtra("IS_NEW_BUSINESS") != null && getIntent().getStringExtra("IS_NEW_BUSINESS").equals("TRUE")) {
                db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).delete();
                db.collection("users").document(fuser.getUid()).update("myBusinesses", FieldValue.arrayRemove(getIntent().getStringExtra("BUSINESS_ID")));
            }

            if (getIntent().getStringExtra("FROM_ACTIVITY").equals("HomeActivity")) {
                Intent intent = new Intent(AddBusinessActivity.this, HomeActivity.class);
                intent.putExtra("fragmentToLoad", "MyProfileFragment");
                startActivity(intent);
            } else if (getIntent().getStringExtra("FROM_ACTIVITY").equals("BusinessActivity")) {
                Intent intent = new Intent(AddBusinessActivity.this, BusinessActivity.class);
                intent.putExtra("FROM_ACTIVITY", "AddBusinessActivity");
                intent.putExtra("BUSINESS_ID", getIntent().getStringExtra("BUSINESS_ID"));
                startActivity(intent);
            } else if (getIntent().getStringExtra("FROM_ACTIVITY").equals("SearchActivity")) {
                Intent intent = new Intent(AddBusinessActivity.this, SearchActivity.class);
                intent.putExtra("FROM_ACTIVITY", "AddBusinessActivity");
                startActivity(intent);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    /**
     * This method gets called after an action to get data from the user
     * Sets image to newly selected image
     *
     * @param requestCode the request code of the request
     * @param resultCode  a code representing the state of the result of the action
     * @param data        the data gained from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            bitmap = rotator.getImageBitmap(imageUri);
            logo.setImageBitmap(bitmap);
            hasImageChanged = true;
        }
    }

    /**
     * Uploads the event image to cloud storage with the file name as id
     *
     * @param id the name of the file
     */
    public void uploadFile(String id) {
        if (name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        }

        if (hasImageChanged && bitmap != null) {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            final StorageReference fileRef = storageReference.child(id);
            byte[] file = rotator.getBytesFromBitmap(bitmap);
            uploadTask = fileRef.putBytes(file)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(this::editBusiness)).addOnFailureListener(exception -> {
                    }).addOnProgressListener(taskSnapshot -> {
                    }); // leave this just in case
        } else {
            editBusiness(null);
        }
    }

    private void editBusiness(Uri uri) {
        // update business' fields
        db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("name", name.getText().toString());
        db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("website", website.getText().toString());
        db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("about", about.getText().toString());
        db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("CSRVision", vision.getText().toString());
        db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("CSRLink", csrLink.getText().toString());
        if (esg.getText().toString() == null || esg.getText().toString().equals(""))
            db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("ESGScore", -1.0);
        else
            db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("ESGScore", Double.parseDouble(esg.getText().toString()));

        if (uri != null)
            db.collection("businesses").document(getIntent().getStringExtra("BUSINESS_ID")).update("logo", uri.toString()).addOnSuccessListener(aVoid -> {
                Intent intent = new Intent(AddBusinessActivity.this, BusinessActivity.class);
                intent.putExtra("BUSINESS_ID", getIntent().getStringExtra("BUSINESS_ID"));
                intent.putExtra("FROM_ACTIVITY", "AddBusinessActivity");
                startActivity(intent);
            });
        else {
            Intent intent = new Intent(AddBusinessActivity.this, BusinessActivity.class);
            intent.putExtra("BUSINESS_ID", getIntent().getStringExtra("BUSINESS_ID"));
            intent.putExtra("FROM_ACTIVITY", "AddBusinessActivity");
            startActivity(intent);
        }
    }
}