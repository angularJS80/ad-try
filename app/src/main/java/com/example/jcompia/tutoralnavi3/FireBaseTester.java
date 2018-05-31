package com.example.jcompia.tutoralnavi3;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jun on 2017-11-25.
 */

public class FireBaseTester {
    private String TAG = "테그";

    private FirebaseAuth mAuth;
    private Context mContext;
    private String msg;

    public FireBaseTester(Context context){
        mContext = context;
    }

    public void firebaseAuthWithGoogle() {

        AuthCredential credential = GoogleAuthProvider.getCredential(GoogleApplication.getInstance().getGoogleSignAccount().getIdToken(), null);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Log.d("페일에일", "석세스");
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("message").push().setValue(getMsg());
                        } else {
                            Log.d("페일에일", "페일");
                        }

                    }
                });
    }

    public String getMsg(){
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}
