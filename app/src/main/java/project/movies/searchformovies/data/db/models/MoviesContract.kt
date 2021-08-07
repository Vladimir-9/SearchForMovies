package project.movies.searchformovies.data.db.models

object MoviesContract {
    const val TABLE_NAME = "favouritesMovies"

    object Columns {
        const val ID = "id"
        const val DESCRIPTIONS = "descriptions"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val RELEASE_DATE = "release_date"
        const val TITLE = "title"
    }
}