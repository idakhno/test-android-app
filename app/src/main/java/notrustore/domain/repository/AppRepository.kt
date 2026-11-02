package notrustore.domain.repository

import notrustore.domain.model.AppInfo

interface AppRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun getApp(name: String): AppInfo?
}
