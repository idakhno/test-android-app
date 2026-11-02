package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import notrustore.R
import notrustore.domain.model.AppInfo
import notrustore.presentation.preview.previewApps
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Size
import notrustore.presentation.theme.Spacing

@Composable
fun SimilarApps(
    apps: List<AppInfo>,
    onDownloadClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.app_details_similar_apps),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.height(Spacing.xs))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s)) {
            items(apps) { app ->
                SimilarAppItem(app = app, onDownloadClick = { onDownloadClick(app) })
            }
        }
    }
}

@Composable
private fun SimilarAppItem(
    app: AppInfo,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(Size.appIconLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = app.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Size.appIconLarge)
                .clip(MaterialTheme.shapes.large),
        )
        Text(
            text = app.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Button(
            onClick = onDownloadClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.app_details_download),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NotRuStoreTheme {
        SimilarApps(
            apps = previewApps,
            onDownloadClick = {},
        )
    }
}
