package notrustore.domain.repository

import notrustore.domain.model.AppInfo

class FakeAppRepository : AppRepository {

    var apps: List<AppInfo> = emptyList()
    var details: Map<String, AppInfo> = emptyMap()
    var ratings: Map<String, Float> = emptyMap()
    var error: Exception? = null

    val requestedAppIds = mutableListOf<String>()
    val sequentialRequests = mutableListOf<List<String>>()
    val parallelRequests = mutableListOf<List<String>>()

    override suspend fun getApps(): List<AppInfo> {
        error?.let { throw it }
        return apps
    }

    override suspend fun getApp(id: String): AppInfo {
        error?.let { throw it }
        requestedAppIds += id
        return details.getValue(id)
    }

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        error?.let { throw it }
        sequentialRequests += ids
        return ids.associateWith { ratings.getValue(it) }
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> {
        error?.let { throw it }
        parallelRequests += ids
        return ids.associateWith { ratings.getValue(it) }
    }
}
