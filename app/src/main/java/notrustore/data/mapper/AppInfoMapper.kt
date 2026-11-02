package notrustore.data.mapper

import notrustore.data.dto.AppInfoDto
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category

fun AppInfoDto.toDomain(): AppInfo = AppInfo(
    iconUrl = iconUrl.orEmpty(),
    name = name,
    description = description.orEmpty(),
    category = category.toCategory(),
    developer = developer.orEmpty(),
    ageRating = ageRating ?: 0,
    size = sizeMb ?: 0f,
    screenshotUrlList = screenshots.orEmpty(),
)

fun List<AppInfoDto>.toDomain(): List<AppInfo> = map { it.toDomain() }

private fun String?.toCategory(): Category =
    Category.entries.find { it.name.equals(this, ignoreCase = true) } ?: Category.APP
