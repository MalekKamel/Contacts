package app.common.data.model

data class ContactsRequest (
        var search: String = "",
        var nextPage: Int = 1
)
