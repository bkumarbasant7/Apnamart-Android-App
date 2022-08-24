package com.apnamart.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.apnamart.android.webservice.response.TrendingRepoResponse
import com.google.gson.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RemoteService {
    @GET("https://api.github.com/search/repositories?q=stars")
    suspend fun getTrendingRepos(): Response<TrendingRepoResponse>
}

class RemoteServiceProvider(context: Context) {
    var gson =
        GsonBuilder().registerTypeAdapter(Double::class.java, object : JsonSerializer<Double?> {
            override fun serialize(
                src: Double?,
                typeOfSrc: java.lang.reflect.Type?,
                context: JsonSerializationContext?
            ): JsonElement {
                return if (src == src!!.toLong()
                        .toDouble()
                ) JsonPrimitive(src.toLong()) else JsonPrimitive(
                    src
                )
            }
        }).create()
    val webService = Retrofit.Builder()
        .client(getOkHttpClient(context))
        .baseUrl("https://api.github.com/search/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build().create(RemoteService::class.java)

    private fun getOkHttpClient(context: Context): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (context.hasNetwork()!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()
    }


}


//    .addNetworkInterceptor(ChuckInterceptor(InstaApi.App.appContext))


