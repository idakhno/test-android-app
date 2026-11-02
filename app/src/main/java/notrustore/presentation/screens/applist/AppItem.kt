package notrustore.presentation.screens.applist

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import notrustore.R
import notrustore.domain.model.AppInfo
import notrustore.domain.model.Category
import notrustore.presentation.preview.previewApp
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Size
import notrustore.presentation.theme.Spacing

@Composable
fun AppItem(
    app: AppInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.l),
    ) {
        AsyncImage(
            model = app.iconUrl,
            contentDescription = app.name,
            modifier = Modifier
                .size(Size.appIcon)
                .clip(MaterialTheme.shapes.medium),
        )
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.xxs)) {
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
internal val Category.titleRes: Int
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
    NotRuStoreTheme {
        AppItem(
            app = previewApp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.l, vertical = Spacing.m),
        )
    }
}
