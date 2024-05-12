package kit.eliza.studyorganaizer.data.section

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kit.eliza.studyorganaizer.data.subject.Subject

@Entity(tableName = "section",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["idSubject"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["idSubject"])])
data class Section(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val idSubject: Int,
    val name: String
)
