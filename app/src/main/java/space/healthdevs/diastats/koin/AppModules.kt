package space.healthdevs.diastats.koin

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import space.healthdevs.diastats.repo.Repository

object AppModules {
    private val repositoryModule : Module = applicationContext {
        bean { Repository() }
        bean { Repository::class }
    }

    val modules = listOf(repositoryModule) 
}