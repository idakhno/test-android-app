package notrustore.data.repository

import kotlinx.coroutines.test.runTest
import notrustore.data.dto.AppDetailsDto
import notrustore.data.dto.CatalogAppDto
import notrustore.data.network.FakeCatalogApi
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppRepositoryImplTest {

    private val catalogDtos = listOf(
        CatalogAppDto(id = "1", name = "First", description = "d1", category = "Музыка", iconUrl = "u1"),
        CatalogAppDto(id = "2", name = "Second", description = null, category = null, iconUrl = null),
    )

    private val detailsDto = AppDetailsDto(
        id = "1",
        name = "First",
        description = "d1",
        category = "Спорт",
        iconUrl = "u1",
        developer = "Developer",
        ageRating = 12,
        size = 25.5f,
        screenshotUrlList = listOf("s1", "s2"),
    )

    @Test
    fun getAppsReturnsDomainModels() = runTest {
        val repository = AppRepositoryImpl(FakeCatalogApi(apps = catalogDtos))

        val apps = repository.getApps()

        assertEquals(2, apps.size)
        assertEquals("1", apps[0].id)
        assertEquals("First", apps[0].name)
        assertEquals("d1", apps[0].description)
        assertEquals(Category.MUSIC, apps[0].category)
        assertEquals("u1", apps[0].iconUrl)
    }

    @Test
    fun getAppsMapsNullFieldsToDefaults() = runTest {
        val repository = AppRepositoryImpl(FakeCatalogApi(apps = catalogDtos))

        val expected = AppInfo(
            id = "2",
            iconUrl = "",
            name = "Second",
            description = "",
            category = Category.APP,
            developer = "",
            ageRating = 0,
            size = 0f,
            screenshotUrlList = emptyList(),
        )

        assertEquals(expected, repository.getApps()[1])
    }

    @Test
    fun getAppsOnEmptyCatalogReturnsEmptyList() = runTest {
        val repository = AppRepositoryImpl(FakeCatalogApi(apps = emptyList()))

        assertEquals(emptyList<AppInfo>(), repository.getApps())
    }

    @Test
    fun getAppReturnsMappedDetails() = runTest {
        val repository = AppRepositoryImpl(FakeCatalogApi(details = mapOf("1" to detailsDto)))

        val expected = AppInfo(
            id = "1",
            iconUrl = "u1",
            name = "First",
            description = "d1",
            category = Category.SPORTS,
            developer = "Developer",
            ageRating = 12,
            size = 25.5f,
            screenshotUrlList = listOf("s1", "s2"),
        )

        assertEquals(expected, repository.getApp("1"))
    }

    @Test
    fun getAppsPropagatesApiError() = runTest {
        val repository = AppRepositoryImpl(FakeCatalogApi(error = RuntimeException("network down")))

        val result = runCatching { repository.getApps() }

        assertTrue(result.exceptionOrNull() is RuntimeException)
        assertEquals("network down", result.exceptionOrNull()?.message)
    }

    @Test
    fun getAppPropagatesApiError() = runTest {
        val repository = AppRepositoryImpl(FakeCatalogApi(error = RuntimeException("network down")))

        val result = runCatching { repository.getApp("1") }

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }

    @Test
    fun loadRatingsSequentiallyReturnsRatingForEachIdInOrder() = runTest {
        val api = FakeCatalogApi(ratings = mapOf("a" to 4.5f, "b" to 3.0f, "c" to 5.0f))
        val repository = AppRepositoryImpl(api)

        val ratings = repository.loadRatingsSequentially(listOf("a", "b", "c"))

        assertEquals(mapOf("a" to 4.5f, "b" to 3.0f, "c" to 5.0f), ratings)
        assertEquals(listOf("a", "b", "c"), api.ratingRequests)
    }

    @Test
    fun loadRatingsSequentiallyWithEmptyIdsReturnsEmptyMap() = runTest {
        val api = FakeCatalogApi(ratings = mapOf("a" to 4.5f))
        val repository = AppRepositoryImpl(api)

        assertEquals(emptyMap<String, Float>(), repository.loadRatingsSequentially(emptyList()))
        assertEquals(emptyList<String>(), api.ratingRequests)
    }

    @Test
    fun loadRatingsInParallelReturnsRatingsForAllIds() = runTest {
        val api = FakeCatalogApi(ratings = mapOf("a" to 4.5f, "b" to 3.0f, "c" to 5.0f))
        val repository = AppRepositoryImpl(api)

        val ratings = repository.loadRatingsInParallel(listOf("a", "b", "c"))

        assertEquals(mapOf("a" to 4.5f, "b" to 3.0f, "c" to 5.0f), ratings)
        assertEquals(3, api.ratingRequests.size)
        assertTrue(api.ratingRequests.containsAll(listOf("a", "b", "c")))
    }
}
