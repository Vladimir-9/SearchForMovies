package project.movies.searchformovies.remote

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import project.movies.searchformovies.db.models.MoviesContract

@JsonClass(generateAdapter = true)
data class RemoteMovies(
    val page: Int,
    val results: List<MoviesData>
)

@Entity(
    tableName = MoviesContract.TABLE_NAME,
    indices = [
        Index(MoviesContract.Columns.TITLE)
    ]
)

@Parcelize
@JsonClass(generateAdapter = true)
data class MoviesData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MoviesContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = MoviesContract.Columns.DESCRIPTIONS)
    @Json(name = "overview")
    val description: String,
    @ColumnInfo(name = MoviesContract.Columns.POSTER_PATH)
    val poster_path: String?,
    @ColumnInfo(name = MoviesContract.Columns.BACKDROP_PATH)
    val backdrop_path: String?,
    @ColumnInfo(name = MoviesContract.Columns.RELEASE_DATE)
    val release_date: String?,
    @ColumnInfo(name = MoviesContract.Columns.TITLE)
    val title: String
) : Parcelable