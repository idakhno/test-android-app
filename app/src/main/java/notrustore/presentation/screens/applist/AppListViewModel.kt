package notrustore.presentation.screens.applist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.launch
import notrustore.domain.repository.AppRepository

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository,
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
                val ratings = repository.loadRatingsSequentially(ids)
                _state.update { current ->
                    if (current is AppListState.Content) current.copy(ratings = ratings) else current
                }
            }
            _state.update { current ->
                if (current is AppListState.Content) {
                    current.copy(sequentialTimeMillis = sequentialTime)
                } else current
            }

            val parallelTime = measureTimeMillis {
                val ratings = repository.loadRatingsInParallel(ids)
                _state.update { current ->
                    if (current is AppListState.Content) current.copy(ratings = ratings) else current
                }
            }
            _state.update { current ->
                if (current is AppListState.Content) {
                    current.copy(parallelTimeMillis = parallelTime)
                } else current
            }
            Log.d("AppListViewModel",
                "Ratings: count=${ids.size}, sequential=${sequentialTime}ms, parallel=${parallelTime}ms")
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            Log.e("AppListViewModel", "Failed to load ratings", error)
            _state.update { current ->
                if (current is AppListState.Content) current.copy(ratingsFailed = true) else current
            }
        }
    }

    private fun getApps() {
        viewModelScope.launch {
            try {
                val apps = repository.getApps()
                _state.value = AppListState.Content(apps = apps)
                loadRatings(apps.map { it.id })
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                Log.e("AppListViewModel", "Failed to load catalog", error)
                _state.value = AppListState.Error
            }
        }
    }
}
