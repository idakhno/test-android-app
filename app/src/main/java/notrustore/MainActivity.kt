package notrustore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import notrustore.presentation.navigation.AppNavHost
import notrustore.presentation.theme.NotRuStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotRuStoreTheme {
                AppNavHost()
            }
        }
    }
}
