package notrustore.di

import notrustore.data.repository.AppRepositoryImpl
import notrustore.data.source.AppsLocalDataSource
import notrustore.domain.repository.AppRepository

object AppContainer {
    val appRepository: AppRepository by lazy {
        AppRepositoryImpl(AppsLocalDataSource())
    }
}
