package org.youngmonkeys.example.push_message.data

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tvd12.ezyfoxserver.client.entity.EzyObject
import org.youngmonkeys.example.push_message.R

class MessageListAdapter(
    context: Context
): ArrayAdapter<EzyObject>(context, R.layout.message_item) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.message_item, parent,false);
        val title = view.findViewById<TextView>(R.id.title)
        val subtitle = view.findViewById<TextView>(R.id.subtitle)
        val data = getItem(position)!!
        title.text = data.get("title", String::class.java)
        subtitle.text = data.get("content", String::class.java)
        return view
    }

}