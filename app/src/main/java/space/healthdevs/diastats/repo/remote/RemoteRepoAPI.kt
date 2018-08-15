package space.healthdevs.diastats.repo.remote

import space.healthdevs.diastats.repo.Repository

interface RemoteRepoAPI {
    fun getAllUsers(callback: Repository.OnUsersFetched)
}