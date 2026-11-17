package notrustore.domain.usecase

import kotlinx.coroutines.test.runTest
import notrustore.domain.repository.FakeAppRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoadRatingsInParallelUseCaseTest {

    @Test
    fun returnsRatingsFromRepository() = runTest {
        val repository = FakeAppRepository()
        repository.ratings = mapOf("a" to 4.5f, "b" to 3.0f)
        val useCase = LoadRatingsInParallelUseCase(repository)

        assertEquals(mapOf("a" to 4.5f, "b" to 3.0f), useCase(listOf("a", "b")))
    }

    @Test
    fun passesIdsToRepository() = runTest {
        val repository = FakeAppRepository()
        repository.ratings = mapOf("a" to 4.5f, "b" to 3.0f, "c" to 5.0f)
        val useCase = LoadRatingsInParallelUseCase(repository)

        useCase(listOf("a", "b", "c"))

        assertEquals(listOf(listOf("a", "b", "c")), repository.parallelRequests)
    }

    @Test
    fun withEmptyIdsReturnsEmptyMap() = runTest {
        val useCase = LoadRatingsInParallelUseCase(FakeAppRepository())

        assertEquals(emptyMap<String, Float>(), useCase(emptyList()))
    }

    @Test
    fun propagatesRepositoryError() = runTest {
        val repository = FakeAppRepository()
        repository.error = RuntimeException("ratings failed")
        val useCase = LoadRatingsInParallelUseCase(repository)

        val result = runCatching { useCase(listOf("a")) }

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }
}
