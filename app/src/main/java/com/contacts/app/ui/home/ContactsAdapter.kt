package com.contacts.app.ui.home

import android.view.ViewGroup
import android.widget.TextView
import app.common.data.model.ContactItem
import app.common.presentation.ui.adapter.BaseRecyclerAdapter
import app.common.presentation.ui.adapter.BaseViewHolder
import app.common.presentation.ui.view.ViewInterface
import com.contacts.app.R

/**
 * Created by Sha on 4/20/17.
 */

class ContactsAdapter(baseView: ViewInterface) :
    BaseRecyclerAdapter<ContactItem, ContactsAdapter.Vh>(
        emptyList(), baseView
    ) {

    override fun getViewHolder(viewGroup: ViewGroup, viewType: Int): Vh {
        return Vh(viewGroup)
    }

    inner class Vh internal constructor(viewGroup: ViewGroup) :
        BaseViewHolder<ContactItem>(viewGroup, R.layout.item_contact) {

        override fun bindView(item: ContactItem) {
            itemView.findViewById<TextView>(R.id.tvName).text = item.name
            itemView.findViewById<TextView>(R.id.tvPhone).text = item.name
        }
    }

}
