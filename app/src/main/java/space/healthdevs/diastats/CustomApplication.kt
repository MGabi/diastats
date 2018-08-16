package space.healthdevs.diastats

import android.app.Application
import android.util.Log
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.regions.Regions
import com.mcxiaoke.koi.KoiConfig
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import space.healthdevs.diastats.koin.AppModules

@Suppress("unused")
class CustomApplication : Application() {

    lateinit var userPool: CognitoUserPool

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        userPool = CognitoUserPool(this,
                BuildConfig.COGNITO_POOL_ID,
                BuildConfig.COGNITO_CLIENT_ID,
                BuildConfig.COGNITO_CLIENT_SECRET,
                Regions.EU_WEST_2)
        KoiConfig.logEnabled = true //default is false
        KoiConfig.logLevel = Log.VERBOSE
        startKoin(this, AppModules.modules)
    }
}