package space.healthdevs.diastats.ui.activities.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import space.healthdevs.diastats.R
import space.healthdevs.diastats.ui.activities.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1000L)
    }
}
