package app.common.data.network.interceptor

import app.common.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import app.common.data.pref.SharedPref
import java.io.IOException

class TokenInterceptor(private val pref: SharedPref) : Interceptor {
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYTNkOTkzZTNmOGJhOGIzNDJmNTAyZjllZDY5ZTUzMiIsInN1YiI6IjVmMTQzMTViMWM2YWE3MDAzNTgxN2U2ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1R5tfdhb_LC_MqR1SrTDTtJKvWZsSQGC72WFlF4oiOU"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
                .addHeader("Authorization", "Bearer ")
                .build()

        return chain.proceed(request)
    }
}

/*

eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYTNkOTkzZTNmOGJhOGIzNDJmNTAyZjllZDY5ZTUzMiIsInN1YiI6IjVmMTQzMTViMWM2YWE3MDAzNTgxN2U2ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1R5tfdhb_LC_MqR1SrTDTtJKvWZsSQGC72WFlF4oiOU
*/