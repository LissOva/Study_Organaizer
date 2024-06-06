package kit.eliza.studyorganaizer.screens.section_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PriorityHigh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kit.eliza.studyorganaizer.screens.ModeNote
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.screens.SectionEvent
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.section.Section
import kit.eliza.studyorganaizer.dialog.dialogMessage.DialogMessage
import kit.eliza.studyorganaizer.dialog.dialog.DialogUI

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionScreen(
    navController: NavHostController,
    subjectId: Int,
    vm: SectionViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 6.dp) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    title = {
                        Text(text = vm.subjectName)

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        vm.subjectId = subjectId
        vm.onEvent(SectionEvent.GetSections(vm.subjectId))
        vm.onEvent(SectionEvent.GetSubject(vm.subjectId))
        val listSection = vm.listSection?.collectAsState(initial = emptyList())
        val map = LinkedHashMap<Int, State<List<Note>>?>()

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 4.dp, bottom = 400.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(innerPadding))
                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    modifier = Modifier.padding(
                        top = 4.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        TextField(
                            modifier = Modifier
                                .weight(1f),
                            value = vm.sectionName.value,
                            onValueChange = { text ->
                                vm.onEvent(SectionEvent.OnSectionEventNameChange(text))
                            },
                            label = { Text(text = "Новый раздел") },
                            trailingIcon = {
                                IconButton(onClick = {
                                    vm.onEvent(SectionEvent.OnSectionEventInsert)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add"
                                    )
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
            }
            if (listSection != null) {
                items(listSection.value) { section ->
                    vm.onEvent(SectionEvent.GetNotes(section.id!!))
                    val list = vm.list?.collectAsState(initial = emptyList())
                    map[section.id] = list;
                    SectionCard(
                        vm,
                        section = section,
                        map[section.id],
                        navController = navController
                    )
                }
            }
        }
    }
    DialogUI(dialogController = vm)
    DialogMessage(controller = vm)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionCard(
    vm: SectionViewModel,
    section: Section,
    noteList: State<List<Note>>?,
    navController: NavHostController
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    expanded = !expanded
                },
                onLongClick = {
                    vm.onEvent(SectionEvent.OnLongPressSectionEvent(section))
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = section.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = "expanded",
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
    if (expanded) {
        var noteName by remember {
            mutableStateOf("")
        }
        Column(
            Modifier.padding(start = 16.dp, top = 8.dp)
        ) {

            if (noteList!!.value.isNotEmpty()) {
                noteList.value.forEach { note ->
                    NoteCard(note = note, navController = navController)
                }
            }

            Card(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(
                        top = 4.dp
                    )
            ) {
                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    value = noteName,
                    onValueChange = { text ->
                        noteName = text
                    },
                    label = { Text(text = "Новая заметка") },
                    trailingIcon = {
                        IconButton(onClick = {
                            vm.onEvent(SectionEvent.AddNote(section.id!!, noteName))
                            noteName = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun NoteCard(
    note: Note,
    navController: NavHostController
) {
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier
            .padding(top = 4.dp)
            .clickable {
                navController.navigate(Routes.NOTE_SCREEN + "/${note.id}" + "/${ModeNote.READ}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {
            var iconType = Icons.AutoMirrored.Outlined.Article
            when(note.idType){
                2 ->{
                    iconType = Icons.Outlined.AccountBox
                }
                3 ->{
                    iconType = Icons.Outlined.PriorityHigh
                }
                4 -> {
                    iconType = Icons.Outlined.BookmarkBorder
                }
            }
            Icon(imageVector = iconType, contentDescription = "type", modifier = Modifier
                .padding(start = 8.dp))
            Text(
                text = note.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
    }
}

