package org.example.app

import java.util.UUID

/**
 * PUBLIC_INTERFACE
 * Data model for Recipe.
 */
data class Recipe(
    val id: String = "",
    val title: String,
    val description: String,
    val ingredients: String,
    val steps: String,
    val imageUrl: String? = null
)

/**
 * PUBLIC_INTERFACE
 * Simple in-memory repository for Recipes supporting CRUD operations.
 */
class RecipeRepository private constructor(
    private val items: MutableList<Recipe>
) {

    fun getAll(): List<Recipe> = items.toList()

    fun getById(id: String): Recipe? = items.find { it.id == id }

    fun create(recipe: Recipe): Recipe {
        val newRecipe = if (recipe.id.isBlank()) recipe.copy(id = newId()) else recipe
        items.add(newRecipe)
        return newRecipe
    }

    fun update(recipe: Recipe): Boolean {
        val idx = items.indexOfFirst { it.id == recipe.id }
        return if (idx >= 0) {
            items[idx] = recipe
            true
        } else {
            false
        }
    }

    fun delete(id: String): Boolean = items.removeIf { it.id == id }

    companion object {
        fun newId(): String = UUID.randomUUID().toString()

        fun withSeed(): RecipeRepository {
            val repo = RecipeRepository(mutableListOf())
            repo.create(
                Recipe(
                    id = newId(),
                    title = "Classic Pancakes",
                    description = "Fluffy pancakes perfect for breakfast.",
                    ingredients = "- 1½ cups flour\n- 3½ tsp baking powder\n- 1 tbsp sugar\n- 1¼ cups milk\n- 1 egg\n- 3 tbsp butter (melted)\n- pinch of salt",
                    steps = "1) Mix dry ingredients.\n2) Whisk in milk, egg, butter.\n3) Cook on a hot griddle until golden.",
                    imageUrl = ""
                )
            )
            repo.create(
                Recipe(
                    id = newId(),
                    title = "Lemon Herb Chicken",
                    description = "Zesty and aromatic roasted chicken.",
                    ingredients = "- 2 chicken breasts\n- 2 lemons\n- 2 cloves garlic\n- Fresh herbs\n- Olive oil\n- Salt & pepper",
                    steps = "1) Marinate chicken in lemon, herbs, garlic, oil.\n2) Roast at 200°C for 20-25 min.",
                    imageUrl = ""
                )
            )
            return repo
        }
    }
}
