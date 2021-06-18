package com.contacts.app.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.contacts.app.R
import kotlinx.android.synthetic.main.item_contact.view.*
import app.common.data.model.ContactItem
import app.common.presentation.ui.adapter.BasePagedListAdapter
import app.common.presentation.ui.adapter.BaseViewHolder
import app.common.presentation.ui.view.ViewInterface

/**
 * Created by Sha on 4/20/17.
 */

class ContactsAdapter(baseView: ViewInterface) : BasePagedListAdapter<ContactItem>(
        baseView,
        object : DiffUtil.ItemCallback<ContactItem>() {
            override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
                return oldItem == newItem
            }
        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ContactItem> {
        return Vh(parent)
    }

    inner class Vh internal constructor(viewGroup: ViewGroup)
        : BaseViewHolder<ContactItem>(viewGroup, R.layout.item_contact) {

        override fun bindView(item: ContactItem) {
            itemView.tvName.text = item.name
            itemView.tvPhone.text = item.phone
        }
    }
}
