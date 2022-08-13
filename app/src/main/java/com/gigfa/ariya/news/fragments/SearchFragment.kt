package com.gigfa.ariya.news.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigfa.ariya.news.R
import com.gigfa.ariya.news.adapters.SearchsAdapter
import com.gigfa.ariya.news.adapters.TrendsAdapter
import com.gigfa.ariya.news.dataLayer.ApiClient
import com.gigfa.ariya.news.dataLayer.ApiInterface
import com.gigfa.ariya.news.model.ModelSearchResponse
import com.gigfa.ariya.news.model.ModelTrendsResponse
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_trends.*
import kotlinx.android.synthetic.main.popup_window.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var searchAdapter: SearchsAdapter? = null

    private lateinit var searchs: ModelSearchResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshServices()

        search_bar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                progressBarVisibility(true)
                searchsLoad()
                return@OnEditorActionListener true
            }
            false
        })


    }

    private fun refreshServices() {
        search_swipe_refresh.setOnRefreshListener {
            progressBarVisibility(true)
            searchsLoad()
            search_swipe_refresh.isRefreshing = false
        }
    }

    private fun progressBarVisibility(isShown: Boolean) {
        val serviceProgressBar = view?.findViewById<View>(R.id.search_progressBar)
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

    private fun searchsLoad() {

        val searchText = search_bar.text.toString()

        ApiClient.getClient().create(ApiInterface::class.java)
            .getSearch(
                "e7893ec0bd394ae5b9fd38313ba36346", searchText, "relevancy"
            )
            .enqueue(object : Callback<ModelSearchResponse> {
                override fun onResponse(
                    call: Call<ModelSearchResponse>,
                    response: Response<ModelSearchResponse>,
                ) {
                    if (response.isSuccessful) {
                        searchsSetState(true, response)
                    } else {
                        searchsSetState(false)
                    }
                }

                override fun onFailure(call: Call<ModelSearchResponse>, t: Throwable) {
                    searchsSetState(false)
                }

            })

    }

    private fun searchsSetState(
        isSuccess: Boolean,
        response: Response<ModelSearchResponse>? = null,
    ) {
        if (isSuccess) {

            searchs = response?.body()!!
            searchAdapter = SearchsAdapter(requireContext())
            searchAdapter!!.setData(searchs.articles as ArrayList<ModelSearchResponse.Article>)
            searchsInitRecycler()

        } else {
            val toastText = "Please check your internet connection then try again."
            showMessageBox(toastText)
            //Toast.makeText(this, toastText, Toast.LENGTH_LONG).show()
        }
    }

    private fun searchsInitRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.search_RecyclerView)
        if (recyclerView != null) {
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = searchAdapter
        }
        if (recyclerView != null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        progressBarVisibility(false)

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



