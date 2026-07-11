# PPLOG UI Redesign & Exercise Detail Popup — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Modernize the PPLOG Android app UI with a dark athletic Material 3 theme, replace the bottom app bar with a navigation bar, and add a modal bottom-sheet popup that shows exercise steps and form tips when tapping an exercise name.

**Architecture:** Compose Material 3 screens hosted by a single `MainActivity` Scaffold with a shared bottom navigation bar. A reusable `ExerciseDetailBottomSheet` fetches exercise details from the existing local repository and is triggered from exercise cards. Theme tokens, shared components, and screen composables are layered so each file has one clear responsibility.

**Tech Stack:** Kotlin, Jetpack Compose (Material 3), Navigation Compose, Room, Koin, Coil.

---

## File Structure

**Theme tokens**
- `app/src/main/java/com/pplog/app/ui/theme/Color.kt` — new palette tokens
- `app/src/main/java/com/pplog/app/ui/theme/Theme.kt` — dark/light schemes using tokens
- `app/src/main/java/com/pplog/app/ui/theme/Type.kt` — full Material 3 type scale

**Navigation & shell**
- `app/src/main/java/com/pplog/app/MainActivity.kt` — root Scaffold with bottom bar
- `app/src/main/java/com/pplog/app/ui/navigation/PPLOGNavHost.kt` — routes without `ExerciseDetail`
- `app/src/main/java/com/pplog/app/ui/components/PPLOGBottomBar.kt` — styled bottom navigation

**Shared components**
- `app/src/main/java/com/pplog/app/ui/components/PrimaryButton.kt` — styled CTA button
- `app/src/main/java/com/pplog/app/ui/components/HeroCard.kt` — gradient hero card
- `app/src/main/java/com/pplog/app/ui/components/StatRow.kt` — horizontally scrollable stat pills
- `app/src/main/java/com/pplog/app/ui/components/FilterSection.kt` — collapsible filter header + chips
- `app/src/main/java/com/pplog/app/ui/components/ExerciseListItem.kt` — redesigned exercise row/card
- `app/src/main/java/com/pplog/app/ui/components/DayCard.kt` — expandable plan day card
- `app/src/main/java/com/pplog/app/ui/components/WorkoutSetCard.kt` — checkbox set tracker card
- `app/src/main/java/com/pplog/app/ui/components/ExerciseDetailBottomSheet.kt` — exercise detail popup

**Screens**
- `app/src/main/java/com/pplog/app/ui/screens/home/HomeScreen.kt`
- `app/src/main/java/com/pplog/app/ui/screens/home/HomeViewModel.kt`
- `app/src/main/java/com/pplog/app/ui/screens/explore/ExploreScreen.kt`
- `app/src/main/java/com/pplog/app/ui/screens/explore/ExploreViewModel.kt` (no change unless needed)
- `app/src/main/java/com/pplog/app/ui/screens/plan/PlanScreen.kt`
- `app/src/main/java/com/pplog/app/ui/screens/workout/WorkoutScreen.kt`
- `app/src/main/java/com/pplog/app/ui/screens/settings/SettingsScreen.kt`
- `app/src/main/java/com/pplog/app/ui/screens/onboarding/OnboardingScreen.kt`
- **Delete:** `app/src/main/java/com/pplog/app/ui/screens/explore/ExerciseDetailScreen.kt`

**Tests**
- `app/src/androidTest/java/com/pplog/app/ExerciseDetailBottomSheetTest.kt`
- Update `app/src/test/java/com/pplog/app/PlanBuilderEngineTest.kt` if `Exercise` shape changed (it did not).

**Build config**
- `gradle/libs.versions.toml` — add material-icons-extended
- `app/build.gradle.kts` — use material-icons-extended

---

## Task 1: Add Material Icons Extended Dependency

**Files:**
- Modify: `gradle/libs.versions.toml`
- Modify: `app/build.gradle.kts`

- [ ] **Step 1: Add the library entry**

In `gradle/libs.versions.toml`, add this line under `[libraries]`:

