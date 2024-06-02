package com.example.newsapiapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapplication.R
import com.example.newsapiapplication.models.TopHeadline
import com.facebook.drawee.view.SimpleDraweeView

class TopHeadlinesAdapter(private val data: List<TopHeadline>) :
    RecyclerView.Adapter<TopHeadlinesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val imageView: SimpleDraweeView = itemView.findViewById(R.id.image)
        val author: TextView = itemView.findViewById(R.id.author)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_headlines, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.titleTextView.text = item.title
        holder.imageView.setImageURI(item.urlToImage)
        holder.author.text = item.author
        holder.createdAt.text = item.publishedAt
    }

    override fun getItemCount(): Int = data.size
}