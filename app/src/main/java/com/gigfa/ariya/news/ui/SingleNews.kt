package com.gigfa.ariya.news.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gigfa.ariya.news.R
import kotlinx.android.synthetic.main.activity_single_news.*

class SingleNews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news)

        val newsImage = intent.getStringExtra("trendImage")
        val newsTitle: String? = intent.getStringExtra("trendTitle")
        val trendSource: String? = intent.getStringExtra("trendSource")
        val trendContent: String? = intent.getStringExtra("trendContent")
        val trendTime: String? = intent.getStringExtra("trendTime")
        val trendAuthor: String? = intent.getStringExtra("trendAuthor")
        val trendLink: String? = intent.getStringExtra("trendLink")

        Glide.with(this)
            .load(newsImage)
            .into(single_news_Image)
        single_news_title.text = newsTitle
        single_news_source.text = trendSource
        single_news_content.text = trendContent
        single_news_time.text = trendTime
        single_news_author.text = trendAuthor

        single_news_read_more_btn.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trendLink))
            startActivity(browserIntent)
        }


    }
}