package kit.eliza.studyorganaizer.data.type_note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class TypeNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
)