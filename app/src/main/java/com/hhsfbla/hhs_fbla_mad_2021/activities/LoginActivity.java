package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;

public class LoginActivity extends AppCompatActivity {
    private SignInButton loginGoogleBtn;
//    private LoginButton loginFacebookBtn;
    private FirebaseUser fuser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
//    private CallbackManager mCallbackManager;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;

    private static final int GOOGLE_SIGN_IN = 123;

    /**
     * Creates the page and initializes all page components, such as textviews, image views, buttons, and dialogs,
     *
     * @param savedInstanceState the save state of the activity or page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Go Home Son stuff, delete later
        Button test = findViewById(R.id.testButton);
        test.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, HomeActivity.class)));

//        mCallbackManager = CallbackManager.Factory.create();
        if (fuser != null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            doesUserExist();
        }

        setTitle("Login");
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loginGoogleBtn = findViewById(R.id.loginGoogleBtn);
        loginGoogleBtn.setOnClickListener(v -> signIn());
        setGooglePlusButtonProperties();
//
//        loginFacebookBtn = findViewById(R.id.loginFacebookBtn);
//
//        loginFacebookBtn.setReadPermissions(Arrays.asList("email", "public_profile"));
//        loginFacebookBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                progressDialog.setMessage("Loading...");
//                progressDialog.show();
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(LoginActivity.this, "Facebook Login Failed", Toast.LENGTH_SHORT).show();
//            }
//        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth = FirebaseAuth.getInstance();
        authListener = firebaseAuth -> {
            fuser = mAuth.getCurrentUser();
            if (fuser != null) {
                doesUserExist();
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            } else {

            }
        };
        db = FirebaseFirestore.getInstance();
        fuser = mAuth.getCurrentUser();
    }

    public void signIn() {
        Log.d("", "111");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    /**
     * This method gets called after an action to get data from the user
     * Receives callback from sign in method and acts accordingly (tries to sign in with google or facebook)
     *
     * @param requestCode the request code of the request
     * @param resultCode  a code representing the state of the result of the action
     * @param data        the data gained from the activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("", "222");
        //Facebook
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        //Google
        if (requestCode == GOOGLE_SIGN_IN) {
            Log.d("", "333");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("", "444");
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("", "555");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d("", "666");
                Log.d("", e.toString());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Google Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Checks if the user has a chapter and is entered in the databse
     * If they are, sends them to home page
     * if not, sends them to choose chapter
     */
    public void doesUserExist() {
        db.collection("users")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        try {
                            if (document.getId().equals(fuser.getUid()) && !document.get("name").equals("")) {
                                progressDialog.dismiss();
                                sendToHomePage();
                                return;
                            }
                        } catch(Exception e) {
                            progressDialog.dismiss();
                            return;
                        }
                    }
                    progressDialog.dismiss();
                    sendToSignUp();
                });
    }

//    /**
//     * Handles facebook login
//     *
//     * @param token the token used to get credentials for login
//     */
//    private void handleFacebookAccessToken(AccessToken token) {
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            fuser = mAuth.getCurrentUser();
//                            doesUserExist();
//                        } else {
//                            progressDialog.dismiss();
//                            Toast.makeText(LoginActivity.this, "A user with this email exists already", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    private void sendToHomePage() {
        fuser = mAuth.getCurrentUser();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    private void sendToSignUp() {
        fuser = mAuth.getCurrentUser();
        User user = new User(fuser.getDisplayName(), fuser.getEmail());
        user.setPfp(fuser.getPhotoUrl().toString());
        db.collection("users").document(fuser.getUid()).set(user);
        Intent intent = new Intent(LoginActivity.this, OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Runs when the activity starts
     * adds listener for authentication state
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
        fuser = mAuth.getCurrentUser();
    }

    /**
     * Called when the activity stops
     * stops listening for authentication changes
     */
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }

    /**
     * Sets cosmetics of google sign in button
     */
    public void setGooglePlusButtonProperties() {
        for (int i = 0; i < loginGoogleBtn.getChildCount(); i++) {
            View v = loginGoogleBtn.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTextSize(24);
                return;
            }
        }
    }

    /**
     * Authenticates in firebase with google account
     *
     * @param acct the google account that was used to sign in
     */
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("", "666");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        fuser = mAuth.getCurrentUser();
                        doesUserExist();
                    } else {
                        Toast.makeText(LoginActivity.this, "A user with this email exists already", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
        Log.d("", "777");
    }
}