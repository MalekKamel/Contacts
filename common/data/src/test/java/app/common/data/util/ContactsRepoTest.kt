package app.common.data.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.common.data.domain.contacts.ContactsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
class ContactsRepoTest {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var repo: ContactsRepo

}