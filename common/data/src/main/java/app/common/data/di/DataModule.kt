package app.common.data.di

import androidx.preference.PreferenceManager
import androidx.room.Room
import app.common.data.BuildConfig
import app.common.data.DataManager
import app.common.data.db.ContactsDatabase
import app.common.data.domain.contacts.*
import app.common.data.network.interceptor.TokenInterceptor
import app.common.data.pref.SharedPref
import app.common.data.rx.SchedulerProvider
import app.common.data.rx.SchedulerProviderImpl
import app.common.data.util.RetrofitHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDataModule() = loadModule

private val loadModule by lazy {
    loadKoinModules(
        listOf(
            dataManagerModule,
            prefModule,
            okHttpModule,
            contactsModule,
            roomModule
        )
    )
}

val dataManagerModule = module {
    single {
        DataManager(get())
    }
}

val okHttpModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(get<TokenInterceptor>())
        builder.build()
    }

    single<SchedulerProvider> { SchedulerProviderImpl() }
    single { TokenInterceptor(get()) }
}

val prefModule = module {
    single { SharedPref(get()) }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
}

val contactsModule = module {
    single {
        RetrofitHelper.createService(BuildConfig.API_BASE_URL, get(), ContactsApi::class.java)
    }
    factory { ContactSynchronizer(get(), get()) }
    factory { ContactsLocalDataSrc(get()) }
    factory { ContactsRemoteDataSrc(get()) }

    factory { ContactsRetriever(androidContext().contentResolver) }
    factory { ContactsProviderDataSrc(get()) }

    factory { ContactsRepo(get(), get(), get()) }
}

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), ContactsDatabase::class.java, "ContactsDatabase")
            .build()
    }
}
