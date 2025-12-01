package org.example.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * PUBLIC_INTERFACE
 * Detail Screen - shows selected recipe and provides edit/delete.
 */
@Composable
fun RecipeDetailScreen(
    repository: RecipeRepository,
    recipeId: String,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val recipe = repository.getById(recipeId)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.title ?: "Recipe") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit, enabled = recipe != null) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    IconButton(
                        onClick = onDelete,
                        enabled = recipe != null,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        if (recipe == null) {
            Box(Modifier.fillMaxSize().padding(padding)) {
                Text("Recipe not found", modifier = Modifier.padding(16.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(recipe.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
                AssistChip(
                    onClick = {},
                    label = { Text("Ocean Professional") },
                    colors = AssistChipDefaults.assistChipColors(
                        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                Text("Description", style = MaterialTheme.typography.titleSmall)
                Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
                Text("Ingredients", style = MaterialTheme.typography.titleSmall)
                Text(recipe.ingredients, style = MaterialTheme.typography.bodyMedium)
                Text("Steps", style = MaterialTheme.typography.titleSmall)
                Text(recipe.steps, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
