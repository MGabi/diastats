package space.healthdevs.diastats.repo

import android.arch.lifecycle.LiveData
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.healthdevs.diastats.models.User
import space.healthdevs.diastats.repo.local.LocalRepoAPI
import space.healthdevs.diastats.repo.remote.RemoteRepoAPI
import space.healthdevs.diastats.repo.remote.UsersAPI

class Repository : LocalRepoAPI, RemoteRepoAPI, KoinComponent {
    private val userApi by inject<UsersAPI>()

    interface OnUsersFetched {
        fun onUsersFetched(users: List<User>)
    }

    override fun getAllUsers(callback: OnUsersFetched) {
        userApi.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                callback.onUsersFetched(response?.body() ?: return)
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {}
        })
    }
}