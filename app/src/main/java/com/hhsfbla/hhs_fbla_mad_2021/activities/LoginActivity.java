package com.hhsfbla.hhs_fbla_mad_2021.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hhsfbla.hhs_fbla_mad_2021.R;
import com.hhsfbla.hhs_fbla_mad_2021.classes.User;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private SignInButton loginGoogleBtn;
    private FirebaseUser fuser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private LoginButton loginFacebookBtn;
    private Button reportBug;

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

        if (fuser != null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            doesUserExist();
        }

        // Bug reporting
        reportBug = findViewById(R.id.login_report_bug);
        reportBug.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/NbedNqwVFTSXSrRQ7")), null));

        // FB
        callbackManager = CallbackManager.Factory.create();

        loginFacebookBtn = findViewById(R.id.loginFacebookButton);
        loginFacebookBtn.setPermissions(Arrays.asList("email", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Facebook Sign-in Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Facebook Sign-in Failed", Toast.LENGTH_SHORT).show();
            }
        });

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

    /**
     * Signs the user in to the google account
     */
    public void signIn() {
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
        //Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        //Google
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Google Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Handles facebook login
     *
     * @param token the token used to get credentials for login
     */
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                fuser = mAuth.getCurrentUser();
                doesUserExist();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "A user with this email exists already", Toast.LENGTH_SHORT).show();
            }
        });
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
                    if (document.getId().equals(fuser.getUid())) {
                        progressDialog.dismiss();
                        sendToHomePage();
                        return;
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    return;
                }
            }
            progressDialog.dismiss();
            sendToOnboarding();
        });
    }

    private void sendToHomePage() {
        fuser = mAuth.getCurrentUser();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    private void sendToOnboarding() {
        fuser = mAuth.getCurrentUser();
        User user = new User(fuser.getDisplayName(), fuser.getEmail());
        user.setPfp(fuser.getPhotoUrl().toString());
        user.addFollower(fuser.getUid());
        user.addFollowing(fuser.getUid());
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
                tv.setTextSize(28);
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
    }
}