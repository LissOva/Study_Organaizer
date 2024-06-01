package kit.eliza.studyorganaizer.screens.subject_screen
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kit.eliza.studyorganaizer.ModeNote
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.screens.navigate_bar.NavBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNoteScreen(
    navController: NavHostController,
    vm: AllNoteViewModel = hiltViewModel()){
    Scaffold(
        topBar = {
            Surface(shadowElevation = 6.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        //titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(id = R.string.TitleMyNotes))

                    },
                    /*navigationIcon = {
                    IconButton(onClick = { onEvent(
                        Event.Navigate(
                            Routes.SUBJECT_LIST
                        )
                    )
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },*/
                    actions = {
                        /*IconButton(onClick = { *//* do something *//* }) {
                            Icon(
                                painter = painterResource(id = R.drawable.swap_24),
                                contentDescription = "Localized description"
                            )
                        }*/
                    }
                )
            }
        }
    ) {
        vm.get()
        val listNotes = vm.listNotes?.collectAsState(initial = emptyList())
        Column(
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 200.dp)
            ) {
                item{
                    Spacer(modifier = Modifier.padding(top = 46.dp))
                }
                item{
                    SearchCard()
                }
                if (listNotes != null) {
                    items(listNotes.value) { note ->
                        NoteCard(note = note, navController = navController)
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCard() {
    var search by remember { mutableStateOf("") }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth(),
            //.height(56.dp),
        query = search,
        onQueryChange ={ text -> search = text },
        onSearch = {text ->

        },
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(text = "Найти запись")
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search ,contentDescription =  "Search")
            }
        }
    ) {
    }
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun NoteCard(
    note: Note,
    navController: NavHostController
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.NOTE_SCREEN + "/${note.id}" + "/${ModeNote.READ}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = note.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}