package notrustore.presentation.screens.applist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import notrustore.R
import notrustore.data.model.AppInfo
import notrustore.data.sampleApps
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Size
import notrustore.presentation.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    onAppClick: (AppInfo) -> Unit,
) {
    Scaffold(
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
                )
            )
        }
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
        ) {
            items(sampleApps) { app ->
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

@Preview
@Composable
private fun AppListScreenPreview() {
    NotRuStoreTheme {
        AppListScreen(onAppClick = {})
    }
}