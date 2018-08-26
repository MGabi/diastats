package space.healthdevs.diastats.koin

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import space.healthdevs.diastats.repo.Repository
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import space.healthdevs.diastats.BuildConfig
import space.healthdevs.diastats.repo.remote.UsersAPI

object AppModules {
    private val repositoryModule: Module = applicationContext {
        bean { Repository() }
        bean { Repository::class }
    }

    private val retroUsersModule: Module = applicationContext {
        bean {
            val gSon = GsonBuilder().setLenient().create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
            client.addInterceptor(interceptor)

            Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .client(client.build())
                    .build()
                    .create(UsersAPI::class.java)
        }

        bean { UsersAPI::class.java }
    }

    val modules = listOf(repositoryModule, retroUsersModule)
}