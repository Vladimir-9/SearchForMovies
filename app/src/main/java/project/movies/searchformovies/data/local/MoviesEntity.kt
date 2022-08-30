package project.movies.searchformovies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import project.movies.searchformovies.domain.model.MoviesContract

@Entity(
    tableName = MoviesContract.TABLE_NAME,
    indices = [Index(MoviesContract.Columns.TITLE)]
)

data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MoviesContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = MoviesContract.Columns.DESCRIPTIONS)
    val description: String,
    @ColumnInfo(name = MoviesContract.Columns.POSTER_PATH)
    val posterPath: String,
    @ColumnInfo(name = MoviesContract.Columns.BACKDROP_PATH)
    val backdropPath: String,
    @ColumnInfo(name = MoviesContract.Columns.RELEASE_DATE)
    val releaseDate: String,
    @ColumnInfo(name = MoviesContract.Columns.TITLE)
    val title: String
)