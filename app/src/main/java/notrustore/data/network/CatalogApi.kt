package notrustore.data.network

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import notrustore.data.dto.CatalogAppDto
import notrustore.data.dto.AppDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getApps(): List<CatalogAppDto>

    @GET("catalog/{id}")
    suspend fun getApp(@Path("id") id: String): AppDetailsDto

    suspend fun getRating(id: String): Float {
        require(id.isNotBlank())
        delay(1500.milliseconds)
        return (30..50).random() / 10f
    }
}
