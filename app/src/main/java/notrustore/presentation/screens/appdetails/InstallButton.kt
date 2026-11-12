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
    installState: InstallState = InstallState.NotInstalled,
) {
    Button(
        onClick = onClick,
        enabled = installState != InstallState.Installed,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(vertical = Spacing.m),
        modifier = modifier,
    ) {
        Text(
            text = when (installState) {
                InstallState.NotInstalled -> stringResource(R.string.install)
                is InstallState.Installing ->
                    stringResource(R.string.cancel_install, installState.progress)
                InstallState.Installed -> stringResource(R.string.installed)
            },
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
