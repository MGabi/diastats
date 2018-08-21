package space.healthdevs.diastats.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.toast
import kotlinx.android.synthetic.main.activity_login.*
import space.healthdevs.diastats.R
import space.healthdevs.diastats.models.User
import space.healthdevs.diastats.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private var currentEmail: String = ""
    private var currentPass: String = ""

    private val userLoggedObserver = Observer<List<User>> { users ->
        if (users?.size == 0 || users == null) {
            toast("Empty")
            return@Observer
        }
        users.forEach {
            if (it.email == currentEmail && it.password == currentPass) {
                toast("Correct user")
                return@Observer
            }
        }
        toast("Wrong user")
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
            viewModel.fetchUsers()
        }

        act_login_btn_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.usersObservable.observe(this, userLoggedObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.usersObservable.removeObserver(userLoggedObserver)
    }
}