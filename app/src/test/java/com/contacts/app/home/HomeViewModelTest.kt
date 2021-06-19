package com.contacts.app.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.common.data.DataManager
import app.common.data.util.MainCoroutineRule
import app.common.data.util.getOrAwaitValue
import com.contacts.app.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var vm: HomeViewModel

    @Before
    fun setup() {
        val dm = DataManager(FakeContactsRepo())
        vm = HomeViewModel(dm)
    }

    @Test
    fun loadContacts() {
        vm.loadContacts()

        val value = vm.contacts.getOrAwaitValue()
        assertThat(
            value[0],
            not(nullValue())
        )
        assertThat(
            value[0].name,
            equalTo("name")
        )
    }

    @Test
    fun syncContacts() {
        vm.syncContacts()

        val value = vm.onSync.getOrAwaitValue()
        assertThat(
            value,
            equalTo(true)
        )
    }

}
