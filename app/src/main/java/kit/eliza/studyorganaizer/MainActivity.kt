package kit.eliza.studyorganaizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kit.eliza.studyorganaizer.screens.MainScreen
import kit.eliza.studyorganaizer.screens.navigate_bar.NavBar
import kit.eliza.studyorganaizer.screens.note_screen.NoteScreen
import kit.eliza.studyorganaizer.screens.section_screen.SectionScreen
import kit.eliza.studyorganaizer.screens.subject_screen.AllNoteScreen
import kit.eliza.studyorganaizer.screens.subject_screen.SubjectScreen
import kit.eliza.studyorganaizer.ui.theme.StudyOrganaizerTheme
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyOrganaizerTheme {
                val folderName = FileName.FOLDER
                val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folderName)
                if (!folder.exists()) {
                    folder.mkdirs()
                }

                MainScreen()
            }
        }
    }
}

