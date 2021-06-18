package app.common.data.domain.contacts

import app.common.data.model.ContactsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsApi {

    @GET("contacts")
    suspend fun contacts(@Query("page") page: Int): ContactsResponse

}


