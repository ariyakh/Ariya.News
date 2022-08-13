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
import com.gigfa.ariya.news.model.ModelTrendsResponse
import com.gigfa.ariya.news.ui.SingleNews


class TrendsAdapter(private val context: Context) :
    RecyclerView.Adapter<TrendsAdapter.ViewHolder>() {

    private var trendsList = arrayListOf<ModelTrendsResponse.Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<ModelTrendsResponse.Article>) {
        trendsList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trends, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trends = trendsList[position]


        Glide.with(context)
            .load(trends.urlToImage)
            .into(holder.trendImage)

        holder.trendTitle.text = trends.title
        holder.trendSource.text = trends.source.name
        holder.trendAuthor.text = "Author : " + trends.author
        holder.trendPublishedTime.text = "Published : " + trends.publishedAt

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleNews::class.java)
            intent.putExtra("trendImage", trends.urlToImage)
            intent.putExtra("trendTitle", trends.title)
            intent.putExtra("trendSource", trends.source.name)
            intent.putExtra("trendContent", trends.content)
            intent.putExtra("trendTime", trends.publishedAt)
            intent.putExtra("trendAuthor", trends.author)
            intent.putExtra("trendLink", trends.url)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return trendsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val trendTitle: TextView = itemView.findViewById(R.id.trending_item_title)
        val trendSource: TextView = itemView.findViewById(R.id.trending_item_source)
        val trendAuthor: TextView = itemView.findViewById(R.id.trending_item_author)
        val trendPublishedTime: TextView = itemView.findViewById(R.id.trending_item_time)
        val trendImage: ImageView = itemView.findViewById(R.id.trending_item_gif_picture)


    }

}