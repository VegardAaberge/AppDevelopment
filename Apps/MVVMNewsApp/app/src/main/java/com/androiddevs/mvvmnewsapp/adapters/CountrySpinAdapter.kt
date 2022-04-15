package com.androiddevs.mvvmnewsapp.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.androiddevs.mvvmnewsapp.models.Country

class CountrySpinAdapter(context: Context, resource: Int, countries: List<Country>) : ArrayAdapter<Country>(context, resource, countries) {

    var countries = countries

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getView(position, convertView, parent) as TextView
        textView.setText(countries[position].countryName)
        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getDropDownView(position, convertView, parent) as TextView
        textView.setText(countries[position].countryName)
        return textView
    }
}