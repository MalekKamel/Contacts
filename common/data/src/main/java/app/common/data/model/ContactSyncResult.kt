package app.common.data.model

data class ContactSyncResult(
    val new: List<ContactItem>,
    val modified: List<ContactItem>
) {
    fun isModified(): Boolean {
        return new.isNotEmpty() || modified.isNotEmpty()
    }
}