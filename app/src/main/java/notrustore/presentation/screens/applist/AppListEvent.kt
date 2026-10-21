package notrustore.presentation.screens.applist

sealed interface AppListEvent {
    data object ToolbarClicked : AppListEvent
}
