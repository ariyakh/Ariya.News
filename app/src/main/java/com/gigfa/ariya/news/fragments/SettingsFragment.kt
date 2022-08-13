package com.gigfa.ariya.news.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.gigfa.ariya.news.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val values = arrayOf("All", "us", "sa", "fr", "jp", "at", "au")
    private val category = arrayOf(
        "All",
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology"
    )
    private val sources = arrayOf(
        "All",
        "abc-news",
        "bbc-news",
        "bbc-sport",
        "bbc-sport",
        "cbc-news",
        "cnn",
        "ox-news",
        "google-news",
        "national-geographic",
        "new-york-magazine",
        "ars-technica"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerStuff()

    }

    private fun spinnerStuff() {
        countrySpinnerStuff()
        categorySpinnerStuff()
        sourcesSpinnerStuff()
    }

    private fun sourcesSpinnerStuff() {
        val spinner = setting_sources_spinner

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sources)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {

                val selectedItemValue = sources[position]

                val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
                val sEdit: SharedPreferences.Editor = shPref.edit()
                sEdit.putString("sources", selectedItemValue)
                sEdit.apply()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                // TODO
            }

        }


        val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val sourcesValue = shPref.getString("sources", "All")!!

        val spinnerPosition = (spinner.adapter as ArrayAdapter<String>).getPosition(sourcesValue)
        spinner.setSelection(spinnerPosition)

    }

    private fun countrySpinnerStuff() {
        val spinner = setting_country_spinner

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, values)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {

                val selectedItemValue = values[position]

                val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
                val sEdit: SharedPreferences.Editor = shPref.edit()
                sEdit.putString("country", selectedItemValue)
                sEdit.apply()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                // TODO
            }

        }


        val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val countryValue = shPref.getString("country", "All")!!

        val spinnerPosition = (spinner.adapter as ArrayAdapter<String>).getPosition(countryValue)
        spinner.setSelection(spinnerPosition)

    }



    private fun categorySpinnerStuff() {
        val categorySpinner = setting_category_spinner

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, category)
        categorySpinner.adapter = arrayAdapter
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {

                val selectedItemValue = category[position]

                val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
                val sEdit: SharedPreferences.Editor = shPref.edit()
                sEdit.putString("category", selectedItemValue)
                sEdit.apply()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                // TODO
            }

        }


        val shPref = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val countryValue = shPref.getString("category", "All")!!

        val spinnerPosition =
            (categorySpinner.adapter as ArrayAdapter<String>).getPosition(countryValue)
        categorySpinner.setSelection(spinnerPosition)

    }

}