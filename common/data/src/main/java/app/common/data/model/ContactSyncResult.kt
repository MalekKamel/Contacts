package app.common.data.model

data class ContactSyncResult(
    val new: List<ContactItem>,
    val modified: List<ContactItem>,
    val deleted: List<ContactItem>
) {
    fun isModified(): Boolean {
        return new.isNotEmpty() ||
                modified.isNotEmpty() ||
                deleted.isNotEmpty()
    }
}