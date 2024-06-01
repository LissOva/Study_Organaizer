package kit.eliza.studyorganaizer.data.formula_note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kit.eliza.studyorganaizer.data.note.Note

@Entity(tableName = "formula",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["idNote"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["idNote"])])
class FormulaNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val idNote: Int,
    var formula: String,
    var text: String? = null,
    var name: String? = null,
    var author: String? = null
)