package space.healthdevs.diastats.ui.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import space.healthdevs.diastats.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        act_login_button_login.setOnClickListener {
            Snackbar.make(act_login_main_layout, "Login!", Snackbar.LENGTH_SHORT).show()
        }
        act_login_button_register.setOnClickListener{
            Snackbar.make(act_login_main_layout, "Register!", Snackbar.LENGTH_SHORT).show()
        }
    }
}