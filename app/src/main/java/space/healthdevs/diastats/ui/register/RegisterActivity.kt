package space.healthdevs.diastats.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.longToast
import com.mcxiaoke.koi.ext.onClick
import kotlinx.android.synthetic.main.activity_register.*
import space.healthdevs.diastats.CustomApplication
import space.healthdevs.diastats.R
import space.healthdevs.diastats.models.SignupState

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private val signupStateObserver = Observer<SignupState> {
        it?.let { state ->
            act_register_btn_register.isEnabled = true
            longToast(state.message)
            if (state.success) {
                //User successfully sign up, proceed to next screen
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        setupViews()
        observe()
    }

    private fun observe() {
        viewModel.signupStateObservable.observe(this, signupStateObserver)
    }

    private fun setupViews() {
        act_register_btn_register.onClick {
            val email = act_register_et_email.text.toString()
            val password = act_register_et_password.text.toString()
            val passwordConfirm = act_register_et_confirm_password.text.toString()
            it.isEnabled = false

            viewModel.registerUser((application as CustomApplication).userPool,
                    email,
                    password,
                    passwordConfirm)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.signupStateObservable.removeObserver(signupStateObserver)
    }
}
