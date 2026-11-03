package notrustore.data.dto

data class AppDetailsDto(
    val id: String,
    val name: String,
    val description: String?,
    val category: String?,
    val iconUrl: String?,
    val developer: String?,
    val ageRating: Int?,
    val size: Float?,
    val screenshotUrlList: List<String>?,
)
