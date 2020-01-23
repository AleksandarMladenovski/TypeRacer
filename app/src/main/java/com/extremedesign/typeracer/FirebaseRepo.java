package com.extremedesign.typeracer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.model.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.ref.WeakReference;

public class FirebaseRepo {
    private static FirebaseAuth authINSTANCE;
    private static DatabaseReference databaseReference;
    private static User currentUser;
    private static String defaultWebClientId = "850690284059-8k9vmorftm8595r8gu3mu7t7060gkk0t.apps.googleusercontent.com";
    private static GoogleSignInOptions gso;
    private static WeakReference<GoogleSignInClient> mGoogleSignInClient = new WeakReference<GoogleSignInClient>(null);
    private static FirebaseStorage firebaseStorage;


    public static void createUserWithEmailAndPassword(String email, String password, final String name, final JobWorker listener) {
        final FirebaseAuth auth = getAuthINSTANCE();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseStorage storage = getFirebaseStorage();
                            final boolean isUserNew = task.getResult().getAdditionalUserInfo().isNewUser();

                            storage.getReference().child("baloon.jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(task.getResult())
                                                .build();
                                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseRepo.createCurrentUser();
                                                    if (isUserNew) {
                                                        FirebaseRepo.saveUserToFirebaseDatabase();
                                                    }
                                                    listener.jobFinished(true);
                                                } else {
                                                    listener.jobFinished(false);
                                                    //TODO
                                                    //update failed
                                                    //do something
                                                }
                                            }
                                        });
                                    } else {
                                        listener.jobFinished(false);
                                        //cannot acces photo in storage for some reason
                                        //do something
                                    }
                                }
                            });


                        } else {
                            listener.jobFinished(false);
//                            Toast.makeText(getContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public static void saveUserToFirebaseDatabase() {
        final User user = getCurrentUser();
        if (user != null) {
            final DatabaseReference db = getDatabaseInstance();
            db.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        db.child("users").child(user.getUid()).setValue(user.getUserInfo());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public static FirebaseStorage getFirebaseStorage() {
        if (firebaseStorage == null) {
            firebaseStorage = FirebaseStorage.getInstance();
        }
        return firebaseStorage;
    }

    public static GoogleSignInClient getGoogleSignInClient(Context context) {
        if (mGoogleSignInClient.get() == null) {
            GoogleSignInClient gsc = GoogleSignIn.getClient(context, FirebaseRepo.createGoogleSignInOptions());
            mGoogleSignInClient = new WeakReference<GoogleSignInClient>(gsc);
        }
        return mGoogleSignInClient.get();
    }

    public static GoogleSignInOptions createGoogleSignInOptions() {
        if (gso == null) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(defaultWebClientId)
                    .requestEmail()
                    .build();
        }
        return gso;
    }

    public static boolean createCurrentUser() {
        FirebaseUser user = getAuthINSTANCE().getCurrentUser();
        if (user != null) {
            User player = new User(user.getUid(), new UserInfo(
                    user.getDisplayName(),
                    user.getEmail(),
                    String.valueOf(user.getPhotoUrl()),
                    user.isEmailVerified()));
            setCurrentUser(player);
            Log.e("plyyyy",player.toString());
            return true;
        } else {
            return false;
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        FirebaseRepo.currentUser = currentUser;
    }

    public static FirebaseAuth getAuthINSTANCE() {
        if (authINSTANCE == null) {
            authINSTANCE = FirebaseAuth.getInstance();
        }

        return authINSTANCE;
    }

    public static DatabaseReference getDatabaseInstance() {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

}
