package notrustore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import notrustore.presentation.screens.appdetails.AppDetailsScreen
import notrustore.presentation.screens.applist.AppListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.APP_LIST,
    ) {
        composable(Routes.APP_LIST) {
            AppListScreen(
                onAppClick = { app -> navController.navigate("${Routes.APP_DETAILS}/${app.id}") },
            )
        }
        composable(
            route = "${Routes.APP_DETAILS}/{${Routes.APP_ID_ARG}}",
            arguments = listOf(navArgument(Routes.APP_ID_ARG) { type = NavType.StringType }),
        ) {
            AppDetailsScreen(
                onBackClick = { navController.popBackStack() },
                onShowcaseAppClick = {
                    navController.popBackStack(Routes.APP_LIST, inclusive = false)
                },
            )
        }
    }
}
