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
import notrustore.domain.repository.AppRepository
import notrustore.presentation.navigation.Routes

class AppDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
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
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            val appDetails = repository.getApp(appName)
            _state.value = if (appDetails != null) {
                val apps = repository.getApps()
                AppDetailsState.Content(
                    appDetails = appDetails,
                    similarApps = apps,
                    showcaseApps = apps.take(SHOWCASE_PAGE_COUNT * SHOWCASE_APPS_PER_PAGE),
                    descriptionCollapsed = false,
                )
            } else {
                AppDetailsState.Error
            }
        }
    }
}
