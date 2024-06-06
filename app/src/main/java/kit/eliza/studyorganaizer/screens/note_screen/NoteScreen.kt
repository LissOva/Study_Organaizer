package kit.eliza.studyorganaizer.screens.note_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kit.eliza.studyorganaizer.screens.ModeNote
import kit.eliza.studyorganaizer.screens.NoteBlock
import kit.eliza.studyorganaizer.screens.NoteEvent
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.data.event_note.EventNote
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.quote_note.QuoteNote
import kit.eliza.studyorganaizer.data.text_note.TextNote
import kit.eliza.studyorganaizer.dialog.dialogMessage.DialogMessage
import kit.eliza.studyorganaizer.screens.EventBlock
import kit.eliza.studyorganaizer.screens.EventBlockEdit
import kit.eliza.studyorganaizer.screens.FormulaBlock
import kit.eliza.studyorganaizer.screens.FormulaBlockEdit
import kit.eliza.studyorganaizer.screens.QuoteBlock
import kit.eliza.studyorganaizer.screens.QuoteBlockEdit
import kit.eliza.studyorganaizer.screens.TextBlock
import kit.eliza.studyorganaizer.screens.TextBlockEdit

//Экран отображения записей

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NoteScreen(
    navController: NavHostController,
    noteId: Int,
    mode: String,
    vm: NoteViewModel = hiltViewModel()
) {
    vm.noteId = noteId
    vm.mode = mode
    vm.navController = navController

    vm.onEvent(NoteEvent.GetNote(vm.noteId))

    val listChips = vm.listType?.collectAsState(initial = emptyList())

    val selectedChip = remember { mutableStateOf("Тема") }

    if (listChips != null) {
        selectedChip.value =
            vm.note?.let { it1 -> listChips.value[it1.idType - 1].name }.toString()
    }

    if (vm.noteId == 0) {
        selectedChip.value = "Тема"
    }

    var currentMode by remember { mutableStateOf(vm.mode) }
    val listQuote by vm.listQuote.collectAsState()
    val listEvent by vm.listEvent.collectAsState()
    val listFormula by vm.listFormula.collectAsState()
    val textNote by vm.textNote.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            Surface(shadowElevation = 6.dp) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    title = {
                        when (currentMode) {
                            ModeNote.READ -> {
                                Text(text = vm.note?.name ?: "")
                            }
                            ModeNote.EDIT -> {
                                Text(text = stringResource(id = R.string.TitleEditNote))
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            when (currentMode) {
                                ModeNote.READ -> {
                                    navController.navigateUp()
                                }

                                ModeNote.EDIT -> {
                                    vm.selectedNoteBlock = NoteBlock.NOTE
                                    vm.openMessage.value = true
                                }
                            }
                        }) {
                            if (currentMode == ModeNote.READ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            when (currentMode) {
                                ModeNote.READ -> {
                                    currentMode = ModeNote.EDIT
                                }
                                ModeNote.EDIT -> {
                                    var type = 1;
                                    listChips!!.value.forEach { typeNote ->
                                        if(typeNote.name == selectedChip.value){
                                            type = typeNote.id!!
                                        }
                                    }
                                    var flag = false
                                    if(textNote != null){
                                        if(textNote!!.text == ""){
                                            flag = true
                                        }
                                    }
                                    when(type){
                                        1 ->{
                                            listEvent.forEach{event->
                                                if(event.text == "" || event.yearStart == ""){
                                                    flag = true
                                                }
                                            }
                                            listQuote.forEach{quote ->
                                                if(quote.text == ""){
                                                    flag = true
                                                }
                                            }
                                            listFormula.forEach{formula ->
                                                if(formula.formula == ""){
                                                    flag = true
                                                }
                                            }
                                        }
                                        2 ->{
                                            listEvent.forEach{event->
                                                if(event.text == "" || event.yearStart == ""){
                                                    flag = true
                                                }
                                            }
                                            listQuote.forEach{quote ->
                                                if(quote.text == ""){
                                                    flag = true
                                                }
                                            }
                                        }
                                        3 ->{
                                            listFormula.forEach{formula ->
                                                if(formula.formula == ""){
                                                    flag = true
                                                }
                                            }
                                        }
                                    }
                                    if(flag){
                                        flag = false
                                        Toast.makeText(context, "Все обязательные поля должны быть заполнены!", Toast.LENGTH_LONG).show()
                                    } else {
                                        vm.onEvent(NoteEvent.SaveNote(type))
                                        currentMode = ModeNote.READ
                                    }
                                }
                            }
                        }) {
                            if (currentMode == ModeNote.READ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Complete"
                                )
                            }
                        }
                    }
                )
            }

        },
    ) { innerPadding ->
        if (currentMode != ModeNote.READ) {
            FlowColumn(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 200.dp)
            ) {
                Spacer(modifier = Modifier.padding(innerPadding))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.size(8.dp))
                    listChips?.value?.forEach { chip ->
                        FilterChip(
                            onClick = {
                                selectedChip.value = chip.name
                            },
                            label = {
                                Text(chip.name)
                            },
                            selected = chip.name == selectedChip.value,
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
                 var editNoteName by remember {
                     mutableStateOf(vm.note?.name ?: "")
                 }
                OutlinedTextField(
                    value = editNoteName,
                    onValueChange = { editNoteName = it
                        vm.note?.name = it
                        vm.onEvent(NoteEvent.OnNoteEventUpdate(vm.note!!))
                        /*noteName = it */},
                    label = { Text("Название заметки") },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 16.dp)
                )
                val modifierScreen = Modifier.padding(horizontal = 16.dp, vertical = 0.dp)

                if (textNote != null) {
                    Title(title = "Основное описание/текст")
                    Row {
                        IconButton(onClick = {
                            vm.selectedNoteBlock = NoteBlock.TEXT
                            vm.openMessage.value = true
                        }) {
                            IconDelete()
                        }
                        TextBlockEdit(text = textNote){textNote ->
                            vm.onEvent(NoteEvent.OnNoteEventInsertText(textNote))
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            onClick = {
                                vm.onEvent(NoteEvent.OnNoteEventInsertText(TextNote(idNote = noteId, "", "")))
                                vm.onEvent(NoteEvent.GetNote(vm.noteId))
                            }, modifier = modifierScreen
                                .padding(top = 4.dp)
                        ) {
                            AddLabel(label = "Текст")
                        }
                    }
                }
                if (selectedChip.value == "Биография" || selectedChip.value == "Тема") {
                    if (listEvent.isNotEmpty()) {
                        Title(title = "События")
                        listEvent.forEach { event ->
                            Row {
                                IconButton(
                                    onClick = {
                                        vm.selectedNoteBlock = NoteBlock.EVENT
                                        vm.selectedEvent = event
                                        vm.openMessage.value = true
                                    },
                                ) {
                                    IconDelete()
                                }
                                EventBlockEdit(event = event){event ->
                                    vm.onEvent(NoteEvent.OnNoteEventInsertEvent(event))
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            onClick = {
                                vm.onEvent(NoteEvent.OnNoteEventInsertEvent(EventNote(null, noteId, "", "", "")))
                            },
                            modifier = modifierScreen.padding(top = 4.dp)
                        ) {
                            AddLabel(label = "Cобытие")
                        }
                    }
                }
                if (selectedChip.value == "Биография" || selectedChip.value == "Тема") {
                    if (listQuote.isNotEmpty()) {
                        Title(title = "Цитаты")
                        listQuote.forEach { quote ->
                            Row {
                                IconButton(onClick = {
                                    vm.selectedNoteBlock = NoteBlock.QUOTE
                                    vm.selectedQuote = quote
                                    vm.openMessage.value = true
                                }) {
                                    IconDelete()
                                }
                                QuoteBlockEdit(quote = quote){quote->
                                    vm.onEvent(NoteEvent.OnNoteEventInsertQuote(quote))
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            onClick = {
                                vm.onEvent(NoteEvent.OnNoteEventInsertQuote(QuoteNote(null, noteId, "", "")))
                            },
                            modifier = modifierScreen.padding(top = 4.dp)
                        ) {
                            AddLabel(label = "Цитата")
                        }

                    }
                }
                if (selectedChip.value == "Правило" || selectedChip.value == "Тема") {

                    if (listFormula.isNotEmpty()) {
                        Title(title = "Формулы")
                        listFormula.forEach { formula ->
                            Row {
                                IconButton(onClick = {
                                    vm.selectedNoteBlock = NoteBlock.FORMULA
                                    vm.selectedFormula = formula
                                    vm.openMessage.value = true
                                }) {
                                    IconDelete()
                                }
                                FormulaBlockEdit(formula = formula){formula ->
                                    vm.onEvent(NoteEvent.OnNoteEventInsertFormula(formula))
                                }
                            }
                        }

                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Button(
                            onClick = {
                                vm.onEvent(NoteEvent.OnNoteEventInsertFormula(FormulaNote(null, noteId, "", "", "")))
                            },
                            modifier = modifierScreen.padding(top = 4.dp)
                        ) {
                            AddLabel(label = "Формула")
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 200.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.padding(innerPadding))
                }
                item {
                    textNote?.let {
                            TextBlock(text = it)
                    }
                }
                if (listEvent.isNotEmpty()) {
                    item {
                        TitleSmall(title = if (listEvent.size == 1 ) "Событие" else "События")
                        Line()
                    }
                items(listEvent) { event ->
                    EventBlock(event = event)
                }
                }
                if (listFormula.isNotEmpty()) {
                    item {
                        TitleSmall(title = if (listFormula.size == 1 ) "Формула" else "Формулы")
                        Line()
                    }
                items(listFormula) { formula ->
                    FormulaBlock(formula = formula)

                }
                    }
                if (listQuote.isNotEmpty()) {
                    item {
                        TitleSmall(title = if (listQuote.size == 1) "Цитата" else "Цитаты")
                        Line()
                    }
                    items(listQuote) { quote ->
                        QuoteBlock(quote = quote)
                    }
                }
            }
        }
    }
    DialogMessage(controller = vm)
}

@Composable
fun Line() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp), color = MaterialTheme.colorScheme.secondaryContainer
    ) {}
}

@Composable
fun IconDelete() {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Delete",
        tint = MaterialTheme.colorScheme.error
    )
}

@Composable
fun AddLabel(label: String) {
    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    Text(label, modifier = Modifier.padding(horizontal = 8.dp))
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(start = 16.dp, top = 6.dp)
    )
}

@Composable
fun TitleSmall(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 16.dp, top = 6.dp)
    )
}



