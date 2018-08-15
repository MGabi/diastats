package space.healthdevs.diastats.repo.remote

import retrofit2.Call
import retrofit2.http.GET
import space.healthdevs.diastats.models.User

interface UsersAPI {
    @GET("/users")
    fun getAllUsers() : Call<List<User>>
}