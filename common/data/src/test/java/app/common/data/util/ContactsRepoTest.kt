package app.common.data.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.common.data.domain.contacts.ContactSynchronizerContract
import app.common.data.domain.contacts.ContactsLocalDataSrcContract
import app.common.data.domain.contacts.ContactsProviderDataSrcContract
import app.common.data.domain.contacts.ContactsRepo
import app.common.data.model.ContactItem
import app.common.data.model.ContactSyncResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactsRepoTest {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: ContactsRepo
    private lateinit var localSrc: FakeContactsLocalDataSrc
    private lateinit var provider: FakeContactsProviderDataSrc
    private lateinit var synchronizer: FakeContactSynchronizer

    @Before
    fun setup() {
        localSrc = FakeContactsLocalDataSrc()
        provider = FakeContactsProviderDataSrc()
        synchronizer = FakeContactSynchronizer()
        repo = ContactsRepo(
            localSrc,
            provider,
            synchronizer
        )
    }

    @Test
    fun `contacts return local items`() = runBlockingTest {
        localSrc.items = listOf(ContactItem(1, "name", "123"))

        val value = repo.contacts()

        MatcherAssert.assertThat(
            value[0],
            CoreMatchers.not(CoreMatchers.nullValue())
        )
        MatcherAssert.assertThat(
            value[0].name,
            CoreMatchers.equalTo("name")
        )
    }

    @Test
    fun `contacts return provider items`() = runBlockingTest {
        localSrc.items = emptyList()

        val value = repo.contacts()

        MatcherAssert.assertThat(
            value[0],
            CoreMatchers.not(CoreMatchers.nullValue())
        )
        MatcherAssert.assertThat(
            value[0].name,
            CoreMatchers.equalTo("provider")
        )
    }

    @Test
    fun sync() = runBlockingTest {
        localSrc.items = emptyList()

        val value = repo.sync()

        MatcherAssert.assertThat(
            value,
            CoreMatchers.equalTo(true)
        )

        MatcherAssert.assertThat(
            localSrc.saved[0].name,
            CoreMatchers.equalTo("new")
        )

        MatcherAssert.assertThat(
            localSrc.updated[0].name,
            CoreMatchers.equalTo("modified")
        )

        MatcherAssert.assertThat(
            localSrc.deleted[0].name,
            CoreMatchers.equalTo("deleted")
        )
    }

}

class FakeContactsLocalDataSrc : ContactsLocalDataSrcContract {
    var items: List<ContactItem> = emptyList()
    var saved: List<ContactItem> = emptyList()
    var updated: List<ContactItem> = emptyList()
    var deleted: List<ContactItem> = emptyList()

    override suspend fun all(): List<ContactItem> {
        return items
    }

    override suspend fun save(contacts: List<ContactItem>) {
        saved = contacts
    }

    override suspend fun update(contacts: List<ContactItem>) {
        updated = contacts
    }

    override suspend fun delete(contacts: List<ContactItem>) {
        deleted = contacts
    }

}

class FakeContactsProviderDataSrc : ContactsProviderDataSrcContract {
    override suspend fun all(): List<ContactItem> {
        return listOf(ContactItem(1, "provider", "123"))
    }

}

class FakeContactSynchronizer : ContactSynchronizerContract {
    override suspend fun sync(): ContactSyncResult {
        return ContactSyncResult(
            listOf(ContactItem(1, "new", "123")),
            listOf(ContactItem(1, "modified", "123")),
            listOf(ContactItem(1, "deleted", "123"))
        )
    }

}