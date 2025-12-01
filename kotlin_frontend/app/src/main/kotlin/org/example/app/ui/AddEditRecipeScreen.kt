package org.example.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * PUBLIC_INTERFACE
 * Add/Edit Screen - form to create or update a recipe.
 */
@Composable
fun AddEditRecipeScreen(
    repository: RecipeRepository,
    recipe: Recipe?,
    onSave: (Recipe) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(recipe?.title ?: "")) }
    var description by remember { mutableStateOf(TextFieldValue(recipe?.description ?: "")) }
    var ingredients by remember { mutableStateOf(TextFieldValue(recipe?.ingredients ?: "")) }
    var steps by remember { mutableStateOf(TextFieldValue(recipe?.steps ?: "")) }
    var imageUrl by remember { mutableStateOf(TextFieldValue(recipe?.imageUrl ?: "")) }

    val isValid = title.text.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (recipe == null) "Add Recipe" else "Edit Recipe") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isValid) {
                                val newRecipe = Recipe(
                                    id = recipe?.id ?: "",
                                    title = title.text.trim(),
                                    description = description.text.trim(),
                                    ingredients = ingredients.text.trim(),
                                    steps = steps.text.trim(),
                                    imageUrl = imageUrl.text.trim().ifBlank { null }
                                )
                                onSave(newRecipe)
                            }
                        },
                        enabled = isValid,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = if (isValid) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                isError = title.text.isBlank(),
                supportingText = {
                    if (title.text.isBlank()) Text("Title is required", color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Short Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = steps,
                onValueChange = { steps = it },
                label = { Text("Steps") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL (optional)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
