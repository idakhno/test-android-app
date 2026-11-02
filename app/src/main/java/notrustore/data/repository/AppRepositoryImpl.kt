package notrustore.data.repository

import notrustore.data.mapper.toDomain
import notrustore.data.source.AppsLocalDataSource
import notrustore.domain.model.AppInfo
import notrustore.domain.repository.AppRepository

class AppRepositoryImpl(
    private val localDataSource: AppsLocalDataSource,
) : AppRepository {

    override suspend fun getApps(): List<AppInfo> =
        localDataSource.getApps().toDomain()

    override suspend fun getApp(name: String): AppInfo? =
        localDataSource.getApps().find { it.name == name }?.toDomain()
}
