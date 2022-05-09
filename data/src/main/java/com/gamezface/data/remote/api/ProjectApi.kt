package com.gamezface.data.remote.api

import retrofit2.http.*

interface ProjectApi {
    @DELETE
    @JvmSuppressWildcards
    suspend fun delete(
        @Url url: String,
        @QueryMap queries: Map<String, Any> = HashMap(),
        @HeaderMap headers: Map<String, Any> = HashMap()
    ): String?

    @HTTP(method = "DELETE", hasBody = true)
    @JvmSuppressWildcards
    suspend fun delete(
        @Url url: String,
        @Body body: Any? = null,
        @QueryMap queries: Map<String, Any> = HashMap(),
        @HeaderMap headers: Map<String, Any> = HashMap()
    ): String?

    @GET
    @JvmSuppressWildcards
    suspend fun get(
        @Url url: String,
        @QueryMap queries: Map<String, Any> = HashMap(),
        @HeaderMap headers: Map<String, Any> = HashMap()
    ): String?

    @POST
    @JvmSuppressWildcards
    suspend fun post(
        @Url url: String,
        @Body body: Any? = null,
        @QueryMap queries: Map<String, Any> = HashMap(),
        @HeaderMap headers: Map<String, Any> = HashMap()
    ): String?

    @PUT
    @JvmSuppressWildcards
    suspend fun put(
        @Url url: String,
        @Body body: Any? = null,
        @QueryMap queries: Map<String, Any> = HashMap(),
        @HeaderMap headers: Map<String, Any> = HashMap()
    ): String?
}