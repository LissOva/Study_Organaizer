package kit.eliza.studyorganaizer.screens.to_do_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kit.eliza.studyorganaizer.ToDoEvent
import kit.eliza.studyorganaizer.data.to_do.ToDo
import kit.eliza.studyorganaizer.dialog.DialogMessage
import kit.eliza.studyorganaizer.dialog.DialogUI

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
    vm: ToDoViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            Surface(shadowElevation = 6.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    title = {
                        Text(text = "Задачи")

                    }
                )
            }
        },
    ) { innerPadding ->
        vm.onEvent(ToDoEvent.GetToDo)
        val listToDo = vm.listToDo?.collectAsState(initial = emptyList())
        val listCompleteToDo = vm.listCompleteToDo?.collectAsState(initial = emptyList())
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
                        var toDoName by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                            modifier = Modifier
                                .weight(1f),
                            value = toDoName,
                            onValueChange = { text ->
                                toDoName = text
                            },
                            label = { Text(text = "Новая задача") },
                            trailingIcon = {
                                IconButton(onClick = {
                                    vm.onEvent(
                                        ToDoEvent.OnToDoEventInsert(
                                            ToDo(
                                                null,
                                                toDoName,
                                                false
                                            )
                                        )
                                    )
                                    toDoName = ""
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

            if (listToDo != null) {
                items(listToDo.value) { toDo ->
                    ToDoCard(vm = vm, toDo = toDo)
                }
            }

            if (listCompleteToDo != null && listCompleteToDo.value.isNotEmpty()) {
                item {
                    Text(
                        text = "Завершено",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                items(listCompleteToDo.value) { toDo ->
                    ToDoCard(vm = vm, toDo = toDo)
                }
            }

        }
    }
    DialogUI(dialogController = vm)
    DialogMessage(controller = vm)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoCard(
    vm: ToDoViewModel,
    toDo: ToDo
) {
    val cardColor = if (!toDo.status) {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    } else {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    }
    val fontColor = if (!toDo.status) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.secondary
    }
    ElevatedCard(
        colors = cardColor,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    vm.onEvent(ToDoEvent.OnPressToDoEvent(toDo))
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Checkbox(checked = toDo.status,
                onCheckedChange = {
                    toDo.status = !toDo.status
                    vm.onEvent(ToDoEvent.OnToDoEventInsert(toDo))
                })
            Text(
                text = toDo.name,
                style = MaterialTheme.typography.titleMedium,
                color = fontColor,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}
