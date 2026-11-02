package notrustore.domain.model

data class AppInfo(
    val iconUrl: String,
    val name: String,
    val description: String,
    val category: Category,
    val developer: String,
    val ageRating: Int,
    val size: Float,
    val screenshotUrlList: List<String>,
)
