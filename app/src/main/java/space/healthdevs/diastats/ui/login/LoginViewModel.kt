package space.healthdevs.diastats.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import space.healthdevs.diastats.models.User
import space.healthdevs.diastats.repo.Repository

class LoginViewModel : ViewModel(), KoinComponent, Repository.OnUsersFetched {
    private val repo by inject<Repository>()
    val usersObservable = MutableLiveData<List<User>>()

    override fun onUsersFetched(users: List<User>) {
        usersObservable.value = users
    }

    fun fetchUsers() = repo.getAllUsers(this)
}