package space.healthdevs.diastats.repo

import org.koin.standalone.KoinComponent
import space.healthdevs.diastats.repo.local.LocalRepoAPI
import space.healthdevs.diastats.repo.remote.RemoteRepoAPI

class Repository : LocalRepoAPI, RemoteRepoAPI, KoinComponent