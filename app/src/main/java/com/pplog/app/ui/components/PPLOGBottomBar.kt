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
