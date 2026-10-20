package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import notrustore.R
import notrustore.data.model.AppInfo
import notrustore.data.sampleApps
import notrustore.presentation.screens.applist.titleRes
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Size
import notrustore.presentation.theme.Spacing
import kotlin.math.roundToInt

@Composable
fun AppDetailsHeader(
    appDetails: AppInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = appDetails.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Size.appIconLarge)
                .clip(MaterialTheme.shapes.large),
        )
        Spacer(Modifier.width(Spacing.l))
        Column {
            Text(
                text = stringResource(appDetails.category.titleRes),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = appDetails.name,
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(Modifier.height(Spacing.xs))
            Text(
                text = appDetails.developer,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(Modifier.height(Spacing.xs))
            Row {
                Column(Modifier.width(IntrinsicSize.Max)) {
                    Text(
                        text = stringResource(R.string.app_details_age_value, appDetails.ageRating),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(Spacing.xs))
                    Text(
                        text = stringResource(R.string.app_details_age),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Spacer(Modifier.width(Spacing.m))
                Column {
                    Text(
                        text = stringResource(
                            R.string.app_details_size_value,
                            appDetails.size.roundToInt(),
                        ),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Spacer(Modifier.height(Spacing.xs))
                    Text(
                        text = stringResource(R.string.app_details_size),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NotRuStoreTheme {
        AppDetailsHeader(appDetails = sampleApps.first(), modifier = Modifier.fillMaxWidth())
    }
}
