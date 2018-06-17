package com.example.jcompia.tutoralnavi3;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.jcompia.tutoralnavi3.adapter.MyHashMapAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jun on 2017-11-25.
 */

public class FireBaseModel {
    private String TAG = "테그";

    private FirebaseAuth mAuth;
    private Context mContext;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Gson GSON = new Gson();
    public ArrayList<String> getmListItems() {
        return mListItems;
    }

    public void setmListItems(ArrayList<String> mListItems) {
        this.mListItems = mListItems;
    }

    private ArrayList<String> mListItems = new ArrayList<String>();

    public FireBaseModel(Context context){
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
                             firebaseUser = mAuth.getCurrentUser();
                             firebaseDatabase = FirebaseDatabase.getInstance();
                             databaseReference = firebaseDatabase.getReference();
                            //Log.d("페일에일", "석세스");

                        } else {
                            Log.d("페일에일", "페일");
                        }

                    }
                });
    }

    public void firebaseNoneAuth() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    public void saveData(String todoTaskInput){
        databaseReference.child("message").push().setValue(todoTaskInput);
    }

    public void setListListener(MyHashMapAdapter arrayAdapter){
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildAdded 1",GSON.toJson(dataSnapshot.toString()));

                HashMap hashMap = new HashMap();
                hashMap.put("key",dataSnapshot.getKey());
                hashMap.put("value",dataSnapshot.getValue());


                arrayAdapter.add(  hashMap);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildChanged",dataSnapshot.toString());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("onChildRemoved",dataSnapshot.toString());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeItem(String s) {
        Log.d("removeItem",s);
        databaseReference.child("message").child(s).removeValue();
    }
}
