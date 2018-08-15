package space.healthdevs.diastats.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.toast
import kotlinx.android.synthetic.main.activity_login.*
import space.healthdevs.diastats.R
import space.healthdevs.diastats.models.User

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private var currentEmail: String = ""
    private var currentPass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setupViews()
        observe()
    }

    private fun setupViews() {
        act_login_button_login.setOnClickListener {
            currentEmail = act_login_et_email.text.toString()
            currentPass = act_login_et_password.text.toString()
            viewModel.fetchUsers()
        }

        act_login_button_register.setOnClickListener {
            Snackbar.make(act_login_main_layout, "Register!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observe() {
        viewModel.usersObservable.observe(this, Observer<List<User>> { users ->
            if (users?.size == 0 || users == null)
                return@Observer
            users.forEach {
                if (it.email == currentEmail && it.password == currentPass) {
                    toast("User exists!")
                    return@Observer
                }
            }
            toast("User doesn't exists")
        })
    }
}