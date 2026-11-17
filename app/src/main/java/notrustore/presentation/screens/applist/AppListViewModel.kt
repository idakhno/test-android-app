package notrustore.presentation.screens.applist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import notrustore.domain.model.AppInfo
import notrustore.domain.usecase.GetAppsUseCase
import notrustore.domain.usecase.LoadRatingsInParallelUseCase
import notrustore.domain.usecase.LoadRatingsSequentiallyUseCase
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
    private val loadRatingsSequentiallyUseCase: LoadRatingsSequentiallyUseCase,
    private val loadRatingsInParallelUseCase: LoadRatingsInParallelUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<AppListEvent>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        getApps()
    }

    fun onToolbarClick() {
        viewModelScope.launch {
            _events.send(AppListEvent.ToolbarClicked)
        }
    }

    private suspend fun loadRatings(ids: List<String>) {
        try {
            val sequentialTime = measureTimeMillis {
                val ratings = loadRatingsSequentiallyUseCase(ids)
                _state.update { current ->
                    (current as? AppListState.Content)?.copy(ratings = ratings) ?: current
                }
            }
            _state.update { current ->
                (current as? AppListState.Content)
                    ?.copy(sequentialTimeMillis = sequentialTime) ?: current
            }

            val parallelTime = measureTimeMillis {
                val ratings = loadRatingsInParallelUseCase(ids)
                _state.update { current ->
                    (current as? AppListState.Content)?.copy(ratings = ratings) ?: current
                }
            }
            _state.update { current ->
                (current as? AppListState.Content)
                    ?.copy(parallelTimeMillis = parallelTime) ?: current
            }
            Log.d(
                "AppListViewModel",
                "Ratings: count=${ids.size}, sequential=${sequentialTime}ms, parallel=${parallelTime}ms",
            )
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Log.e("AppListViewModel", "Failed to load ratings", error)
            _state.update { current ->
                (current as? AppListState.Content)?.copy(ratingsFailed = true) ?: current
            }
        }
    }

    private fun getApps() {
        viewModelScope.launch {
            try {
                val apps = getAppsUseCase()
                _state.value = AppListState.Content(apps = apps)
                loadRatings(apps.map(AppInfo::id))
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                Log.e("AppListViewModel", "Failed to load catalog", error)
                _state.value = AppListState.Error
            }
        }
    }
}
