package notrustore.domain.usecase

import kotlinx.coroutines.test.runTest
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category
import notrustore.domain.repository.FakeAppRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetAppUseCaseTest {

    @Test
    fun returnsAppById() = runTest {
        val repository = FakeAppRepository()
        repository.details = mapOf("7" to appInfo())
        val useCase = GetAppUseCase(repository)

        assertEquals(appInfo(), useCase("7"))
    }

    @Test
    fun passesIdToRepository() = runTest {
        val repository = FakeAppRepository()
        repository.details = mapOf("7" to appInfo())
        val useCase = GetAppUseCase(repository)

        useCase("7")

        assertEquals(listOf("7"), repository.requestedAppIds)
    }

    @Test
    fun propagatesRepositoryError() = runTest {
        val repository = FakeAppRepository()
        repository.error = RuntimeException("details failed")
        val useCase = GetAppUseCase(repository)

        val result = runCatching { useCase("7") }

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }

    private fun appInfo() = AppInfo(
        id = "7",
        iconUrl = "icon",
        name = "App 7",
        description = "description",
        category = Category.MUSIC,
        developer = "Developer",
        ageRating = 12,
        size = 25.5f,
        screenshotUrlList = listOf("s1"),
    )
}
