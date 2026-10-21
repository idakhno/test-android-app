package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import notrustore.R
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Spacing

@Composable
fun InstallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(vertical = Spacing.m),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.install),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
private fun InstallButtonPreview() {
    NotRuStoreTheme {
        InstallButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
