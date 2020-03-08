package com.extremedesign.typeracer;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.model.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Executor;

public class FirebaseRepo {
    private  FirebaseAuth authINSTANCE;
    private  DatabaseReference databaseReference;
    private  FirebaseStorage firebaseStorage;
    private MutableLiveData<User> currentUser;
    private  static GoogleSignInOptions gso;
    private  static WeakReference<GoogleSignInClient> mGoogleSignInClient = new WeakReference<GoogleSignInClient>(null);

    public FirebaseRepo(){
        authINSTANCE=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference=FirebaseDatabase.getInstance().getReference();
//        User defaultUser=new User("id","name","email","defaultPhoto",false);
    }


    public void createUserWithEmailAndPassword(String email, String password, final String name) {
        final FirebaseAuth auth = getAuthINSTANCE();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        createCurrentUser(false);
                                    } else {
                                        //TODO NAME CHANGE FAILED
                                    }
                                }
                            });


                        } else {
                            //TODO registration failed
                        }
                    }
                });
    }

    public void saveUserToFirebaseDatabase() {
        final User user = getCurrentUser().getValue();
        if (user != null) {
            final DatabaseReference db = getDatabaseInstance();
            db.child("users").child(user.getUid()).setValue(user);
        }

    }

    //get imageName from userDatabase
    //get exp,coins,etx as well
    public  void createCurrentUser(boolean userExists){
        FirebaseUser user = getAuthINSTANCE().getCurrentUser();
        if (user != null) {
            final User player = new User(user.getUid(),
                    user.getDisplayName(),
                    user.getEmail(),
                    "default1",
                    user.isEmailVerified());
            setCurrentUser(player);
            if(!userExists)
            {

                saveUserToFirebaseDatabase();
            }
            else {
                getDatabaseInstance().child("users").child(player.getUid()).child("photoName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String imageName = String.valueOf(dataSnapshot.getValue());
                        if(dataSnapshot.getValue()!=null){
                            player.setPhotoName(imageName);
                            setCurrentUser(player);
                        }
                        else{
                            saveUserToFirebaseDatabase();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    }
    public void logInWithProvider(final OnCompleteListener<AuthResult> listener, String nameOfProvider, Activity activity) {

        Task<AuthResult> pendingResultTask = getAuthINSTANCE().getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                   getAuthINSTANCE().signInWithCredential(authResult.getCredential())
                                            .addOnCompleteListener(listener);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    listener.onComplete(new Task<AuthResult>() {
                                        @Override
                                        public boolean isComplete() {
                                            return false;
                                        }

                                        @Override
                                        public boolean isSuccessful() {
                                            return false;
                                        }

                                        @Override
                                        public boolean isCanceled() {
                                            return false;
                                        }

                                        @Nullable
                                        @Override
                                        public AuthResult getResult() {
                                            return null;
                                        }

                                        @Nullable
                                        @Override
                                        public <X extends Throwable> AuthResult getResult(@NonNull Class<X> aClass) throws X {
                                            return null;
                                        }

                                        @Nullable
                                        @Override
                                        public Exception getException() {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                                            return null;
                                        }
                                    });
                                }
                            });
        } else {
            OAuthProvider.Builder provider = OAuthProvider.newBuilder(nameOfProvider);
            provider.addCustomParameter("prompt", "login");
            getAuthINSTANCE()
                    .startActivityForSignInWithProvider(activity , provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    getAuthINSTANCE().signInWithCredential(authResult.getCredential())
                                            .addOnCompleteListener(listener);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    listener.onComplete(new Task<AuthResult>() {
                                        @Override
                                        public boolean isComplete() {
                                            return false;
                                        }

                                        @Override
                                        public boolean isSuccessful() {
                                            return false;
                                        }

                                        @Override
                                        public boolean isCanceled() {
                                            return false;
                                        }

                                        @Nullable
                                        @Override
                                        public AuthResult getResult() {
                                            return null;
                                        }

                                        @Nullable
                                        @Override
                                        public <X extends Throwable> AuthResult getResult(@NonNull Class<X> aClass) throws X {
                                            return null;
                                        }

                                        @Nullable
                                        @Override
                                        public Exception getException() {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                                            return null;
                                        }

                                        @NonNull
                                        @Override
                                        public Task<AuthResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                                            return null;
                                        }
                                    });

                                }
                            });
        }
    }



    public  void setCurrentUser(User currentUser) {
        this.currentUser.setValue(currentUser);
    }

    public  FirebaseAuth getAuthINSTANCE() {
        if (authINSTANCE == null) {
            authINSTANCE = FirebaseAuth.getInstance();
        }

        return authINSTANCE;
    }

    public  DatabaseReference getDatabaseInstance() {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }
    public  FirebaseStorage getFirebaseStorage() {
        if (firebaseStorage == null) {
            firebaseStorage = FirebaseStorage.getInstance();
        }
        return firebaseStorage;
    }

    public static GoogleSignInClient getGoogleSignInClient(Context context) {
        if (mGoogleSignInClient.get() == null) {
            GoogleSignInClient gsc = GoogleSignIn.getClient(context, createGoogleSignInOptions());
            mGoogleSignInClient = new WeakReference<GoogleSignInClient>(gsc);
        }
        return mGoogleSignInClient.get();
    }

    public  static GoogleSignInOptions createGoogleSignInOptions() {
        String defaultWebClientId = "850690284059-8k9vmorftm8595r8gu3mu7t7060gkk0t.apps.googleusercontent.com";
        if (gso == null) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(defaultWebClientId)
                    .requestEmail()
                    .build();
        }
        return gso;
    }

    public static void sendPasswordResetEmail(String email, OnCompleteListener<Void> listener) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(listener);
    }

    public  void updateUserProfilePicture(String nameOfProfilePicture) {
         updateCurrentUserProfilePicture(nameOfProfilePicture);
         updateFirebaseUserInDatabase(nameOfProfilePicture);
    }

    private  void updateFirebaseUserInDatabase(final String nameOfProfilePicture) {
        getDatabaseInstance().child("users").child(getCurrentUser().getValue().getUid()).child("photoName").setValue(nameOfProfilePicture);
            final DatabaseReference db = getDatabaseInstance();
//            db.child("users").child(getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.getValue() == null) {
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
    }

    private  void updateCurrentUserProfilePicture(String nameOfProfilePicture) {
        User newUser=getCurrentUser().getValue();
        newUser.setPhotoName(nameOfProfilePicture);
        getCurrentUser().setValue(newUser);
//        Objects.requireNonNull(getCurrentUser().getValue()).setPhotoName(nameOfProfilePicture);
    }

    public MutableLiveData<User> getCurrentUser(){
        if(currentUser==null){
            currentUser=new MutableLiveData<User>();
            createCurrentUser(true);
        }
        return currentUser;
    }

    public boolean isUserLoggedIn() {
        if(authINSTANCE.getCurrentUser()==null){
            return false;
        }
        return true;
    }
}
