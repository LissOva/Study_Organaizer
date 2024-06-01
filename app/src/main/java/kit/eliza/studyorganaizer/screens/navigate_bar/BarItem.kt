package kit.eliza.studyorganaizer.screens.navigate_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.Routes

sealed class BarItem(val label: Int, val icon: ImageVector, val route: String) {
    object SubjectItem: BarItem(R.string.BarItemSubject, Icons.Outlined.Folder, Routes.SUBJECT_SCREEN)
    object NoteItem: BarItem(R.string.BarItemNotes, Icons.Outlined.Description, Routes.ALL_NOTE_LIST)
    object SettingsItem: BarItem(R.string.BarItemSettings,Icons.Outlined.Settings, Routes.SETTING_SCREEN)
}