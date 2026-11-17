package notrustore.domain.usecase

import kotlinx.coroutines.test.runTest
import notrustore.domain.repository.FakeAppRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoadRatingsSequentiallyUseCaseTest {

    @Test
    fun returnsRatingsFromRepository() = runTest {
        val repository = FakeAppRepository()
        repository.ratings = mapOf("a" to 4.5f, "b" to 3.0f)
        val useCase = LoadRatingsSequentiallyUseCase(repository)

        assertEquals(mapOf("a" to 4.5f, "b" to 3.0f), useCase(listOf("a", "b")))
    }

    @Test
    fun passesIdsToRepository() = runTest {
        val repository = FakeAppRepository()
        repository.ratings = mapOf("a" to 4.5f, "b" to 3.0f, "c" to 5.0f)
        val useCase = LoadRatingsSequentiallyUseCase(repository)

        useCase(listOf("a", "b", "c"))

        assertEquals(listOf(listOf("a", "b", "c")), repository.sequentialRequests)
    }

    @Test
    fun withEmptyIdsReturnsEmptyMap() = runTest {
        val useCase = LoadRatingsSequentiallyUseCase(FakeAppRepository())

        assertEquals(emptyMap<String, Float>(), useCase(emptyList()))
    }

    @Test
    fun propagatesRepositoryError() = runTest {
        val repository = FakeAppRepository()
        repository.error = RuntimeException("ratings failed")
        val useCase = LoadRatingsSequentiallyUseCase(repository)

        val result = runCatching { useCase(listOf("a")) }

        assertTrue(result.exceptionOrNull() is RuntimeException)
    }
}
