package com.example.typeracer.application

import android.app.Application
import com.example.typeracer.data_repository.repository.UserRepository
import com.example.typeracer.data_repository.viewmodel.UserViewModel
import com.example.typeracer.persistance.PreferencesTypeRacer
import com.example.typeracer.ui.login.LoginFragment
import com.example.typeracer.ui.login.RegisterFragment
import com.example.typeracer.ui.login.ResetPasswordFragment
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
        single { LoginFragment() }
        single { RegisterFragment() }
        single { ResetPasswordFragment() }
        single { UserRepository() }
        viewModel { UserViewModel(get()) }
    }
}

