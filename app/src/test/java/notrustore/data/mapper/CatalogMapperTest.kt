package notrustore.data.mapper

import notrustore.data.dto.AppDetailsDto
import notrustore.data.dto.CatalogAppDto
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CatalogMapperTest {

    @Test
    fun catalogDtoToDomainMapsAllFields() {
        val dto = CatalogAppDto(
            id = "1",
            name = "First",
            description = "description",
            category = "Музыка",
            iconUrl = "icon",
        )

        val expected = AppInfo(
            id = "1",
            iconUrl = "icon",
            name = "First",
            description = "description",
            category = Category.MUSIC,
            developer = "",
            ageRating = 0,
            size = 0f,
            screenshotUrlList = emptyList(),
        )

        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun catalogDtoToDomainReplacesNullsWithDefaults() {
        val dto = CatalogAppDto(id = "1", name = "First", description = null, category = null, iconUrl = null)

        val expected = AppInfo(
            id = "1",
            iconUrl = "",
            name = "First",
            description = "",
            category = Category.APP,
            developer = "",
            ageRating = 0,
            size = 0f,
            screenshotUrlList = emptyList(),
        )

        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun detailsDtoToDomainMapsAllFields() {
        val dto = AppDetailsDto(
            id = "1",
            name = "First",
            description = "description",
            category = "Спорт",
            iconUrl = "icon",
            developer = "Developer",
            ageRating = 12,
            size = 25.5f,
            screenshotUrlList = listOf("s1", "s2"),
        )

        val expected = AppInfo(
            id = "1",
            iconUrl = "icon",
            name = "First",
            description = "description",
            category = Category.SPORTS,
            developer = "Developer",
            ageRating = 12,
            size = 25.5f,
            screenshotUrlList = listOf("s1", "s2"),
        )

        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun detailsDtoToDomainReplacesNullsWithDefaults() {
        val dto = AppDetailsDto(
            id = "1",
            name = "First",
            description = null,
            category = null,
            iconUrl = null,
            developer = null,
            ageRating = null,
            size = null,
            screenshotUrlList = null,
        )

        val expected = AppInfo(
            id = "1",
            iconUrl = "",
            name = "First",
            description = "",
            category = Category.APP,
            developer = "",
            ageRating = 0,
            size = 0f,
            screenshotUrlList = emptyList(),
        )

        assertEquals(expected, dto.toDomain())
    }

    @Test
    fun knownCategoryStringsMapToCategories() {
        canonicalCategories.forEach { (raw, category) ->
            assertEquals("category string: $raw", category, catalogDto(category = raw).toDomain().category)
        }
    }

    @Test
    fun synonymCategoryStringsMapToSameCategory() {
        val synonyms = mapOf(
            "Фотография" to Category.PHOTOGRAPHY,
            "Здоровье" to Category.HEALTH,
            "Еда" to Category.FOOD,
            "Покупки" to Category.SHOPPING,
        )

        synonyms.forEach { (raw, category) ->
            assertEquals("synonym: $raw", category, catalogDto(category = raw).toDomain().category)
        }
    }

    @Test
    fun unknownCategoryFallsBackToApp() {
        listOf("Игры", "Погода", "", null).forEach { raw ->
            assertEquals("category string: $raw", Category.APP, catalogDto(category = raw).toDomain().category)
        }
    }

    @Test
    fun appInfoToCatalogAppDtoRoundtripPreservesData() {
        val original = CatalogAppDto(
            id = "1",
            name = "First",
            description = "description",
            category = "Музыка",
            iconUrl = "icon",
        )

        assertEquals(original, original.toDomain().toCatalogAppDto())
    }

    @Test
    fun appInfoToCatalogAppDtoRoundtripPreservesNulls() {
        val original = CatalogAppDto(id = "1", name = "First", description = null, category = null, iconUrl = null)

        assertEquals(original, original.toDomain().toCatalogAppDto())
    }

    @Test
    fun appInfoToAppDetailsDtoRoundtripPreservesData() {
        val original = AppDetailsDto(
            id = "1",
            name = "First",
            description = "description",
            category = "Спорт",
            iconUrl = "icon",
            developer = "Developer",
            ageRating = 12,
            size = 25.5f,
            screenshotUrlList = listOf("s1", "s2"),
        )

        assertEquals(original, original.toDomain().toAppDetailsDto())
    }

    @Test
    fun appInfoToAppDetailsDtoRoundtripPreservesNulls() {
        val original = AppDetailsDto(
            id = "1",
            name = "First",
            description = null,
            category = null,
            iconUrl = null,
            developer = null,
            ageRating = null,
            size = null,
            screenshotUrlList = null,
        )

        assertEquals(original, original.toDomain().toAppDetailsDto())
    }

    @Test
    fun appCategoryMapsToNullInDto() {
        val domain = catalogDto(category = null).toDomain()

        assertNull(domain.toCatalogAppDto().category)
        assertNull(domain.toAppDetailsDto().category)
    }

    @Test
    fun everyKnownCategoryRoundtripsThroughDomain() {
        canonicalCategories.forEach { (raw, category) ->
            val domain = catalogDto(category = raw).toDomain()

            assertEquals("dto -> domain: $raw", category, domain.category)
            assertEquals("domain -> catalog dto: $category", raw, domain.toCatalogAppDto().category)
            assertEquals("domain -> details dto: $category", raw, domain.toAppDetailsDto().category)
        }

        val fallback = catalogDto(category = null).toDomain()

        assertEquals(Category.APP, fallback.category)
        assertNull(fallback.toCatalogAppDto().category)
        assertNull(fallback.toAppDetailsDto().category)
    }

    private val canonicalCategories = mapOf(
        "Производительность" to Category.PRODUCTIVITY,
        "Образование" to Category.EDUCATION,
        "Музыка" to Category.MUSIC,
        "Фото и видео" to Category.PHOTOGRAPHY,
        "Здоровье и фитнес" to Category.HEALTH,
        "Спорт" to Category.SPORTS,
        "Новости" to Category.NEWS,
        "Бизнес" to Category.BUSINESS,
        "Финансы" to Category.FINANCE,
        "Еда и напитки" to Category.FOOD,
        "Шопинг" to Category.SHOPPING,
        "Утилиты" to Category.UTILITIES,
    )

    private fun catalogDto(category: String?) = CatalogAppDto(
        id = "1",
        name = "First",
        description = "description",
        category = category,
        iconUrl = "icon",
    )
}
