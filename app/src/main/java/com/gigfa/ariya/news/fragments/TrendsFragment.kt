package com.gigfa.ariya.news.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.load.model.Model
import com.gigfa.ariya.news.R
import com.gigfa.ariya.news.adapters.TrendsAdapter
import com.gigfa.ariya.news.dataLayer.ApiClient
import com.gigfa.ariya.news.dataLayer.ApiInterface
import com.gigfa.ariya.news.model.ModelTrendsResponse
import kotlinx.android.synthetic.main.fragment_trends.*
import kotlinx.android.synthetic.main.popup_window.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendsFragment : Fragment() {

    private var trendAdapter: TrendsAdapter? = null

    private lateinit var trends: ModelTrendsResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        trendsLoad()


        return inflater.inflate(R.layout.fragment_trends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshServices()


    }

    private fun refreshServices() {
        trends_swipe_refresh.setOnRefreshListener {
            progressBarVisibility(true)
            trendsLoad()
            trends_swipe_refresh.isRefreshing = false
        }
    }

    private fun trendsLoad() {

        val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        var countryValue = shPref.getString("country", "")!!
        var categoryValue = shPref.getString("category", "")!!
        var sourcesValue = shPref.getString("sources", "")!!
        if (categoryValue == "All") {
            categoryValue = ""
        }
        if (sourcesValue == "All") {
            sourcesValue = ""
        }
        if (countryValue == "All") {
            countryValue = ""
        }



        if (categoryValue != "" && sourcesValue != "") {
            showMessageBox("You cannot mix the sources parameter with the country or category parameters.")
        } else if (countryValue != "" && sourcesValue != "") {
            showMessageBox("You cannot mix the sources parameter with the country or category parameters.")
        } else {
            ApiClient.getClient().create(ApiInterface::class.java)
                .getTrends(
                    "e7893ec0bd394ae5b9fd38313ba36346",
                    countryValue,
                    categoryValue,
                    sourcesValue
                )
                .enqueue(object : Callback<ModelTrendsResponse> {
                    override fun onResponse(
                        call: Call<ModelTrendsResponse>,
                        response: Response<ModelTrendsResponse>,
                    ) {
                        if (response.isSuccessful) {
                            trendsSetState(true, response)
                        } else {
                            trendsSetState(false)
                        }
                    }

                    override fun onFailure(call: Call<ModelTrendsResponse>, t: Throwable) {
                        trendsSetState(false)
                    }

                })

        }

    }

    private fun trendsSetState(
        isSuccess: Boolean,
        response: Response<ModelTrendsResponse>? = null,
    ) {
        if (isSuccess) {

            trends = response?.body()!!
            trendAdapter = TrendsAdapter(requireContext())
            trendAdapter!!.setData(trends.articles as ArrayList<ModelTrendsResponse.Article>)
            trendsInitRecycler()

        } else {
            val toastText = "Please check your internet connection then try again."
            showMessageBox(toastText)
            //Toast.makeText(this, toastText, Toast.LENGTH_LONG).show()
        }
    }

    private fun trendsInitRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.trends_RecyclerView)
        if (recyclerView != null) {
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = trendAdapter
        }
        if (recyclerView != null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        progressBarVisibility(false)

    }

    private fun progressBarVisibility(isShown: Boolean) {
        val serviceProgressBar = view?.findViewById<View>(R.id.trends_progressBar)
        if (isShown) {
            if (serviceProgressBar != null) {
                serviceProgressBar.visibility = View.VISIBLE
            }
        } else {
            if (serviceProgressBar != null) {
                serviceProgressBar.visibility = View.GONE
            }
        }
    }

    private fun showMessageBox(text: String) {
        val messageBoxView =
            LayoutInflater.from(requireContext()).inflate(R.layout.popup_window, null)
        val messageBoxBuilder = AlertDialog.Builder(requireContext()).setView(messageBoxView)
        messageBoxView.popUpMenuText.text = text
        val messageBoxInstance = messageBoxBuilder.show()
        messageBoxInstance.window?.setBackgroundDrawableResource(R.drawable.popup_menu_background)
        messageBoxView.popUpMenuButton.setOnClickListener {
            messageBoxInstance.dismiss()
        }
    }


}