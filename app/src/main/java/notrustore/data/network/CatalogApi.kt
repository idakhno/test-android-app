package notrustore.data.network

import notrustore.data.dto.CatalogAppDto
import notrustore.data.dto.AppDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getApps(): List<CatalogAppDto>

    @GET("catalog/{id}")
    suspend fun getApp(@Path("id") id: String): AppDetailsDto
}
