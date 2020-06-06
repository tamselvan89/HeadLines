package com.newsapp.app.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newsapp.app.data.model.Country
import com.newsapp.app.databinding.DialogCountryBinding
import com.newsapp.app.utils.CommonUtil
import com.newsapp.app.utils.Logger
import com.newsapp.app.utils.ViewModelProviderFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_country.*
import okhttp3.internal.notify
import javax.inject.Inject

class CountryDialog : BottomSheetDialogFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var binding: DialogCountryBinding? = null
    private lateinit var adapter: CountryAdapter
    private var countryList = mutableListOf<Country>()
    private var btnSort = false

    @Inject
    internal lateinit var factory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCountryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.executePendingBindings()
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        adapter = CountryAdapter(requireContext(), ::OnCountryClick)
        recyclerview_country.adapter = adapter
        val countryData = CommonUtil.getJsonFromAsset(requireContext(), "country.json")
        Logger.d("Country", countryData.toString())

        val gson = Gson()
        val listCategoryType = object : TypeToken<List<Country>>() {}.type
        countryList = gson.fromJson(countryData, listCategoryType)

        countryList.sortBy { country -> country.name }
        adapter.submitList(countryList)

        btn_sort.setOnClickListener {
            when (btnSort) {
                true -> {
                    countryList.sortByDescending { country -> country.name }
                }
                false -> {
                    countryList.sortBy { country -> country.name }
                }
            }
            btnSort = !btnSort
            adapter.submitList(countryList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun OnCountryClick(country: String) {
        homeViewModel.setCountry(country)
        dismiss()
    }
}