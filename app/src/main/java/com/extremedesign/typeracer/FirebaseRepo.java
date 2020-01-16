package com.extremedesign.typeracer;

import com.extremedesign.typeracer.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRepo {
    private static FirebaseAuth authINSTANCE;
    private static DatabaseReference databaseReference;
    private static User currentUser;

    public static void setAuthINSTANCE(FirebaseAuth authINSTANCE) {
        FirebaseRepo.authINSTANCE = authINSTANCE;
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
    public static DatabaseReference getDatabaseInstance(){
        if(databaseReference==null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

}
