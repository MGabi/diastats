package space.healthdevs.diastats.ui.activities.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.longToast
import kotlinx.android.synthetic.main.activity_login.*
import space.healthdevs.diastats.CustomApplication
import space.healthdevs.diastats.R
import space.healthdevs.diastats.models.CognitoUserState
import space.healthdevs.diastats.ui.activities.main.MainActivity
import space.healthdevs.diastats.ui.activities.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private var currentEmail: String = ""
    private var currentPass: String = ""

    private val cognitoUserStateObserver = Observer<CognitoUserState> {
        it?.let { userState ->
            act_login_btn_login.isEnabled = true
            longToast(userState.message)
            if (userState.success) {
                startActivity(Intent(this, MainActivity::class.java))
                //login successful, proceed to next screen with currentEmail
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setupViews()
        observe()
    }

    private fun setupViews() {
        act_login_btn_login.setOnClickListener {
            currentEmail = act_login_et_email.text.toString()
            currentPass = act_login_et_password.text.toString()
            it.isEnabled = false
            viewModel.signInUser((application as CustomApplication).userPool, currentEmail, currentPass)
        }

        act_login_btn_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.cognitoUserStateObservable.observe(this, cognitoUserStateObserver)
    }

    override fun onDestroy() {
        viewModel.cognitoUserStateObservable.removeObserver(cognitoUserStateObserver)
        super.onDestroy()
    }
}