package kit.eliza.studyorganaizer.screens.navigate_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavBar(
   navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val listItems = listOf(
        BarItem.SubjectItem,
        BarItem.NoteItem,
        BarItem.ToDoItem,
        BarItem.SettingsItem
    )
    var flag = false

    listItems.forEach{item ->
        if(currentRoute?.hierarchy?.any { it.route == item.route} == true ){
            flag = true
            return@forEach
        }
    }
    if(flag) {
        NavigationBar {
            listItems.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "icon"
                        )
                    },
                    label = {
                        Text(text = stringResource(id = item.label))
                    },

                    alwaysShowLabel = false
                )
            }
        }
    }
}