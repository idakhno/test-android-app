package myapplication.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import myapplication.R
import myapplication.data.AppInfo
import myapplication.data.Category
import myapplication.data.sampleApps
import myapplication.ui.theme.MyApplicationTheme

@Composable
fun AppItem(
    app: AppInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = app.iconUrl,
            contentDescription = app.name,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp)),
        )
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = app.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = app.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(app.category.titleRes),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@get:StringRes
private val Category.titleRes: Int
    get() = when (this) {
        Category.APP -> R.string.category_app
        Category.PRODUCTIVITY -> R.string.category_productivity
        Category.EDUCATION -> R.string.category_education
        Category.MUSIC -> R.string.category_music
        Category.PHOTOGRAPHY -> R.string.category_photography
        Category.HEALTH -> R.string.category_health
        Category.SPORTS -> R.string.category_sports
        Category.NEWS -> R.string.category_news
        Category.BUSINESS -> R.string.category_business
        Category.FINANCE -> R.string.category_finance
        Category.FOOD -> R.string.category_food
        Category.SHOPPING -> R.string.category_shopping
        Category.UTILITIES -> R.string.category_utilities
    }

@Preview(showBackground = true)
@Composable
private fun AppItemPreview() {
    MyApplicationTheme {
        AppItem(app = sampleApps.first())
    }
}
