package notrustore.presentation.screens.appdetails

import androidx.compose.runtime.Immutable
import notrustore.domain.model.AppInfo

@Immutable
sealed interface AppDetailsState {
    data object Error : AppDetailsState
    data object Loading : AppDetailsState
    data class Content(
        val appDetails: AppInfo,
        val similarApps: List<AppInfo>,
        val showcaseApps: List<AppInfo>,
        val descriptionCollapsed: Boolean,
    ) : AppDetailsState
}
