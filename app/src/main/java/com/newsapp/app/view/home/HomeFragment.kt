package com.newsapp.app.view.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newsapp.app.BR
import com.newsapp.app.R
import com.newsapp.app.data.model.Category
import com.newsapp.app.data.model.NewsResponse
import com.newsapp.app.databinding.FragmentHomeBinding
import com.newsapp.app.extension.observeLiveData
import com.newsapp.app.utils.CommonUtil
import com.newsapp.app.utils.Logger
import com.newsapp.app.utils.ViewModelProviderFactory
import com.newsapp.app.view.base.BaseFragment
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var newsResponse: NewsResponse
    private lateinit var categoryAdapter: CategoryAdapter
    private val adapter = HeadLinesAdapter()
    private val categoryList = ArrayList<Category>()

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
        context?.let { CommonUtil.showProgressDialog(it, "") }
        /*By Default show General News*/
        homeViewModel.loadData(categoryList.get(0).name)
    }

    private fun setObservers(homeViewModel: HomeViewModel) {
        observeLiveData(homeViewModel.getNewsResponse()) {
            CommonUtil.dismissProgressDialog()
            newsResponse = it
            adapter.submitList(newsResponse.articles)
            recyclerview_headlines.smoothScrollToPosition(0)
        }
    }

    private fun setRecyclerView() {
        categoryAdapter = CategoryAdapter(requireContext(), ::OnCategoryClick)
        recyclerview_category.adapter = categoryAdapter
        val categoryData = getJsonFromAsset("category.json")
        Logger.d("Category", categoryData.toString())

        val gson = Gson()
        val listCategoryType = object : TypeToken<List<String>>() {}.type
        val categoryValue: List<String> = gson.fromJson(categoryData, listCategoryType)

        for (value in categoryValue) {
            val category = Category(value, false)
            categoryList.add(category)
        }

        /* By Default select the General Category*/
        categoryList.get(0).isSelected = true
        categoryAdapter.submitList(categoryList)

        recyclerview_headlines.adapter = adapter
    }

    private fun getJsonFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString =
                requireContext().assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun OnCategoryClick(category: String) {
        context?.let { CommonUtil.showProgressDialog(it, "") }
        homeViewModel.loadData(category)
    }
}
