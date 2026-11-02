package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import notrustore.domain.model.AppInfo
import notrustore.presentation.preview.previewApp
import notrustore.presentation.preview.previewApps
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Spacing

@Composable
fun AppDetailsContent(
    content: AppDetailsState.Content,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onReadMoreClick: () -> Unit,
    onDeveloperClick: () -> Unit,
    onSimilarAppDownloadClick: (AppInfo) -> Unit,
    onShowcaseAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    val appDetails = content.appDetails
    val descriptionCollapsed = content.descriptionCollapsed

    Column(modifier.verticalScroll(rememberScrollState())) {
        Toolbar(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
        )
        Spacer(Modifier.height(Spacing.s))
        AppDetailsHeader(
            appDetails = appDetails,
            modifier = Modifier.padding(horizontal = Spacing.l),
        )
        Spacer(Modifier.height(Spacing.l))
        InstallButton(
            onClick = onInstallClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.l)
        )
        if (content.similarApps.isNotEmpty()) {
            Spacer(Modifier.height(Spacing.m))
            SimilarApps(
                apps = content.similarApps,
                onDownloadClick = onSimilarAppDownloadClick,
                modifier = Modifier.padding(horizontal = Spacing.l),
            )
        }
        Spacer(Modifier.height(Spacing.m))
        ScreenshotsList(
            screenshotUrlList = appDetails.screenshotUrlList,
            contentPadding = PaddingValues(horizontal = Spacing.l),
        )
        Spacer(Modifier.height(Spacing.m))
        AppDescription(
            description = appDetails.description,
            collapsed = descriptionCollapsed,
            onReadMoreClick = onReadMoreClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.l),
        )
        Spacer(Modifier.height(Spacing.m))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = Spacing.l),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        Spacer(Modifier.height(Spacing.m))
        Developer(
            name = appDetails.developer,
            onClick = onDeveloperClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Spacing.l),
        )
        Spacer(Modifier.height(Spacing.m))
        Showcase(
            apps = content.showcaseApps,
            onAppClick = onShowcaseAppClick,
            contentPadding = PaddingValues(horizontal = Spacing.l),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NotRuStoreTheme {
        AppDetailsContent(
            content = AppDetailsState.Content(
                appDetails = previewApp,
                similarApps = previewApps,
                showcaseApps = previewApps,
                descriptionCollapsed = false,
            ),
            onReadMoreClick = {},
            onBackClick = {},
            onShareClick = {},
            onInstallClick = {},
            onDeveloperClick = {},
            onSimilarAppDownloadClick = {},
            onShowcaseAppClick = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
