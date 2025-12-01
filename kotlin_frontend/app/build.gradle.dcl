androidApplication {
    namespace = "org.example.app"

    // Enable Jetpack Compose using declarative 'compose' block
    compose {
        enabled = true
    }

    dependencies {
        // Compose BOM for version alignment
        implementation(platform("androidx.compose:compose-bom:2024.10.01"))

        // Core Compose UI
        implementation("androidx.activity:activity-compose:1.9.3")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling-preview")

        // Tooling (no debugImplementation scope in declarative DSL)
        implementation("androidx.compose.ui:ui-tooling")

        // Material3 components
        implementation("androidx.compose.material3:material3:1.3.1")

        // Navigation for Compose
        implementation("androidx.navigation:navigation-compose:2.8.3")

        // Optional icons
        implementation("androidx.compose.material:material-icons-extended")

        // Kotlin coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

        // Keep existing sample dependency (not used anymore)
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation(project(":utilities"))
    }
}
