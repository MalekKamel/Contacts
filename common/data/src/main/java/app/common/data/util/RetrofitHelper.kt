package app.common.data.util

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun <T> createService(baseUrl: String, client: OkHttpClient, service: Class<T>): T {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(service)
    }
}