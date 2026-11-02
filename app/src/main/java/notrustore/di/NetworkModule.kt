package notrustore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import notrustore.data.network.CatalogApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCatalogApi(): CatalogApi = Retrofit.Builder()
        .baseUrl("http://185.103.109.134/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatalogApi::class.java)
}
