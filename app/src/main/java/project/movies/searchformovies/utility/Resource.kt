package project.movies.searchformovies.utility

sealed class Resource<T>(val data: T?, val code: Int?, val errorBody: String?) {
    class Success<T>(data: T?) : Resource<T>(data, null, null)
    class Error<T>(code: Int?, errorBody: String?) : Resource<T>(null, code, errorBody)
}