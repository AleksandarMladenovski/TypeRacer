package com.extremedesign.typeracer.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.listener.ChangeImageListener;

import java.util.List;

public class ProfilePhotoChangeAdapter extends RecyclerView.Adapter<ProfilePhotoChangeAdapter.ProfilePhoto> {
    List<Drawable> photos;
    ChangeImageListener listener;
    public ProfilePhotoChangeAdapter(List<Drawable> photos,ChangeImageListener listener) {
        this.photos = photos;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ProfilePhoto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_photo_change,parent,false);
        return new ProfilePhoto(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePhoto holder, int position) {
    final int position_real=holder.getAdapterPosition();
    final Drawable drawable=photos.get(position_real);
    holder.image_photo.setImageDrawable(drawable);
    holder.image_photo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener.changeImage(drawable,position_real);
        }
    });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ProfilePhoto extends RecyclerView.ViewHolder {
        ImageView image_photo;
        public ProfilePhoto(@NonNull View itemView) {
            super(itemView);
            image_photo=itemView.findViewById(R.id.item_profile_picture);
        }
    }

}
