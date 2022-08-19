package com.apnamart.android.utils

import com.apnamart.android.webservice.response.TrendingRepoResponse
import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface RemoteService {
    companion object Factory {
        fun create(): RemoteService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/search/repositories?q=stars")
                .build()

            return retrofit.create(RemoteService::class.java)
        }
    }
    @GET("https://api.github.com/search/repositories?q=stars")
    suspend fun getTrendingRepos(): Response<TrendingRepoResponse>
}

private val okClient = OkHttpClient().newBuilder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(120, TimeUnit.SECONDS)
    .writeTimeout(120, TimeUnit.SECONDS)
    .build()

//    .addNetworkInterceptor(ChuckInterceptor(InstaApi.App.appContext))


var gson = GsonBuilder().registerTypeAdapter(Double::class.java, object : JsonSerializer<Double?> {
    override fun serialize(
        src: Double?,
        typeOfSrc: java.lang.reflect.Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src == src!!.toLong().toDouble()) JsonPrimitive(src.toLong()) else JsonPrimitive(
            src
        )
    }
}).create()

val webservice by lazy {
    Retrofit.Builder()
        .client(okClient)
        .baseUrl("https://api.github.com/search/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build().create(RemoteService::class.java)
}

interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation

}