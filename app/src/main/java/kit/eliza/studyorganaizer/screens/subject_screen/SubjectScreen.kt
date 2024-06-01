package kit.eliza.studyorganaizer.screens.subject_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.SubjectEvent
import kit.eliza.studyorganaizer.data.subject.Subject
import kit.eliza.studyorganaizer.dialog.DialogMessage
import kit.eliza.studyorganaizer.dialog.DialogUI
import kit.eliza.studyorganaizer.screens.navigate_bar.NavBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubjectScreen(
    navController: NavHostController,
    vm: SubjectViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 6.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        //titleContentColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    title = {
                        Text(stringResource(id = R.string.TitleMySubject))

                    },
                    actions = {
                        IconButton(onClick = { vm.onEvent(SubjectEvent.AddSubjectEvent) }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                            )
                        }
                        IconButton(onClick = { vm.onEvent(SubjectEvent.OnSubjectEventSort) }) {
                            Icon(
                                imageVector = Icons.Default.SwapVert,
                                contentDescription = "Sort",
                            )
                        }
                    }
                )
            }
        }
    ) {
        val listSubject by vm.listSubject.collectAsState()
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 200.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 46.dp))
            }
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                SearchCard(vm)
            }
            items(items = listSubject) { item ->
                SubjectCard(item, vm, navController)
            }
        }
    }
    DialogUI(dialogController = vm)
    DialogMessage(controller = vm)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCard(vm: SubjectViewModel) {
    var search by remember { mutableStateOf("") }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth(),
        //.height(56.dp),
        query = search,
        onQueryChange = { text ->
            search = text
            vm.onEvent(SubjectEvent.OnSubjectEventSearch(search))
        },
        onSearch = {},
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(text = "Найти предмет")
        },
        trailingIcon = {
            IconButton(onClick = { vm.onEvent(SubjectEvent.OnSubjectEventSearch(search)) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    ) {
    }
    Spacer(modifier = Modifier.size(8.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectCard(
    subject: Subject,
    vm: SubjectViewModel,
    navController: NavHostController
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .height(200.dp)
            .combinedClickable(
                onClick = {
                    navController.navigate(Routes.SECTION_SCREEN + "/${subject.id}")
                },
                onLongClick = {
                    vm.onEvent(SubjectEvent.OnLongPressSubjectEvent(subject))
                }
            )

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = subject.name,
                style = MaterialTheme
                    .typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                //.padding(start = 8.dp)
            )
        }
    }
}