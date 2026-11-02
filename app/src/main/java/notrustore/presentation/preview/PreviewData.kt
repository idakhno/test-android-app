package notrustore.presentation.preview

import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category

internal val previewApp = AppInfo(
    iconUrl = "https://fastly.picsum.photos/id/237/200/200.jpg?hmac=zHUGikXUDyLCCmvyww1izLK3R3k8oRYBRiTizZEdyfI",
    name = "QuickNote",
    description = "Быстрые заметки и списки дел с синхронизацией между устройствами",
    category = Category.PRODUCTIVITY,
    developer = "Notely Labs",
    ageRating = 0,
    size = 24.5f,
    screenshotUrlList = listOf(
        "https://picsum.photos/seed/quicknote-1/640/360",
        "https://picsum.photos/seed/quicknote-2/640/360",
        "https://picsum.photos/seed/quicknote-3/640/360",
    ),
)

internal val previewApps = List(9) { index -> previewApp.copy(name = "${previewApp.name} $index") }