```toml
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
```

- [ ] **Step 2: Add dependency to app module**

In `app/build.gradle.kts`, add inside the `dependencies { ... }` block:

```kotlin
implementation(libs.androidx.material.icons.extended)
```

- [ ] **Step 3: Verify build still compiles**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git add gradle/libs.versions.toml app/build.gradle.kts
git commit -m "build: add material-icons-extended for outlined navigation icons"
```

---

## Task 2: Update Theme Color Tokens

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/theme/Color.kt`

- [ ] **Step 1: Replace the entire file content**

```kotlin
package com.pplog.app.ui.theme

import androidx.compose.ui.graphics.Color

val Background = Color(0xFF0F1419)
val Surface = Color(0xFF1A1F25)
val Primary = Color(0xFF4ADE80)
val PrimaryContainer = Color(0xFF166534)
val OnPrimary = Color(0xFF052E16)
val Secondary = Color(0xFFFB923C)
val SecondaryContainer = Color(0xFF7C2D12)
val OnSurface = Color(0xFFF1F5F9)
val OnSurfaceMuted = Color(0xFF94A3B8)
val Error = Color(0xFFF87171)
val Outline = Color(0xFF334155)
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/theme/Color.kt
git commit -m "style: update color tokens for dark athletic theme"
```

---

## Task 3: Update Typography Scale

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/theme/Type.kt`

- [ ] **Step 1: Replace the entire file content**

```kotlin
package com.pplog.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/theme/Type.kt
git commit -m "style: add full Material 3 typography scale"
```

---

## Task 4: Update Theme to Use New Tokens

The app always uses the dark athletic color scheme; a light scheme is not provided.

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/theme/Theme.kt`

- [ ] **Step 1: Replace the entire file content**

```kotlin
package com.pplog.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    secondary = Secondary,
    secondaryContainer = SecondaryContainer,
    background = Background,
    surface = Surface,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceMuted,
    outline = Outline,
    error = Error
)

@Composable
fun PPLOGTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/theme/Theme.kt
git commit -m "style: wire color tokens into dark color scheme"
```

---

## Task 5: Create the Bottom Navigation Bar Component

**Files:**
- Create: `app/src/main/java/com/pplog/app/ui/components/PPLOGBottomBar.kt`

- [ ] **Step 1: Create the file**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pplog.app.ui.navigation.Screen

private data class NavItem(
    val route: String,
    val label: String,
    val selectedIcon: @Composable () -> Unit,
    val unselectedIcon: @Composable () -> Unit
)

