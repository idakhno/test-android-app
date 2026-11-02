package notrustore.presentation.screens.applist

import androidx.compose.runtime.Immutable
import notrustore.domain.model.AppInfo

@Immutable
sealed interface AppListState {
    data object Loading : AppListState
    data object Error : AppListState
    data class Content(
        val apps: List<AppInfo>,
    ) : AppListState
}
