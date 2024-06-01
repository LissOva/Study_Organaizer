package kit.eliza.studyorganaizer.data.note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kit.eliza.studyorganaizer.data.section.Section

@Entity(tableName = "note",
    foreignKeys = [
        ForeignKey(
            entity = Section::class,
            parentColumns = ["id"],
            childColumns = ["idSection"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["idSection"])])
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var idSection: Int,
    var name: String,
    var idType: Int
)
