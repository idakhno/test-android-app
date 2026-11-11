package notrustore.domain.repository

import notrustore.domain.model.AppInfo

interface AppRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun getApp(id: String): AppInfo
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}
