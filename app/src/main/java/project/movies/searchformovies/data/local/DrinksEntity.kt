package project.movies.searchformovies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import project.movies.searchformovies.domain.model.DrinksContract

@Entity(
    tableName = DrinksContract.TABLE_NAME,
    indices = [Index(DrinksContract.Columns.ID)]
)

data class DrinksEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DrinksContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = DrinksContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = DrinksContract.Columns.IMAGE)
    val image: String
)