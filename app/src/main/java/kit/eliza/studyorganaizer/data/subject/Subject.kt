package kit.eliza.studyorganaizer.data.subject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String
)