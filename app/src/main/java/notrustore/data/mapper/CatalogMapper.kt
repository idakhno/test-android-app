package notrustore.data.mapper

import notrustore.data.dto.AppDetailsDto
import notrustore.data.dto.CatalogAppDto
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category

fun CatalogAppDto.toDomain(): AppInfo = AppInfo(
    id = id,
    name = name,
    description = description.orEmpty(),
    category = category.toCatalogCategory(),
    iconUrl = iconUrl.orEmpty(),
    developer = "",
    ageRating = 0,
    size = 0f,
    screenshotUrlList = emptyList(),
)

fun AppDetailsDto.toDomain(): AppInfo = AppInfo(
    id = id,
    name = name,
    description = description.orEmpty(),
    category = category.toCatalogCategory(),
    iconUrl = iconUrl.orEmpty(),
    developer = developer.orEmpty(),
    ageRating = ageRating ?: 0,
    size = size ?: 0f,
    screenshotUrlList = screenshotUrlList.orEmpty(),
)

private fun String?.toCatalogCategory(): Category = when (this) {
    "Производительность" -> Category.PRODUCTIVITY
    "Образование" -> Category.EDUCATION
    "Музыка" -> Category.MUSIC
    "Фото и видео", "Фотография" -> Category.PHOTOGRAPHY
    "Здоровье и фитнес", "Здоровье" -> Category.HEALTH
    "Спорт" -> Category.SPORTS
    "Новости" -> Category.NEWS
    "Бизнес" -> Category.BUSINESS
    "Финансы" -> Category.FINANCE
    "Еда и напитки", "Еда" -> Category.FOOD
    "Шопинг", "Покупки" -> Category.SHOPPING
    "Утилиты" -> Category.UTILITIES
    else -> Category.APP
}

fun AppInfo.toCatalogAppDto(): CatalogAppDto = CatalogAppDto(
    id = id,
    name = name,
    description = description.ifEmpty { null },
    category = category.toDtoCategory(),
    iconUrl = iconUrl.ifEmpty { null },
)

fun AppInfo.toAppDetailsDto(): AppDetailsDto = AppDetailsDto(
    id = id,
    name = name,
    description = description.ifEmpty { null },
    category = category.toDtoCategory(),
    iconUrl = iconUrl.ifEmpty { null },
    developer = developer.ifEmpty { null },
    ageRating = ageRating.takeIf { it > 0 },
    size = size.takeIf { it > 0f },
    screenshotUrlList = screenshotUrlList.takeIf { it.isNotEmpty() },
)

private fun Category.toDtoCategory(): String? = when (this) {
    Category.APP -> null
    Category.PRODUCTIVITY -> "Производительность"
    Category.EDUCATION -> "Образование"
    Category.MUSIC -> "Музыка"
    Category.PHOTOGRAPHY -> "Фото и видео"
    Category.HEALTH -> "Здоровье и фитнес"
    Category.SPORTS -> "Спорт"
    Category.NEWS -> "Новости"
    Category.BUSINESS -> "Бизнес"
    Category.FINANCE -> "Финансы"
    Category.FOOD -> "Еда и напитки"
    Category.SHOPPING -> "Шопинг"
    Category.UTILITIES -> "Утилиты"
}
