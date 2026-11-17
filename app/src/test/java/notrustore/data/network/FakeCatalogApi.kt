package notrustore.data.network

import notrustore.data.dto.AppDetailsDto
import notrustore.data.dto.CatalogAppDto

class FakeCatalogApi(
    private val apps: List<CatalogAppDto> = emptyList(),
    private val details: Map<String, AppDetailsDto> = emptyMap(),
    private val ratings: Map<String, Float> = emptyMap(),
    private val error: Exception? = null,
) : CatalogApi {

    val ratingRequests = mutableListOf<String>()

    override suspend fun getApps(): List<CatalogAppDto> {
        error?.let { throw it }
        return apps
    }

    override suspend fun getApp(id: String): AppDetailsDto {
        error?.let { throw it }
        return details.getValue(id)
    }

    override suspend fun getRating(id: String): Float {
        error?.let { throw it }
        ratingRequests += id
        return ratings.getValue(id)
    }
}
