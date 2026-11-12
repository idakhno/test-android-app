package notrustore.presentation.screens.appdetails

import androidx.compose.runtime.Immutable

@Immutable
sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState
    data object Installed : InstallState
}
