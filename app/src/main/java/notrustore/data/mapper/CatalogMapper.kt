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
