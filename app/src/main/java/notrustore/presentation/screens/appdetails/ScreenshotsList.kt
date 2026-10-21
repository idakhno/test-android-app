package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import notrustore.R
import notrustore.data.sampleApps
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Spacing

private const val SCREENSHOT_ASPECT_RATIO = 16f / 9f

@Composable
fun ScreenshotsList(
    screenshotUrlList: List<String>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.app_details_screenshots),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(contentPadding),
        )
        Spacer(Modifier.height(Spacing.s))
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = contentPadding,
            pageSpacing = Spacing.s,
            state = rememberPagerState { screenshotUrlList.size },
        ) { index ->
            AsyncImage(
                model = screenshotUrlList[index],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(SCREENSHOT_ASPECT_RATIO)
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NotRuStoreTheme {
        ScreenshotsList(
            screenshotUrlList = sampleApps.first().screenshotUrlList,
            contentPadding = PaddingValues(horizontal = Spacing.l),
        )
    }
}
