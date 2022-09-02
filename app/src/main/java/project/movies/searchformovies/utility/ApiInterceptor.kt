package project.movies.searchformovies.utility

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import project.movies.searchformovies.utility.ApiInterceptor.ApiKey.API_KEY


class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter("api_key", API_KEY).build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }

    object ApiKey {
        const val API_KEY = "6ccd72a2a8fc239b13f209408fc31c33"
    }
}