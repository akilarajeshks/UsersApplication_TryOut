package com.zestworks.application.application

import android.app.Application
import androidx.room.Room
import com.zestworks.application.model.UsersDatabase
import com.zestworks.application.repository.NetworkService
import com.zestworks.application.repository.Repository
import com.zestworks.application.repository.RepositoryImpl
import com.zestworks.application.repository.UsersDAO
import com.zestworks.application.ui.MainActivity
import com.zestworks.application.viewmodel.ApplicationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()


        val appModule = module {
            factory { provideRetrofit() }
            factory { provideDAO() }
            factory { provideNetworkService(get()) }
            single<Repository> { RepositoryImpl(get(), get()) }
            scope(named<MainActivity>()){
                scoped{ ApplicationViewModel(get()) }
            }
        }

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    private fun provideDAO(): UsersDAO {
        val usersDatabase =
            Room.databaseBuilder(this.applicationContext, UsersDatabase::class.java, "users-db")
                .build()

        return usersDatabase.usersDAO()

    }

    private fun provideRetrofit(): Retrofit {

         val baseURL = " https://n161.tech/api/dummyapi/"
         return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            
    }
}