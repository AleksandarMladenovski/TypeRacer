package com.extremedesign.typeracer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class UnUsedCode {
    //Images

//    private OnCompleteListener<Uri> downloadImageFromStorage(final String imageName, final String imageUrl, final boolean isJobFinished, final JobWorker listener) {
//        return new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    String storageImageUrl = String.valueOf(task.getResult());
//                    ProfileImage newImage = new ProfileImage(imageName, storageImageUrl);
//                    if (storageImageUrl.equals(imageUrl)) {
//                        if(isJobFinished)
//                            listener.jobFinished(true);
//                    } else {
//                        if (imageUrl==null) {
//                            downloadImage_SaveInternalMem(imageName, storageImageUrl);
//                        }
//                        databaseDataSource.insertDataProfileImage(newImage, new JobWorker() {
//                            @Override
//                            public void jobFinished(boolean state) {
//                                listener.jobFinished(state);
//                            }
//                        });
//                    }
//                }
//                else {
//                    listener.jobFinished(false);
//                }
//
//            }
//        };
//    }
//
//    public void getImageByName(String name, ProfileImageListener listener) {
//        databaseDataSource.getProfileImageByName(name, listener);
//    }
//
//    private OnCompleteListener<ListResult> getStorageImagesOnCompleteListener(final JobWorker listener) {
//        return new OnCompleteListener<ListResult>() {
//            @Override
//            public void onComplete(@NonNull Task<ListResult> task) {
//                if (task.isSuccessful()) {
//                    int counter = task.getResult().getItems().size();
//                    for (final StorageReference item : task.getResult().getItems()) {
//                        counter--;
//                        final int finalCounter = counter;
//                        final String imageName = item.getName();
//
//                        databaseDataSource.getProfileImageByName(imageName, new ProfileImageListener() {
//                            @Override
//                            public void getImages(List<ProfileImage> images) {
//                                String imageUrl = null;
//                                //image doesn't exist
//                                if (images.size() != 0) {
//                                    imageUrl = images.get(0).getImageUrl();
//                                }
//                                boolean isJobFinished=false;
//                                if (finalCounter == 0) {
//                                    isJobFinished=true;
//                                }
//                                item.getDownloadUrl().addOnCompleteListener(downloadImageFromStorage(imageName, imageUrl, isJobFinished ,listener));
//
//
//                            }
//                        });
//
//                    }
//                } else {
//                    listener.jobFinished(false);
//                }
//            }
//
//
//        };
//    }
//
//    @Override
//    public void synchronizeProfileImages(final JobWorker listener) {
//        FirebaseRepo.getINSTANCE().getFirebaseStorage().getReference().getRoot().child("images")
//                .listAll().addOnCompleteListener(getStorageImagesOnCompleteListener(listener));
//        //get images from google firebase storage
//    }
//
//    public Bitmap loadImageBitmap(String imageName) {
//        Bitmap bitmap = null;
//        FileInputStream fiStream;
//        try {
//
//            fiStream = application.get().openFileInput(imageName);
//            bitmap = BitmapFactory.decodeStream(fiStream);
//            fiStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//
//    public void downloadImage_SaveInternalMem(String imageName, String image_Url) {
//        DownloadImage downloadImage = new DownloadImage(imageName);
//        downloadImage.execute(image_Url);
//    }
//
//    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
//        String imageName;
//
//        public DownloadImage(String imageName) {
//            this.imageName = imageName;
//        }
//
//        private Bitmap downloadImageBitmap(String sUrl) {
//            Bitmap bitmap = null;
//            try {
//                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
//                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
//                inputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            return downloadImageBitmap(params[0]);
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            saveImage(result, imageName);
//        }
//    }
//
//    public void saveImage(Bitmap b, String imageName) {
//        FileOutputStream foStream;
//        try {
//            foStream = application.get().openFileOutput(imageName, Context.MODE_PRIVATE);
//            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
//            ///data/user/0/com.extremedesign.typeracer/files/baloon.jpg
//            foStream.flush();
//            foStream.close();
//        } catch (Exception e) {
//            Log.d("saveImage", "Exception 2, Something went wrong!");
//            e.printStackTrace();
//        }
//    }
}
