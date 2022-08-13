package com.gigfa.ariya.news.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigfa.ariya.news.R
import com.gigfa.ariya.news.model.ModelSearchResponse
import com.gigfa.ariya.news.model.ModelTrendsResponse
import com.gigfa.ariya.news.ui.SingleNews


class SearchsAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchsAdapter.ViewHolder>() {

    private var searchList = arrayListOf<ModelSearchResponse.Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<ModelSearchResponse.Article>) {
        searchList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trends, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchs = searchList[position]


        Glide.with(context)
            .load(searchs.urlToImage)
            .into(holder.trendImage)

        holder.trendTitle.text = searchs.title
        holder.trendSource.text = searchs.source.name
        holder.trendAuthor.text = "Author : " + searchs.author
        holder.trendPublishedTime.text = "Published : " + searchs.publishedAt

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleNews::class.java)
            intent.putExtra("trendImage", searchs.urlToImage)
            intent.putExtra("trendTitle", searchs.title)
            intent.putExtra("trendSource", searchs.source.name)
            intent.putExtra("trendContent", searchs.content)
            intent.putExtra("trendTime", searchs.publishedAt)
            intent.putExtra("trendAuthor", searchs.author)
            intent.putExtra("trendLink", searchs.url)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val trendTitle: TextView = itemView.findViewById(R.id.trending_item_title)
        val trendSource: TextView = itemView.findViewById(R.id.trending_item_source)
        val trendAuthor: TextView = itemView.findViewById(R.id.trending_item_author)
        val trendPublishedTime: TextView = itemView.findViewById(R.id.trending_item_time)
        val trendImage: ImageView = itemView.findViewById(R.id.trending_item_gif_picture)


    }

}