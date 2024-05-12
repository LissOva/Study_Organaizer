package kit.eliza.studyorganaizer.data.text_note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kit.eliza.studyorganaizer.data.note.Note

@Entity(tableName = "text",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["idNote"],
            onDelete = ForeignKey.CASCADE
        )])
data class TextNote (
    @PrimaryKey
    val idNote: Int,
    val title: String,
    val text: String
)