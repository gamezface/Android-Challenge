package com.gamezface.core.base

import com.gamezface.core.BuildConfig
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ClientBuilder {
    companion object {
        fun <S> createService(
            serviceClass: Class<S>,
            gson: Gson
        ): S = createRetrofitClient(gson).create(serviceClass)

        private fun createInterceptors(): ArrayList<Interceptor> {
            val interceptors = arrayListOf<Interceptor>()
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
            interceptors.add(loggingInterceptor)
            return interceptors
        }

        private fun createOkHttpClient(): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .followRedirects(false)
            val interceptors = createInterceptors()
            interceptors.forEach {
                clientBuilder.addInterceptor(it)
            }
            return clientBuilder.build()
        }

        private fun createRetrofitClient(gson: Gson): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(createOkHttpClient())
                .build()
        }
    }
}