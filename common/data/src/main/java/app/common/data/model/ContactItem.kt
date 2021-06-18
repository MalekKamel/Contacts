package app.common.data.model

data class ContactItem(
    var id: Long,
    var name: String?,
    var phone: String?
) {
    constructor() : this(0L, "", "")
}
