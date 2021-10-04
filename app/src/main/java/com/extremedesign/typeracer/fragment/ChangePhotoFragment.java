package com.extremedesign.typeracer.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.adapter.ProfilePhotoChangeAdapter;
import com.extremedesign.typeracer.fragment.UI.CustomActionBarFragment;
import com.extremedesign.typeracer.listener.ChangeImageListener;
import com.extremedesign.typeracer.listener.ProfilePictureListener;
import com.extremedesign.typeracer.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePhotoFragment extends Fragment {
    private RepositoryViewModel repositoryViewModel;
    ImageView chosePhoto ;
    ProfilePictureListener listener;
    ProfilePhotoChangeAdapter adapter;
    Drawable selectedDrawable;
    String name;
    private CustomActionBarFragment customActionBarFragment;
    public ChangePhotoFragment() {
        // Required empty public constructor
    }

    public ChangePhotoFragment(ProfilePictureListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        repositoryViewModel= ViewModelProviders.of(this).get(RepositoryViewModel.class);

        View itemView = inflater.inflate(R.layout.fragment_change_photo, container, false);
        customActionBarFragment=new CustomActionBarFragment("Edit Profile");
        getFragmentManager().beginTransaction()
                .replace(R.id.customToolbarLayout_ChangePhotoFragment,customActionBarFragment).commit();

        Button saveChanges=itemView.findViewById(R.id.user_photo_save_changes);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosePhoto.getDrawable();
            }
        });
        chosePhoto=itemView.findViewById(R.id.user_photo_chosen_image);
        repositoryViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                int selected_id = getResources().getIdentifier(repositoryViewModel.getCurrentUser().getValue().getPhotoName(), "drawable", getContext().getPackageName());
                selectedDrawable=getResources().getDrawable(selected_id);
                name= repositoryViewModel.getCurrentUser().getValue().getPhotoName();
                chosePhoto.setImageDrawable(selectedDrawable);
            }
        });

        ArrayList<Drawable> photos_default=new ArrayList<>();
        for(int i=1;i<=10;i++){
            int id = getResources().getIdentifier("default"+i, "drawable", getContext().getPackageName());
            photos_default.add(getContext().getDrawable(id));
        }
        adapter=new ProfilePhotoChangeAdapter(photos_default, new ChangeImageListener() {
            @Override
            public void changeImage(Drawable drawable,int position) {
                chosePhoto.setImageDrawable(drawable);
                selectedDrawable=drawable;
                name="default"+(position+1);
            }

            @Override
            public void applyChanges(String name) {

            }

        });
        RecyclerView recyclerView=itemView.findViewById(R.id.user_photo_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),6));
        recyclerView.setAdapter(adapter);
        return itemView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repositoryViewModel.getCurrentUser().removeObservers(this);
        listener.onPictureChosen(selectedDrawable,name);
    }
}
