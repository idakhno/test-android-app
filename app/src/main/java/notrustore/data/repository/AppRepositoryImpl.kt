package notrustore.data.repository

import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> =
        ids.associateWith { id -> api.getRating(id) }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> =
        coroutineScope {
            ids.map { id ->
                async { id to api.getRating(id) }
            }.awaitAll().toMap()
        }
}
