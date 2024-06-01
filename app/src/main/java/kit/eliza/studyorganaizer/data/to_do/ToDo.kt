package kit.eliza.studyorganaizer.data.to_do

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    var status: Boolean,
)
