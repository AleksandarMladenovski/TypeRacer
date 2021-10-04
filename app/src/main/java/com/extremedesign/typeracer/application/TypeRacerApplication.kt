package com.extremedesign.typeracer.application

import android.app.Application
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel
import com.extremedesign.typeracer.fragment.ChangePhotoFragment
import com.extremedesign.typeracer.fragment.DisplayPlayerFragment
import com.extremedesign.typeracer.fragment.GatherPlayersFragment
import com.extremedesign.typeracer.fragment.StartGameFragment
import com.extremedesign.typeracer.fragment.UI.CustomActionBarFragment
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment
import com.extremedesign.typeracer.fragment.UI.PasswordEditTextFragment
import com.extremedesign.typeracer.fragment.login_fragments.LoginFragment
import com.extremedesign.typeracer.fragment.login_fragments.RegisterFragment
import com.extremedesign.typeracer.fragment.login_fragments.UserPasswordResetFragment
import com.extremedesign.typeracer.persistance.PreferencesTypeRacer
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
        single { UserPasswordResetFragment() }
        single { ChangePhotoFragment() }
        single { DisplayPlayerFragment() }
        single { GatherPlayersFragment() }
        single { StartGameFragment() }
        single { CustomActionBarFragment() }
        single { EmailEditTextFragment() }
        single { PasswordEditTextFragment() }
        viewModel { RepositoryViewModel(get()) }
    }
}

