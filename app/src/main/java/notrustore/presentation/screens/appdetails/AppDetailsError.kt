package notrustore.presentation.screens.appdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import notrustore.R
import notrustore.presentation.theme.NotRuStoreTheme
import notrustore.presentation.theme.Spacing

@Composable
fun AppDetailsError(
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = Spacing.l),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_details_error),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(Spacing.m))
        Button(
            onClick = onRefreshClick,
        ) {
            Text(
                text = stringResource(R.string.app_details_error_refresh),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NotRuStoreTheme {
        AppDetailsError(
            onRefreshClick = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
