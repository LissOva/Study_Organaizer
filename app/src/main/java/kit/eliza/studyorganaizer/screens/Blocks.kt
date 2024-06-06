package kit.eliza.studyorganaizer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.data.event_note.EventNote
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.quote_note.QuoteNote
import kit.eliza.studyorganaizer.data.text_note.TextNote


val openSansFamily = FontFamily(
    Font(R.font.open_sans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.open_sans_light_italic, FontWeight.Light, FontStyle.Italic)
)

var  modifierTF = Modifier.fillMaxWidth(0.95f)

@Composable
fun TextBlock(
    text: TextNote
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        if (text.title != null) {
            if (text.title!!.isNotEmpty()) {
                Text(
                    text = text.title!!,
                    style = MaterialTheme
                        .typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
            }
        }
        Text(
            text = text.text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun TextBlockEdit(
    text: TextNote?,
    onUpdate: (TextNote) -> Unit
) {
    var titleCurrent by remember { mutableStateOf(text?.title ?: "") }
    var textCurrent by remember { mutableStateOf(text?.text ?: "") }

    Column() {
        OutlinedTextField(
            value = textCurrent,
            onValueChange = {
                textCurrent = it
                text?.text = it
                onUpdate(text!!)
            },
            label = { Text("Основной текст") },
            minLines = 3,
            modifier = modifierTF
        )
        OutlinedTextField(
            value = titleCurrent,
            onValueChange = {
                titleCurrent = it
                text?.title = it
                onUpdate(text!!)
            },
            label = { Text("Название текста(необязательно)") },
            modifier = modifierTF
        )
    }
}

@Composable
fun QuoteBlock(
    quote: QuoteNote
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Row {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = "quote",
                modifier = Modifier.rotate(180f),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = quote.text,
                fontFamily = openSansFamily,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 4.dp, top = 8.dp)
            )
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = quote.author,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun QuoteBlockEdit(
    quote: QuoteNote?,
    onUpdate: (QuoteNote) -> Unit
) {
    var textCurrent by remember { mutableStateOf(quote?.text ?: "") }
    var authorCurrent by remember { mutableStateOf(quote?.author ?: "") }

    Column() {
        OutlinedTextField(
            value = textCurrent,
            onValueChange = {
                textCurrent = it
                quote?.text = it
                onUpdate(quote!!)
            },
            label = { Text("Цитата") },
            minLines = 3,
            modifier = modifierTF
        )
        OutlinedTextField(
            value = authorCurrent,
            onValueChange = {
                authorCurrent = it
                quote?.author = it
                onUpdate(quote!!)
            },
            label = { Text("Автор(необязательно)") },
            modifier = modifierTF
        )
    }
}

@Composable
fun FormulaBlock(
    formula: FormulaNote
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        if (formula.name != null) {
            if (formula.name!!.isNotEmpty()) {
                Text(
                    text = formula.name!!,
                    style = MaterialTheme
                        .typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = formula.formula,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
        }
        if (formula.text != null) {
            if (formula.text!!.isNotEmpty()) {
                Text(
                    text = formula.text!!,
                    style = MaterialTheme.typography.bodyLarge

                )
            }
        }
        if (formula.author != null) {
            if (formula.author!!.isNotEmpty()) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = formula.author!!,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun FormulaBlockEdit(
    formula: FormulaNote?,
    onUpdate: (FormulaNote) -> Unit
) {
    var nameCurrent by remember { mutableStateOf(formula?.name ?: "") }
    var formulaCurrent by remember { mutableStateOf(formula?.formula ?: "") }
    var textCurrent by remember { mutableStateOf(formula?.text ?: "") }
    var authorCurrent by remember { mutableStateOf(formula?.author ?: "") }

    Column() {
        OutlinedTextField(
            value = formulaCurrent,
            onValueChange = {
                formulaCurrent = it
                formula?.formula = it
                onUpdate(formula!!)
            },
            label = { Text("Формула") },
            modifier = modifierTF
        )
        OutlinedTextField(
            value = nameCurrent,
            onValueChange = {
                nameCurrent = it
                formula?.name = it
                onUpdate(formula!!)
            },
            label = { Text("Название(необязательно)") },
            modifier = modifierTF
        )
        OutlinedTextField(
            value = textCurrent,
            onValueChange = {
                textCurrent = it
                formula?.text = it
                onUpdate(formula!!)
            },
            label = { Text("Описание(необязательно") },
            minLines = 3,
            modifier = modifierTF
        )
        OutlinedTextField(
            value = authorCurrent,
            onValueChange = {
                authorCurrent = it
                formula?.author = it
                onUpdate(formula!!)
            },
            label = { Text("Автор(необязательно)") },
            modifier = modifierTF
        )
    }
}

@Composable
fun EventBlock(
    event: EventNote
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = event.yearStart.plus(
                if (event.yearEnd != null && event.yearEnd != "") {
                    "\n-\n"
                } else {
                    ""
                }
            ).plus(event.yearEnd ?: ""), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = event.text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 12.dp)
        )
    }
}

@Composable
fun EventBlockEdit(
    event: EventNote?,
    onUpdate: (EventNote) -> Unit
) {
    var yearStartCurrent by remember { mutableStateOf(event?.yearStart ?: "") }
    var yearEndCurrent by remember { mutableStateOf(event?.yearEnd ?: "") }
    var textCurrent by remember { mutableStateOf(event?.text ?: "") }

    Column() {
        OutlinedTextField(
            value = textCurrent,
            onValueChange = {
                textCurrent = it
                event?.text = it
                onUpdate(event!!)
            },
            label = { Text("Что произошло") },
            minLines = 3,
            modifier = modifierTF
        )
        OutlinedTextField(
            value = yearStartCurrent,
            onValueChange = {
                yearStartCurrent = it
                event?.yearStart = it
                onUpdate(event!!)
            },
            label = { Text("Дата события/Начало события") },
            modifier = modifierTF
        )
        OutlinedTextField(
            value = yearEndCurrent,
            onValueChange = {
                yearEndCurrent = it
                event?.yearEnd = it
                onUpdate(event!!)
            },
            label = { Text("Конец события(необязательно)") },
            modifier = modifierTF
        )
    }
}


