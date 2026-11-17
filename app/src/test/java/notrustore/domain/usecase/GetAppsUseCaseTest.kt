package notrustore.domain.usecase

import kotlinx.coroutines.test.runTest
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category
import notrustore.domain.repository.FakeAppRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetAppsUseCaseTest {

    @Test
    fun returnsAppsFromRepository() = runTest {
        val repository = FakeAppRepository()
        repository.apps = listOf(appInfo("1"), appInfo("2"))
        val useCase = GetAppsUseCase(repository)

        assertEquals(listOf(appInfo("1"), appInfo("2")), useCase())
    }

    @Test
    fun returnsEmptyListWhenCatalogIsEmpty() = runTest {
        val useCase = GetAppsUseCase(FakeAppRepository())

        assertEquals(emptyList<AppInfo>(), useCase())
    }

    @Test
    fun propagatesRepositoryError() = runTest {
        val repository = FakeAppRepository()
        repository.error = RuntimeException("catalog failed")
        val useCase = GetAppsUseCase(repository)

        val result = runCatching { useCase() }

        assertTrue(result.exceptionOrNull() is RuntimeException)
        assertEquals("catalog failed", result.exceptionOrNull()?.message)
    }

    private fun appInfo(id: String) = AppInfo(
        id = id,
        iconUrl = "icon",
        name = "App $id",
        description = "description",
        category = Category.MUSIC,
        developer = "Developer",
        ageRating = 12,
        size = 25.5f,
        screenshotUrlList = listOf("s1"),
    )
}
