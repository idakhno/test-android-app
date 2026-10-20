package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import notrustore.R
import notrustore.data.model.AppInfo
import notrustore.data.sampleApps
import notrustore.presentation.screens.applist.AppItem
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Spacing

internal const val SHOWCASE_PAGE_COUNT = 3
internal const val SHOWCASE_APPS_PER_PAGE = 3

internal val showcaseApps = sampleApps.take(SHOWCASE_PAGE_COUNT * SHOWCASE_APPS_PER_PAGE)

@Composable
fun Showcase(
    apps: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val pages = apps.chunked(SHOWCASE_APPS_PER_PAGE)

    Column(modifier) {
        Text(
            text = stringResource(R.string.app_details_showcase),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(contentPadding),
        )
        Spacer(Modifier.height(Spacing.s))
        HorizontalPager(
            state = rememberPagerState { pages.size },
            contentPadding = contentPadding,
            pageSpacing = Spacing.l,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            Column {
                pages[page].forEach { app ->
                    AppItem(
                        app = app,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAppClick(app) }
                            .padding(vertical = Spacing.s),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NotRuStoreTheme {
        Showcase(
            apps = showcaseApps,
            onAppClick = {},
            contentPadding = PaddingValues(horizontal = Spacing.l),
        )
    }
}
