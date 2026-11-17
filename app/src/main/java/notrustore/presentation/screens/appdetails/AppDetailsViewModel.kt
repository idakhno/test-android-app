package notrustore.presentation.screens.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import notrustore.domain.usecase.GetAppUseCase
import notrustore.domain.usecase.GetAppsUseCase
import notrustore.presentation.navigation.Routes

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAppUseCase: GetAppUseCase,
    private val getAppsUseCase: GetAppsUseCase,
) : ViewModel() {

    private val appId: String = checkNotNull(savedStateHandle[Routes.APP_ID_ARG])

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<AppDetailsEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        getAppDetails()
    }

    private var installJob: Job? = null

    fun onInstallClick() {
        val installState = (_state.value as? AppDetailsState.Content)?.installState ?: return

        when (installState) {
            is InstallState.Installing -> {
                installJob?.cancel()
                installJob = null
                updateInstallState(InstallState.NotInstalled)
            }
            InstallState.NotInstalled -> {
                updateInstallState(InstallState.Installing(0))
                installJob = viewModelScope.launch {
                    emulateInstall()
                }
            }
            InstallState.Installed -> Unit
        }
    }

    private suspend fun emulateInstall() {
        for (progress in (5..100) step 5) {
            delay(150.milliseconds)
            updateInstallState(InstallState.Installing(progress))
        }
        updateInstallState(InstallState.Installed)
    }

    private fun updateInstallState(installState: InstallState) {
        _state.update { current ->
            (current as? AppDetailsState.Content)?.copy(installState = installState) ?: current
        }
    }

    fun showUnderDevelopmentMessage() {
        viewModelScope.launch {
            _events.send(AppDetailsEvent.UnderDevelopment)
        }
    }

    fun collapseDescription() {
        _state.update { currentState ->
            (currentState as? AppDetailsState.Content)?.copy(descriptionCollapsed = true) ?: currentState
        }
    }

    fun getAppDetails() {
        installJob?.cancel()
        installJob = null
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            try {
                val appDetails = getAppUseCase(appId)
                val apps = try {
                    getAppsUseCase()
                } catch (error: CancellationException) {
                    throw error
                } catch (_: Exception) {
                    emptyList()
                }
                _state.value = AppDetailsState.Content(
                    appDetails = appDetails,
                    similarApps = apps,
                    showcaseApps = apps.take(SHOWCASE_PAGE_COUNT * SHOWCASE_APPS_PER_PAGE),
                    descriptionCollapsed = false,
                )
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _state.value = AppDetailsState.Error
            }
        }
    }
}
