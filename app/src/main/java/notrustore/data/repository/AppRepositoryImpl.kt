package notrustore.data.repository

import javax.inject.Inject
import notrustore.data.mapper.toDomain
import notrustore.data.network.CatalogApi
import notrustore.domain.model.AppInfo
import notrustore.domain.repository.AppRepository

class AppRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
) : AppRepository {

    override suspend fun getApps(): List<AppInfo> =
        api.getApps().map { it.toDomain() }

    override suspend fun getApp(id: String): AppInfo =
        api.getApp(id).toDomain()
}
