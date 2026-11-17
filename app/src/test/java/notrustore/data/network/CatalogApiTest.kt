package notrustore.data.network

import kotlinx.coroutines.test.runTest
import notrustore.data.dto.AppDetailsDto
import notrustore.data.dto.CatalogAppDto
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogApiTest {

    private val api = object : CatalogApi {
        override suspend fun getApps(): List<CatalogAppDto> = error("not under test")
        override suspend fun getApp(id: String): AppDetailsDto = error("not under test")
    }

    @Test
    fun getRatingReturnsValueWithinDocumentedRange() = runTest {
        val rating = api.getRating("app-1")

        assertTrue("rating must be in 3.0..5.0, got $rating", rating in (3.0f..5.0f))
    }

    @Test
    fun getRatingRejectsBlankId() = runTest {
        val result = runCatching { api.getRating(" ") }

        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertNull(result.getOrNull())
    }
}
