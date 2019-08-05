package com.example.travelmantics;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    public static final int RC_SIGN_IN = 123;

    public static ArrayList<Trip> mTrips;
    public static AppCompatActivity caller;
    public static FirebaseUtil firebaseUtil;
    public static FirebaseDatabase firebaseDb;
    public static DatabaseReference databaseReference;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    public FirebaseUtil() {
    }

    public static void openDB(String ref, AppCompatActivity activity) {

        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseDb = FirebaseDatabase.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();

            caller = activity;
            mTrips = new ArrayList<>();

            firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signin();
                        Toast.makeText(caller.getBaseContext(), "Welcome back!", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            databaseReference = firebaseDb.getReference().child(ref);
        }
    }

    private static void signin() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        );
    }

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
