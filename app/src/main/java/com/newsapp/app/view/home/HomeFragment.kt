package com.newsapp.app.view.home

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newsapp.app.BR
import com.newsapp.app.R
import com.newsapp.app.data.model.Category
import com.newsapp.app.data.model.NewsResponse
import com.newsapp.app.databinding.FragmentHomeBinding
import com.newsapp.app.utils.CommonUtil
import com.newsapp.app.utils.CommonUtil.getJsonFromAsset
import com.newsapp.app.utils.Logger
import com.newsapp.app.utils.ViewModelProviderFactory
import com.newsapp.app.view.base.BaseFragment
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var adapter: HeadLinesAdapter
    private val categoryList = mutableListOf<Category>()

    @Inject
    internal lateinit var androidInJector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var factory: ViewModelProviderFactory

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): HomeViewModel {
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        return homeViewModel
    }

    override fun setUp() {
        setRecyclerView()
        setObservers(homeViewModel)
        btn_country.text = homeViewModel.getCountry()

        homeViewModel.loadData()

        getViewDataBinding().btnCountry.setOnClickListener {
            CountryDialog().show(parentFragmentManager, "Country")
        }
    }

    private fun OnItemClick(url: String) {
        val uris = Uri.parse(url)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intents)
    }

    private fun setObservers(homeViewModel: HomeViewModel) {
        homeViewModel.viewState.observe(viewLifecycleOwner, Observer { homeViewState ->
            when (homeViewState) {
                is HomeViewState.Error -> {
                    CommonUtil.dismissProgressDialog()
                    showSnackBarErrMessage(homeViewState.message)
                }
                is HomeViewState.Success -> {
                    CommonUtil.dismissProgressDialog()
                    val response: NewsResponse = homeViewState.response
                    adapter.submitList(response.articles)
                    recyclerview_headlines.smoothScrollToPosition(0)
                }
                is HomeViewState.ChangedCountry -> {
                    btn_country.text = homeViewState.country
                }
            }
        })

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            when (isLoading) {
                true -> {
                    CommonUtil.showProgressDialog(requireContext(), "")
                }
                false -> {
                    CommonUtil.dismissProgressDialog()
                }
            }
        })
    }

    private fun setRecyclerView() {
        adapter = HeadLinesAdapter(::OnItemClick)
        categoryAdapter = CategoryAdapter(requireContext(), ::OnCategoryClick)
        recyclerview_category.adapter = categoryAdapter
        val categoryData = getJsonFromAsset(requireContext(), "category.json")
        Logger.d("Category", categoryData.toString())

        val gson = Gson()
        val listCategoryType = object : TypeToken<List<String>>() {}.type
        val categoryValue: List<String> = gson.fromJson(categoryData, listCategoryType)

        for (value in categoryValue) {
            val category = Category(value, false)
            if (value.equals(homeViewModel.getCategory(), true)) {
                category.isSelected = true
            }
            categoryList.add(category)
        }
        categoryAdapter.submitList(categoryList)

        recyclerview_headlines.adapter = adapter
    }

    private fun OnCategoryClick(category: String) {
        homeViewModel.setCategory(category)
    }
}
