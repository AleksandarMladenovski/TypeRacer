package com.example.typeracer.application

import android.app.Application
import com.example.typeracer.data_repository.repository.GameRepository
import com.example.typeracer.data_repository.repository.StorageRepository
import com.example.typeracer.data_repository.repository.UserRepository
import com.example.typeracer.data_repository.viewmodel.UserViewModel
import com.example.typeracer.persistance.PreferencesTypeRacer
import com.example.typeracer.ui.activity.SplashViewModel
import com.example.typeracer.ui.custom_lobby.model.CustomLobbyFragment
import com.example.typeracer.ui.custom_lobby.viemodel.CustomLobbyViewModel
import com.example.typeracer.ui.edit_profile.model.EditProfileFragment
import com.example.typeracer.ui.edit_profile.viewmodel.EditProfileViewModel
import com.example.typeracer.ui.game.model.GameFragment
import com.example.typeracer.ui.game.model.SplashGameFragment
import com.example.typeracer.ui.game.viewmodel.GameViewModel
import com.example.typeracer.ui.home.HomeFragment
import com.example.typeracer.ui.home.HomeViewModel
import com.example.typeracer.ui.login.LoginFragment
import com.example.typeracer.ui.login.RegisterFragment
import com.example.typeracer.ui.login.ResetPasswordFragment
import com.example.typeracer.ui.settings.model.SettingsFragment
import com.example.typeracer.ui.settings.viewmodel.SettingsViewModel
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

@ExperimentalUnsignedTypes
class TypeRacerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TypeRacerApplication)
            modules(myModule)
        }
    }

    private val myModule = module {
        single { PreferencesTypeRacer(androidContext()) }
        single { HomeFragment() }
        single { LoginFragment() }
        single { RegisterFragment() }
        single { ResetPasswordFragment() }
        single { SettingsFragment() }
        single { CustomLobbyFragment() }
        single { EditProfileFragment() }
        single { SplashGameFragment() }
        single { GameFragment() }

        single { UserRepository() }
        single { StorageRepository() }
        single { GameRepository() }
        viewModel { CustomLobbyViewModel() }
        viewModel { EditProfileViewModel(get()) }
        viewModel { UserViewModel(get(), get()) }
        viewModel { HomeViewModel(get(), get()) }
        viewModel { SplashViewModel(get(), get()) }
        viewModel { GameViewModel(get()) }
        viewModel { SettingsViewModel(get()) }
    }
}

