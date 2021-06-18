package app.common.data.domain.contacts

import app.common.data.model.ContactsRequest
import app.common.data.model.ContactsResponse

class ContactsRemoteDataSrc(private val api: ContactsApi) {

    suspend fun contacts(request: ContactsRequest): ContactsResponse {
        return api.contacts(page = request.nextPage)
    }

}