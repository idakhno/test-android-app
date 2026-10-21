package notrustore.presentation.screens.applist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import notrustore.R
import notrustore.data.model.AppInfo
import notrustore.data.sampleApps
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Size
import notrustore.presentation.theme.Spacing

@Composable
fun AppListScreen(
    onAppClick: (AppInfo) -> Unit,
) {
    val viewModel = viewModel<AppListViewModel>()
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    ObserveEvents(
        events = viewModel.events,
        snackbarHostState = snackbarHostState,
    )

    AppListContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onToolbarClick = { viewModel.onToolbarClick() },
        onAppClick = onAppClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppListContent(
    state: AppListState,
    snackbarHostState: SnackbarHostState,
    onToolbarClick: () -> Unit,
    onAppClick: (AppInfo) -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = stringResource(R.string.app_name),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(Size.topBarIcon),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.clickable(onClick = onToolbarClick),
            )
        }
    ) { contentPadding ->
        when (state) {
            is AppListState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is AppListState.Content -> {
                LazyColumn(
                    contentPadding = contentPadding,
                ) {
                    items(state.apps) { app ->
                        AppItem(
                            app = app,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAppClick(app) }
                                .padding(horizontal = Spacing.l, vertical = Spacing.m),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ObserveEvents(
    events: Flow<AppListEvent>,
    snackbarHostState: SnackbarHostState,
) {
    val toolbarMessage = stringResource(R.string.app_list_toolbar_message)

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is AppListEvent.ToolbarClicked -> {
                    snackbarHostState.showSnackbar(toolbarMessage)
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppListScreenPreview() {
    NotRuStoreTheme {
        AppListContent(
            state = AppListState.Content(apps = sampleApps),
            snackbarHostState = remember { SnackbarHostState() },
            onToolbarClick = {},
            onAppClick = {},
        )
    }
}
