package kit.eliza.studyorganaizer.data.event_note


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kit.eliza.studyorganaizer.data.note.Note


@Entity(tableName = "event",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["idNote"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["idNote"])])
data class EventNote (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val idNote: Int,
    val yearStart: String,
    val yearEnd: String? = null,
    val text: String
)