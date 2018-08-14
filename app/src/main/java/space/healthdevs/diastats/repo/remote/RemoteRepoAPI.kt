package space.healthdevs.diastats.repo.remote

import android.arch.lifecycle.LiveData
import retrofit2.Call
import space.healthdevs.diastats.models.User
import space.healthdevs.diastats.repo.Repository

interface RemoteRepoAPI {
    fun getAllUsers(callback: Repository.OnUsersFetched)
}