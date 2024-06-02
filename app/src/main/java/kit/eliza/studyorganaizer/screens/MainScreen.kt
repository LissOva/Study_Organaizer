package kit.eliza.studyorganaizer.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.screens.navigate_bar.NavBar
import kit.eliza.studyorganaizer.screens.note_screen.NoteScreen
import kit.eliza.studyorganaizer.screens.section_screen.SectionScreen
import kit.eliza.studyorganaizer.screens.subject_screen.AllNoteScreen
import kit.eliza.studyorganaizer.screens.subject_screen.SubjectScreen
import kit.eliza.studyorganaizer.screens.to_do_screen.ToDoScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavBar(navController = navController)
        }
    ) { it
       AppNavGraph(navController = navController)
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SUBJECT_SCREEN) {
        composable(Routes.SUBJECT_SCREEN) {
            SubjectScreen(navController = navController)
        }
        composable(
            Routes.SECTION_SCREEN + "/{subjectId}",
            arguments = listOf(navArgument("subjectId") { type = NavType.IntType })
        ) {backstackEntry ->
            SectionScreen(navController = navController, backstackEntry.arguments?.getInt("subjectId")!!)
        }
        composable(Routes.ALL_NOTE_LIST) {
            AllNoteScreen(navController = navController)
        }
        composable(Routes.TODO_SCREEN) {
            ToDoScreen(navController = navController)
        }
        composable(
            Routes.NOTE_SCREEN + "/{noteId}" + "/{mode}",
            arguments = listOf(
                navArgument("noteId") { type = NavType.IntType },
                navArgument("mode") { type = NavType.StringType },
            )
        ) {backstackEntry ->
            NoteScreen(navController = navController,
                backstackEntry.arguments?.getInt("noteId")!!,
                backstackEntry.arguments?.getString("mode")!!)
        }
    }
}
