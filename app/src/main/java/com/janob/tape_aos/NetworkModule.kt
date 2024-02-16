package com.janob.tape_aos

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

const val BASE_URL = "http://3.36.97.28:3000"

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(NetworkModule.provideOkHttpClient(NetworkModule.AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create()).build()
    return retrofit
}
object NetworkModule {

    fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : okhttp3.Response = with(chain) {
            val accessToken = KaKaoApplication.prefs.getString("accessToken", "")
            val newRequest = request().newBuilder()
                .addHeader("authorization", accessToken)
                .build()
            proceed(newRequest)
        }
    }
/*    fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
            = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : okhttp3.Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("(header Key)", "(header Value)")
                .build()
            proceed(newRequest)
        }
    }*/
}