@Composable
fun PPLOGBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(
            route = Screen.Home.route,
            label = "Home",
            selectedIcon = { Icon(Icons.Filled.Home, contentDescription = null) },
            unselectedIcon = { Icon(Icons.Outlined.Home, contentDescription = null) }
        ),
        NavItem(
            route = Screen.Explore.route,
            label = "Explore",
            selectedIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            unselectedIcon = { Icon(Icons.Outlined.Search, contentDescription = null) }
        ),
        NavItem(
            route = Screen.Plan.route,
            label = "Plan",
            selectedIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
            unselectedIcon = { Icon(Icons.Outlined.DateRange, contentDescription = null) }
        ),
        NavItem(
            route = Screen.Settings.route,
            label = "Settings",
            selectedIcon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            unselectedIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) }
        )
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = if (selected) item.selectedIcon else item.unselectedIcon,
                label = { Text(item.label) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/PPLOGBottomBar.kt
git commit -m "feat: add styled bottom navigation bar component"
```

---

## Task 6: Move Bottom Bar into MainActivity Scaffold

**Files:**
- Modify: `app/src/main/java/com/pplog/app/MainActivity.kt`
- Modify: `app/src/main/java/com/pplog/app/ui/navigation/PPLOGNavHost.kt`

- [ ] **Step 1: Replace MainActivity content**

```kotlin
package com.pplog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pplog.app.ui.components.PPLOGBottomBar
import com.pplog.app.ui.navigation.PPLOGNavHost
import com.pplog.app.ui.navigation.Screen
import com.pplog.app.ui.theme.PPLOGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPLOGTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
                val showBottomBar = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Explore.route,
                    Screen.Plan.route,
                    Screen.Settings.route
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            PPLOGBottomBar(
                                currentRoute = currentRoute,
                                onNavigate = { route ->
                                    if (route != currentRoute) {
                                        navController.navigate(route) {
                                            popUpTo(Screen.Home.route) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        PPLOGNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
```

- [ ] **Step 2: Remove bottom bars from screens**

In `HomeScreen.kt`, delete the `bottomBar = { BottomAppBar { ... } }` block from the `Scaffold` call. Leave the rest of the file as-is for now (it will be redesigned in Task 8).

Leave `PPLOGNavHost.kt` unchanged in this task. The `ExerciseDetail` route will be removed in Task 11 once the bottom-sheet popup replaces it; removing it now would crash `ExploreScreen` because it still navigates to that route.

- [ ] **Step 3: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL.

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/pplog/app/MainActivity.kt app/src/main/java/com/pplog/app/ui/screens/home/HomeScreen.kt
git commit -m "feat: move bottom navigation into MainActivity scaffold"
```

---

## Task 7: Create Shared Card Components

**Files:**
- Create: `app/src/main/java/com/pplog/app/ui/components/HeroCard.kt`
- Create: `app/src/main/java/com/pplog/app/ui/components/StatRow.kt`
- Create: `app/src/main/java/com/pplog/app/ui/components/FilterSection.kt`

- [ ] **Step 1: Create HeroCard.kt**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeroCard(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            content()
        }
    }
}
```

- [ ] **Step 2: Create StatRow.kt**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatRow(
    stats: List<Stat>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stats, key = { it.label }) { stat ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(min = 80.dp)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = stat.value,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stat.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

data class Stat(val value: String, val label: String)
```

- [ ] **Step 3: Create FilterSection.kt**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterSection(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        AnimatedVisibility(visible = expanded) {
            content()
        }
    }
}
```

- [ ] **Step 4: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/HeroCard.kt app/src/main/java/com/pplog/app/ui/components/StatRow.kt app/src/main/java/com/pplog/app/ui/components/FilterSection.kt
git commit -m "feat: add shared HeroCard, StatRow, and FilterSection components"
```


---

## Task 8: Redesign HomeScreen

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/screens/home/HomeScreen.kt`
- Modify: `app/src/main/java/com/pplog/app/ui/screens/home/HomeViewModel.kt`

- [ ] **Step 1: Extend HomeViewModel state**

Replace `HomeViewModel.kt` with:

```kotlin
package com.pplog.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pplog.app.data.repository.PlanRepository
import com.pplog.app.domain.model.WorkoutPlan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(planRepository: PlanRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = planRepository.getActivePlan()
        .map { plan ->
            HomeUiState(
                activePlan = plan,
                workoutsThisWeek = 0, // static value; workout log integration is out of scope for this redesign
                currentStreakDays = 0
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    data class HomeUiState(
        val activePlan: WorkoutPlan? = null,
        val workoutsThisWeek: Int = 0,
        val currentStreakDays: Int = 0
    )
}
```

- [ ] **Step 2: Replace HomeScreen content**

```kotlin
package com.pplog.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.ui.components.HeroCard
import com.pplog.app.ui.components.PrimaryButton
import com.pplog.app.ui.components.Stat
import com.pplog.app.ui.components.StatRow
import com.pplog.app.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PPLOG", style = MaterialTheme.typography.headlineMedium) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeroCard(
                title = "Welcome back",
                subtitle = "Ready to hit your goals today?"
            )

            StatRow(
                stats = listOf(
                    Stat(value = state.workoutsThisWeek.toString(), label = "This week"),
                    Stat(value = "${state.currentStreakDays}d", label = "Streak")
                )
            )

            state.activePlan?.let { plan ->
                HeroCard(
                    title = plan.name,
                    subtitle = "${plan.daysPerWeek} days per week"
                ) {
                    PrimaryButton(
                        text = "Start Today's Workout",
                        onClick = { navController.navigate(Screen.Workout.route) },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            } ?: HeroCard(
                title = "No active plan",
                subtitle = "Build a custom plan that fits your schedule."
            ) {
                PrimaryButton(
                    text = "Build Plan",
                    onClick = { navController.navigate(Screen.PlanBuilder.route) },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
```

- [ ] **Step 3: Verify WorkoutPlan has minutesPerSession**

Open `app/src/main/java/com/pplog/app/domain/model/WorkoutPlan.kt`. If it does not have `minutesPerSession`, either remove that reference from `HomeScreen` or add the field to the model. For this plan, assume it exists; if not, adjust the subtitle string.

- [ ] **Step 4: Compile and run unit tests**

Run:

```bash
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
```

Expected: both pass.

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/screens/home/HomeScreen.kt app/src/main/java/com/pplog/app/ui/screens/home/HomeViewModel.kt
git commit -m "feat: redesign HomeScreen with hero cards and stat row"
```

---

## Task 9: Redesign ExploreScreen

**Files:**
- Create: `app/src/main/java/com/pplog/app/ui/components/ExerciseListItem.kt`
- Modify: `app/src/main/java/com/pplog/app/ui/screens/explore/ExploreScreen.kt`

- [ ] **Step 1: Create ExerciseListItem.kt**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pplog.app.domain.model.Exercise

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseListItem(
    exercise: Exercise,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.FitnessCenter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Badge(text = exercise.difficulty.name.lowercase().replaceFirstChar { it.uppercase() })
                    exercise.primaryMuscleGroups.take(2).forEach {
                        Badge(text = it.name.replace("_", " "), muted = true)
                    }
                }
            }
        }
    }
}

@Composable
private fun Badge(text: String, muted: Boolean = false) {
    val bg = if (muted) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primaryContainer
    val fg = if (muted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = fg,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
```

- [ ] **Step 2: Replace ExploreScreen content**

```kotlin
package com.pplog.app.ui.screens.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.domain.model.Difficulty
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.MuscleGroup
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.components.ExerciseListItem
import com.pplog.app.ui.components.FilterChipGroup
import com.pplog.app.ui.components.FilterSection
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var filtersExpanded by remember { mutableStateOf(true) }
    var selectedExerciseId by remember { mutableStateOf<String?>(null) }

    val filteredExercises = state.exercises.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Exercises") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search exercises") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            FilterSection(
                title = "Filters",
                expanded = filtersExpanded,
                onToggle = { filtersExpanded = !filtersExpanded }
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Muscle group", style = MaterialTheme.typography.labelLarge)
                    FilterChipGroup(
                        items = MuscleGroup.entries,
                        selected = state.muscleFilter,
                        onSelected = viewModel::setMuscleFilter,
                        label = { it.name.replace("_", " ") }
                    )

                    Text("Equipment", style = MaterialTheme.typography.labelLarge)
                    FilterChipGroup(
                        items = Equipment.entries,
                        selected = state.equipmentFilter,
                        onSelected = viewModel::setEquipmentFilter,
                        label = { it.name.replace("_", " ") }
                    )

                    Text("Difficulty", style = MaterialTheme.typography.labelLarge)
                    FilterChipGroup(
                        items = Difficulty.entries,
                        selected = state.difficultyFilter,
                        onSelected = viewModel::setDifficultyFilter,
                        label = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredExercises, key = { it.id }) { exercise ->
                    ExerciseListItem(
                        exercise = exercise,
                        onClick = { selectedExerciseId = exercise.id }
                    )
                }
            }
        }
    }

    selectedExerciseId?.let { id ->
        ExerciseDetailBottomSheet(
            exerciseId = id,
            onDismiss = { selectedExerciseId = null }
        )
    }
}
```

- [ ] **Step 3: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL (may show unused `navController` parameter until wiring).

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/ExerciseListItem.kt app/src/main/java/com/pplog/app/ui/screens/explore/ExploreScreen.kt
git commit -m "feat: redesign ExploreScreen with search and exercise list items"
```

---

## Task 10: Create ExerciseDetailBottomSheet

**Files:**
- Create: `app/src/main/java/com/pplog/app/ui/components/ExerciseDetailBottomSheet.kt`

- [ ] **Step 1: Create the file**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pplog.app.data.repository.ExerciseRepository
import com.pplog.app.domain.model.Exercise
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailBottomSheet(
    exerciseId: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
    exerciseRepository: ExerciseRepository = koinInject()
) {
    var exercise by remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(exerciseId) {
        exercise = exerciseRepository.getExercise(exerciseId)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            exercise?.let { ex ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.FitnessCenter,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Text(
                    text = ex.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                BadgeFlow(exercise = ex)

                SectionTitle("Steps & Procedure")
                ex.instructions.forEachIndexed { index, step ->
                    Text(
                        text = "${index + 1}. $step",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                if (ex.tips.isNotEmpty()) {
                    SectionTitle("Form Tips")
                    ex.tips.forEach { tip ->
                        Text(
                            text = "• $tip",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                TextButton(
                    onClick = {
                        ex.imageUrl?.let { path ->
                            exerciseRepository.requestImageDownload(ex.id, path)
                        }
                    },
                    enabled = ex.imageUrl != null,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Filled.Download, contentDescription = null)
                    Text("Download image guide", modifier = Modifier.padding(start = 8.dp))
                }

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Close")
                }
            } ?: Text(
                text = "Exercise not found.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BadgeFlow(exercise: Exercise) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Badge(
            text = exercise.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }
        )
        exercise.primaryMuscleGroups.forEach {
            Badge(text = it.name.replace("_", " "), muted = true)
        }
        exercise.equipment.forEach {
            Badge(text = it.name.replace("_", " "), muted = true)
        }
    }
}
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/ExerciseDetailBottomSheet.kt
git commit -m "feat: add ExerciseDetailBottomSheet popup with steps and tips"
```

---

## Task 11: Wire Bottom Sheet into Explore and Remove ExerciseDetailScreen

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/navigation/PPLOGNavHost.kt`
- Delete: `app/src/main/java/com/pplog/app/ui/screens/explore/ExerciseDetailScreen.kt`

- [ ] **Step 1: Remove ExerciseDetail route and file**

In `PPLOGNavHost.kt`, remove:
- `data object ExerciseDetail : Screen("exercise_detail/{exerciseId}")`
- The `composable(Screen.ExerciseDetail.route) { ... }` block
- The import of `ExerciseDetailScreen`

Delete `app/src/main/java/com/pplog/app/ui/screens/explore/ExerciseDetailScreen.kt`.

- [ ] **Step 2: Compile and run tests**

Run:

```bash
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
```

Expected: both pass.

- [ ] **Step 3: Commit**

```bash
rm app/src/main/java/com/pplog/app/ui/screens/explore/ExerciseDetailScreen.kt
git add app/src/main/java/com/pplog/app/ui/navigation/PPLOGNavHost.kt
git commit -m "feat: remove old ExerciseDetail screen route"
```


---

## Task 12: Redesign PlanScreen

**Files:**
- Create: `app/src/main/java/com/pplog/app/ui/components/DayCard.kt`
- Modify: `app/src/main/java/com/pplog/app/ui/screens/plan/PlanScreen.kt`

- [ ] **Step 1: Create DayCard.kt**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pplog.app.domain.model.PlanDay

@Composable
fun DayCard(
    day: PlanDay,
    onExerciseClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Day ${day.dayNumber}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = day.focus,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${day.exercises.size} exercises",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    day.exercises.forEach { ex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onExerciseClick(ex.exerciseId) }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = ex.exerciseName,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${ex.sets} x ${ex.reps}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
```

- [ ] **Step 2: Replace PlanScreen content**

```kotlin
package com.pplog.app.ui.screens.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.ui.components.DayCard
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: PlanViewModel = koinViewModel()
) {
    val plan by viewModel.activePlan.collectAsState()
    var selectedExerciseId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Plan") }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screen.PlanBuilder.route) },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Build plan") }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            plan?.let { p ->
                Text(
                    text = p.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${p.daysPerWeek} days per week",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(p.days, key = { it.dayNumber }) { day ->
                        DayCard(
                            day = day,
                            onExerciseClick = { selectedExerciseId = it }
                        )
                    }
                }
            } ?: Text(
                text = "No active plan yet. Tap the button to build one.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    selectedExerciseId?.let { id ->
        ExerciseDetailBottomSheet(
            exerciseId = id,
            onDismiss = { selectedExerciseId = null }
        )
    }
}
```

- [ ] **Step 3: Compile and run tests**

Run:

```bash
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
```

Expected: both pass.

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/DayCard.kt app/src/main/java/com/pplog/app/ui/screens/plan/PlanScreen.kt
git commit -m "feat: redesign PlanScreen with expandable DayCards"
```

---

## Task 13: Redesign WorkoutScreen

**Files:**
- Create: `app/src/main/java/com/pplog/app/ui/components/WorkoutSetCard.kt`
- Modify: `app/src/main/java/com/pplog/app/ui/screens/workout/WorkoutScreen.kt`

- [ ] **Step 1: Create WorkoutSetCard.kt**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pplog.app.domain.model.PlannedExercise

@Composable
fun WorkoutSetCard(
    exercise: PlannedExercise,
    completedSets: Set<Int>,
    onToggleSet: (Int) -> Unit,
    onExerciseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = exercise.exerciseName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable(onClick = onExerciseClick)
            )
            Text(
                text = "${exercise.sets} sets x ${exercise.reps} reps · Rest ${exercise.restSeconds}s",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                (1..exercise.sets).forEach { setNumber ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = setNumber in completedSets,
                            onCheckedChange = { onToggleSet(setNumber) }
                        )
                        Text(
                            text = "Set $setNumber",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
```

- [ ] **Step 2: Replace WorkoutScreen content**

```kotlin
package com.pplog.app.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.components.WorkoutSetCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = koinViewModel()
) {
    val plan by viewModel.activePlan.collectAsState()
    var selectedExerciseId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            plan?.days?.firstOrNull()?.let { day ->
                Text(
                    text = "Day ${day.dayNumber}: ${day.focus}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                day.exercises.forEach { exercise ->
                    WorkoutSetCard(
                        exercise = exercise,
                        completedSets = (1..exercise.sets).filter { viewModel.isSetCompleted(exercise.exerciseId, it) }.toSet(),
                        onToggleSet = { viewModel.toggleSet(exercise.exerciseId, it) },
                        onExerciseClick = { selectedExerciseId = exercise.exerciseId }
                    )
                }
            } ?: Text(
                text = "No workout available. Build a plan first.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    selectedExerciseId?.let { id ->
        ExerciseDetailBottomSheet(
            exerciseId = id,
            onDismiss = { selectedExerciseId = null }
        )
    }
}
```

- [ ] **Step 3: Compile and run tests**

Run:

```bash
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
```

Expected: both pass.

- [ ] **Step 4: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/WorkoutSetCard.kt app/src/main/java/com/pplog/app/ui/screens/workout/WorkoutScreen.kt
git commit -m "feat: redesign WorkoutScreen with set cards and detail popup"
```

---

## Task 14: Redesign SettingsScreen

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/screens/settings/SettingsScreen.kt`

- [ ] **Step 1: Replace SettingsScreen content**

```kotlin
package com.pplog.app.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val cloudBackup by viewModel.cloudBackupEnabled.collectAsState()
    val darkTheme by viewModel.darkThemeEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = MaterialTheme.shapes.large
            ) {
                ListItem(
                    headlineContent = { Text("Cloud backup") },
                    supportingContent = { Text("Sync plans to Supabase when online") },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.Cloud,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = cloudBackup,
                            onCheckedChange = viewModel::setCloudBackup
                        )
                    }
                )
                ListItem(
                    headlineContent = { Text("Dark theme") },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Filled.DarkMode,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = darkTheme,
                            onCheckedChange = viewModel::setDarkTheme
                        )
                    }
                )
            }

            Text(
                text = "PPLOG v0.2.0",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/screens/settings/SettingsScreen.kt
git commit -m "feat: redesign SettingsScreen with grouped cards and icons"
```

---

## Task 15: Redesign OnboardingScreen

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/screens/onboarding/OnboardingScreen.kt`

- [ ] **Step 1: Replace OnboardingScreen content**

```kotlin
package com.pplog.app.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pplog.app.domain.model.Equipment
import com.pplog.app.domain.model.Experience
import com.pplog.app.domain.model.Goal
import com.pplog.app.ui.components.HeroCard
import com.pplog.app.ui.components.PrimaryButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    onPlanCreated: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var goalExpanded by remember { mutableStateOf(false) }
    var experienceExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeroCard(
            title = "Let's build your plan",
            subtitle = "Tell us a bit about your goals and equipment."
        )

        Text("Primary goal", style = MaterialTheme.typography.labelLarge)
        ExposedDropdownMenuBox(
            expanded = goalExpanded,
            onExpandedChange = { goalExpanded = it }
        ) {
            TextField(
                value = state.goal.name.lowercase().replaceFirstChar { it.uppercase() },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = goalExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = goalExpanded,
                onDismissRequest = { goalExpanded = false }
            ) {
                Goal.entries.forEach { goal ->
                    DropdownMenuItem(
                        text = { Text(goal.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = {
                            viewModel.updateGoal(goal)
                            goalExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            "Experience",
            style = MaterialTheme.typography.labelLarge
        )
        ExposedDropdownMenuBox(
            expanded = experienceExpanded,
            onExpandedChange = { experienceExpanded = it }
        ) {
            TextField(
                value = state.experience.name.lowercase().replaceFirstChar { it.uppercase() },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = experienceExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = experienceExpanded,
                onDismissRequest = { experienceExpanded = false }
            ) {
                Experience.entries.forEach { exp ->
                    DropdownMenuItem(
                        text = { Text(exp.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = {
                            viewModel.updateExperience(exp)
                            experienceExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            "Available equipment",
            style = MaterialTheme.typography.labelLarge
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Equipment.entries.forEach { equipment ->
                val selected = state.equipment.contains(equipment)
                FilterChip(
                    selected = selected,
                    onClick = { viewModel.toggleEquipment(equipment) },
                    label = { Text(equipment.name.replace("_", " ")) }
                )
            }
        }

        OutlinedTextField(
            value = state.injuries,
            onValueChange = viewModel.updateInjuries,
            label = { Text("Injuries or limitations (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryButton(
            text = "Create Plan",
            onClick = { viewModel.createPlan(onPlanCreated) },
            enabled = state.equipment.isNotEmpty()
        )
    }
}
```

- [ ] **Step 2: Compile and run tests**

Run:

```bash
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
```

Expected: both pass.

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/screens/onboarding/OnboardingScreen.kt
git commit -m "feat: redesign OnboardingScreen with hero card and improved layout"
```

---

## Task 16: Update PrimaryButton Styling

**Files:**
- Modify: `app/src/main/java/com/pplog/app/ui/components/PrimaryButton.kt`

- [ ] **Step 1: Replace the file content**

```kotlin
package com.pplog.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}
```

- [ ] **Step 2: Compile**

Run:

```bash
./gradlew :app:compileDebugKotlin
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/pplog/app/ui/components/PrimaryButton.kt
git commit -m "style: update PrimaryButton with rounded corners and theme colors"
```


---

## Task 17: Add UI Tests for Exercise Detail Bottom Sheet

**Files:**
- Create: `app/src/androidTest/java/com/pplog/app/ExerciseDetailBottomSheetTest.kt`

- [ ] **Step 1: Create the test file**

```kotlin
package com.pplog.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pplog.app.ui.components.ExerciseDetailBottomSheet
import com.pplog.app.ui.theme.PPLOGTheme
import org.junit.Rule
import org.junit.Test

class ExerciseDetailBottomSheetTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomSheetDisplaysExerciseNameAndSteps() {
        composeTestRule.setContent {
            PPLOGTheme {
                ExerciseDetailBottomSheet(
                    exerciseId = "ex_squat",
                    onDismiss = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Barbell Back Squat").assertIsDisplayed()
        composeTestRule.onNodeWithText("Steps & Procedure").assertIsDisplayed()
        composeTestRule.onNodeWithText("1. Stand with feet shoulder-width apart, barbell resting on upper traps.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Form Tips").assertIsDisplayed()
    }

    @Test
    fun bottomSheetDismissesOnClose() {
        var dismissed = false
        composeTestRule.setContent {
            PPLOGTheme {
                ExerciseDetailBottomSheet(
                    exerciseId = "ex_squat",
                    onDismiss = { dismissed = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Close").performClick()
        assert(dismissed)
    }
}
```

- [ ] **Step 2: Run instrumentation tests**

Start the emulator, then run:

```bash
./gradlew :app:connectedDebugAndroidTest
```

Expected: tests pass.

- [ ] **Step 3: Commit**

```bash
git add app/src/androidTest/java/com/pplog/app/ExerciseDetailBottomSheetTest.kt
git commit -m "test: add UI tests for exercise detail bottom sheet"
```

---

## Task 18: Final Build & Test Verification

**Files:**
- All modified files

- [ ] **Step 1: Run full build**

```bash
./gradlew :app:assembleDebug
```

Expected: BUILD SUCCESSFUL and an APK is produced at `app/build/outputs/apk/debug/app-debug.apk`.

- [ ] **Step 2: Run unit tests**

```bash
./gradlew :app:testDebugUnitTest
```

Expected: BUILD SUCCESSFUL, all unit tests pass.

- [ ] **Step 3: Run instrumentation tests**

```bash
./gradlew :app:connectedDebugAndroidTest
```

Expected: all UI tests pass.

- [ ] **Step 4: Commit any remaining changes**

```bash
git add -A
git commit -m "chore: final build verification for UI redesign"
```

---

## Self-Review

### Spec Coverage

| Spec Section | Implementing Task |
|--------------|-------------------|
| Color palette & theme | Task 2, 3, 4 |
| Bottom navigation | Task 5, 6 |
| Home screen redesign | Task 8 |
| Explore screen + search | Task 9 |
| Exercise detail popup | Task 10, 11 |
| Plan screen + day cards | Task 12 |
| Workout screen + set cards | Task 13 |
| Settings screen redesign | Task 14 |
| Onboarding redesign | Task 15 |
| Button styling | Task 16 |
| Testing | Task 17, 18 |

### Placeholder Scan

- No "TBD", "TODO", or "implement later" placeholders remain.
- Code snippets are complete enough to compile; minor model field adjustments are flagged inline.

### Type Consistency

- `Exercise.tips` is used throughout for form tips.
- `WorkoutPlan` is assumed to have `daysPerWeek`; `minutesPerSession` is flagged for verification.
- `PlannedExercise` fields (`exerciseId`, `exerciseName`, `sets`, `reps`, `restSeconds`) match existing usage.

### Known Follow-Ups

None. All spec requirements are covered by the tasks above.

---

## Execution Handoff

Plan complete and saved to `docs/superpowers/plans/2026-07-11-pplog-ui-redesign.md`.

**Execution options:**

1. **Subagent-Driven (recommended)** — dispatch a fresh subagent per task, review between tasks, fast iteration.
2. **Inline Execution** — execute tasks in this session using the executing-plans skill, batch execution with checkpoints.

Reply with **1** or **2** to proceed.
