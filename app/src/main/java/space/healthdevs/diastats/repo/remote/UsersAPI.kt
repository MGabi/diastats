package space.healthdevs.diastats.repo.remote

import android.arch.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import space.healthdevs.diastats.models.User

interface UsersAPI {
    @GET("/users")
    fun getAllUsers() : Call<List<User>>
}