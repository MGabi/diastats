package space.healthdevs.diastats

import android.app.Application
import org.koin.android.ext.android.startKoin
import space.healthdevs.diastats.koin.AppModules

class CustomApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, AppModules.modules)
    }
}