package br.com.uzzi.modelviewfirestoreerror;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class restaurantViewModel extends ViewModel {
    private final CollectionReference restaurantCollection = FirebaseFirestore.getInstance().collection("Restaurants");

    private MutableLiveData<FirebaseUser> userLiveData  = new MutableLiveData<>();

    private MutableLiveData<Restaurant> restaurantLiveData = new MutableLiveData<>();

    private Restaurant restaurant = null;

    private void initiateRestaurant(FirebaseUser user) {
        restaurantCollection.document(user.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            restaurant = documentSnapshot.toObject(Restaurant.class);
                            restaurantLiveData.postValue(restaurant);
                            Log.d("RestaurantViewModel", "Got restaurant");
                        } else {
                            restaurantCollection.document(user.getUid())
                                    .set(new Restaurant(user.getPhoneNumber(), user.getDisplayName(), user.getUid()))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            restaurant = new Restaurant(user.getPhoneNumber(), user.getDisplayName(), user.getUid());
                                            restaurantLiveData.postValue(restaurant);
                                            Log.d("RestaurantViewModel", "new restaurant set");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("RestaurantViewModel", "error setting new restaurant");
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("RestaurantViewModel", "error getting restaurant " + user.getDisplayName());
                    }
                });
    }

    public void setUser(FirebaseUser currentUser){
        userLiveData.setValue(currentUser);

        if(currentUser != null){
            initiateRestaurant(currentUser);
        } else {
            restaurant = null;
            restaurantLiveData.setValue(null);
        }
    }

    public LiveData<FirebaseUser> getUser(){
        return userLiveData;
    }
}
