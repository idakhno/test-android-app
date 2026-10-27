package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import notrustore.R
import notrustore.presentation.preview.previewApp
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Spacing

@Composable
fun AppDescription(
    description: String,
    collapsed: Boolean,
    onReadMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.app_details_description),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(Spacing.s))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (collapsed) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (!collapsed) {
            TextButton(
                onClick = onReadMoreClick,
                contentPadding = PaddingValues(horizontal = Spacing.none)
            ) {
                Text(
                    text = stringResource(R.string.app_details_read_more),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewShort() {
    NotRuStoreTheme {
        AppDescription(
            description = previewApp.description,
            onReadMoreClick = {},
            collapsed = false,
        )
    }
}

@Preview
@Composable
private fun PreviewCollapsed() {
    NotRuStoreTheme {
        AppDescription(
            description = previewApp.description,
            onReadMoreClick = {},
            collapsed = true,
        )
    }
}
