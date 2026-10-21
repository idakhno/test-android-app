package notrustore.presentation.screens.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import notrustore.data.sampleApps
import notrustore.presentation.navigation.Routes

class AppDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val appName: String = checkNotNull(savedStateHandle[Routes.APP_NAME_ARG])

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<AppDetailsEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        getAppDetails()
    }

    fun showUnderDevelopmentMessage() {
        viewModelScope.launch {
            _events.send(AppDetailsEvent.UnderDevelopment)
        }
    }

    fun collapseDescription() {
        _state.update { currentState ->
            if (currentState is AppDetailsState.Content) {
                currentState.copy(descriptionCollapsed = true)
            } else {
                currentState
            }
        }
    }

    fun getAppDetails() {
        val appDetails = sampleApps.find { it.name == appName }
        _state.value = if (appDetails != null) {
            AppDetailsState.Content(
                appDetails = appDetails,
                similarApps = sampleApps,
                showcaseApps = showcaseApps,
                descriptionCollapsed = false,
            )
        } else {
            AppDetailsState.Error
        }
    }
}
