package com.extremedesign.typeracer.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.extremedesign.typeracer.FirebaseRepo
import com.extremedesign.typeracer.R
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel
import com.extremedesign.typeracer.fragment.ChangePhotoFragment
import com.extremedesign.typeracer.fragment.DisplayPlayerFragment
import com.extremedesign.typeracer.listener.PlayerDisplayClose
import com.extremedesign.typeracer.listener.ProfilePictureListener
import com.extremedesign.typeracer.model.User
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val viewModel: RepositoryViewModel by sharedViewModel()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        val photoImage = findViewById<ImageView>(R.id.photoImageButton)

        user = viewModel.currentUser.value

        viewModel.currentUser.observe(this, { user ->
            val id = resources.getIdentifier(user.photoName, "drawable", this@MainActivity.packageName)
            photoImage.setImageResource(id)
            this.user = user
        })
        photoImage.setOnClickListener {
            val listener: PlayerDisplayClose = object : PlayerDisplayClose {
                override fun onPlayerDisplayClosed() {
                    findViewById<View>(R.id.main_FrameLayout).visibility = View.GONE
                }

                override fun openPlayerChangePhoto() {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.main_FrameLayout, ChangePhotoFragment(object : ProfilePictureListener {
                                override fun onPictureChosen(drawable: Drawable?, name: String?) {
//                                        photoImage.setImageDrawable(drawable);
                                    viewModel.firebaseRepo.updateUserProfilePicture(name)
                                }
                            }))
                            .addToBackStack("Change Photo")
                            .commit()
                }
            }
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_FrameLayout, DisplayPlayerFragment(user, listener))
                    .addToBackStack("Display User")
                    .commit()
            findViewById<View>(R.id.main_FrameLayout).visibility = View.VISIBLE
        }
    }


}