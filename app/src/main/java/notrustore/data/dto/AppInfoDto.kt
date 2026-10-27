package notrustore.data.dto

data class AppInfoDto(
    val iconUrl: String?,
    val name: String,
    val description: String?,
    val category: String?,
    val developer: String?,
    val ageRating: Int?,
    val sizeMb: Float?,
    val screenshots: List<String>?,
)
