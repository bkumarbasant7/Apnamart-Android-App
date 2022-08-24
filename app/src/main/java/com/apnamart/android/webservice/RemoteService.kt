package com.apnamart.android.utils

import android.content.Context
import com.apnamart.android.webservice.response.TrendingRepoResponse
import com.google.gson.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

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
        return OkHttpClient().newBuilder()
//        .addInterceptor(offlineIntercepter)
            .addNetworkInterceptor(onlineIntercepter)
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val onlineIntercepter = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var response = chain.proceed(chain.request())
            var maxAge = 60 * 60 * 2 //2 hr
            return response.newBuilder().header("Cache-Control", "public,max-age=$maxAge")
                .removeHeader("Pragma").build()
        }

    }
    private val offlineIntercepter = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            var maxStale = 60 * 60 * 24 * 30
            request = request.newBuilder()
                .header("Cache-Control", "public,only-if-cached,max-stale=$maxStale")
                .removeHeader("Pragma").build()
            return chain.proceed(request)
        }

    }
}


//    .addNetworkInterceptor(ChuckInterceptor(InstaApi.App.appContext))


