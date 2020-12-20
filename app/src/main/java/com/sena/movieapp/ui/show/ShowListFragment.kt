package com.sena.movieapp.ui.show

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.observe
import com.sena.movieapp.R
import com.sena.movieapp.application.MainApplication
import com.sena.movieapp.data.model.ShowResponseModel
import com.sena.movieapp.util.RecyclerViewDecoration
import kotlinx.android.synthetic.main.show_list_fragment.*
import javax.inject.Inject

class ShowListFragment : Fragment(), ShowListAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = ShowListFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ShowListViewModel by viewModels(factoryProducer = { factory })

    private val showAdapter = ShowListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_list_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MainApplication).getAppComponent().newFragmentComponent().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpRecyclerView()

        viewModel.topShows.observe(this) {
            showAdapter.submitList(it)
        }
    }

    private fun setUpRecyclerView() {
        showAdapter.setOnItemClickListener(this)
        val margin =
            (resources.getDimension(R.dimen.rv_item_margin) * resources.displayMetrics.density).toInt()
        show_list_rv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = showAdapter
            addItemDecoration(RecyclerViewDecoration(margin))
            setHasFixedSize(true)
        }
    }

    override fun onItemClicked(show: ShowResponseModel) {
        val action =
            ShowListFragmentDirections.actionShowListFragmentToShowDetailFragment(show.id, show.originalName)
        findNavController().navigate(action)
    }

}
