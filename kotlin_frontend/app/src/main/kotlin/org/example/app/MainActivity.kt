package org.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

/**
 * PUBLIC_INTERFACE
 */
class MainActivity : ComponentActivity() {
    /**
     * Entrypoint Activity for the Recipe Manager Compose app.
     * Sets up the app's theme and navigation host.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OceanProfessionalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeApp()
                }
            }
        }
    }
}

/**
 * PUBLIC_INTERFACE
 * Root composable hosting navigation and a shared in-memory repository.
 */
@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    val repositoryState: MutableState<RecipeRepository> = remember {
        mutableStateOf(RecipeRepository.withSeed())
    }

    NavHost(
        navController = navController,
        startDestination = "list",
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) }
    ) {
        composable("list") {
            RecipeListScreen(
                repository = repositoryState.value,
                onAddClicked = { navController.navigate("edit") },
                onRecipeClicked = { id -> navController.navigate("detail/$id") }
            )
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("id") ?: ""
            RecipeDetailScreen(
                repository = repositoryState.value,
                recipeId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("edit/$id") },
                onDelete = {
                    repositoryState.value.delete(id)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "edit",
        ) {
            AddEditRecipeScreen(
                repository = repositoryState.value,
                recipe = null,
                onSave = { saved ->
                    if (saved.id.isBlank()) {
                        repositoryState.value.create(saved.copy(id = RecipeRepository.newId()))
                    } else {
                        repositoryState.value.update(saved)
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("id")
            val existing = repositoryState.value.getById(id.orEmpty())
            AddEditRecipeScreen(
                repository = repositoryState.value,
                recipe = existing,
                onSave = { saved ->
                    if (saved.id.isBlank()) {
                        repositoryState.value.create(saved.copy(id = RecipeRepository.newId()))
                    } else {
                        repositoryState.value.update(saved)
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
