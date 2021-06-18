package app.common.data.network.interceptor

import app.common.data.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenInterceptor(private val pref: SharedPref) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
            .addHeader("Authorization", "Bearer ")
            .build()

        return chain.proceed(request)
    }
}

