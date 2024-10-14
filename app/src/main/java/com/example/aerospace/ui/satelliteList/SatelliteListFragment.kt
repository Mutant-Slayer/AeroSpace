package com.example.aerospace.ui.satelliteList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aerospace.AeroSpaceApplication
import com.example.aerospace.R
import com.example.aerospace.data.model.SatelliteListResponseItem
import com.example.aerospace.databinding.FragmentSatelliteListBinding
import com.example.aerospace.di.component.DaggerFragmentComponent
import com.example.aerospace.di.module.FragmentModule
import com.example.aerospace.ui.base.UiState
import com.example.aerospace.ui.launchDetails.LaunchDetailsFragment
import javax.inject.Inject

class SatelliteListFragment : Fragment() {

    @Inject
    lateinit var satelliteListViewModel: SatelliteListViewModel

    @Inject
    lateinit var satelliteListAdapter: SatelliteListAdapter

    private lateinit var binding: FragmentSatelliteListBinding

    companion object {
        @JvmStatic
        fun newInstance() = SatelliteListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSatelliteListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservers()
        setUpSearchView()
    }

    private fun setupUi() {
        val recyclerView = binding.rvSatelliteList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.setHasFixedSize(true)

        satelliteListAdapter.setOnClickListener { item ->
            val launchDetailsFragment = LaunchDetailsFragment.newInstance(
                item.mission_name,
                item.launch_date_utc,
                item.rocket.second_stage.payloads[0].payload_id,
                item.rocket.rocket_name,
                item.launch_site.site_name_long
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.flMainContainer, launchDetailsFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = satelliteListAdapter
    }

    private fun setupObservers() {
        satelliteListViewModel.satelliteList.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Error -> {
                    binding.pbProgress.visibility = View.GONE
                    binding.svSearchMission.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.rvSatelliteList.visibility = View.GONE
                }

                UiState.Loading -> {
                    binding.pbProgress.visibility = View.VISIBLE
                    binding.svSearchMission.visibility = View.GONE
                    binding.rvSatelliteList.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.pbProgress.visibility = View.GONE
                    renderList(it.data)
                    binding.svSearchMission.visibility = View.VISIBLE
                    binding.rvSatelliteList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun renderList(articleList: List<SatelliteListResponseItem>) {
        satelliteListAdapter.addData(articleList)
        satelliteListAdapter.notifyDataSetChanged()
    }

    private fun setUpSearchView() {
        binding.svSearchMission.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                satelliteListAdapter.filter(newText.orEmpty())
                return true
            }
        })

        binding.svSearchMission.setOnClickListener {
            binding.svSearchMission.isIconified = false
        }
    }

    private fun injectDependencies() {
        DaggerFragmentComponent.builder()
            .applicationComponent((requireContext().applicationContext as AeroSpaceApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)
    }
}