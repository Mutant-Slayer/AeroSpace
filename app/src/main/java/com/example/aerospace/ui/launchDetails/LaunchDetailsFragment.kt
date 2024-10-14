package com.example.aerospace.ui.launchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aerospace.databinding.FragmentLaunchDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LaunchDetailsFragment : Fragment() {

    private var missionName: String? = null
    private var launchDate: String? = null
    private var rocketDetail: String? = null
    private var payloadDetail: String? = null
    private var launchSite: String? = null

    companion object {

        private const val MISSION_NAME = "mission_name"
        private const val LAUNCH_DATE = "launch_date"
        private const val ROCKET_DETAIL = "rocket_detail"
        private const val PAYLOAD_DETAIL = "payload_detail"
        private const val LAUNCH_SITE = "launch_site"

        @JvmStatic
        fun newInstance(
            missionName: String,
            launchDate: String,
            payloadDetail: String,
            rocketDetail: String,
            launchSite: String
        ) =
            LaunchDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MISSION_NAME, missionName)
                    putString(LAUNCH_DATE, launchDate)
                    putString(ROCKET_DETAIL, rocketDetail)
                    putString(PAYLOAD_DETAIL, payloadDetail)
                    putString(LAUNCH_SITE, launchSite)
                }
            }
    }

    private lateinit var binding: FragmentLaunchDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            missionName = it.getString(MISSION_NAME)
            launchDate = it.getString(LAUNCH_DATE)
            rocketDetail = it.getString(ROCKET_DETAIL)
            payloadDetail = it.getString(PAYLOAD_DETAIL)
            launchSite = it.getString(LAUNCH_SITE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMissionName.text = "Mission Name : $missionName"
        binding.tvLaunchDate.text = "Launch Date : ${formatDate(launchDate.toString())}"
        binding.tvRocketDetails.text = "Rocket Details : $rocketDetail"
        binding.tvLaunchSite.text = "Launch Site : $launchSite"
        binding.tvPayloadDetails.text = "Payload Details : $payloadDetail"
    }

    private fun formatDate(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = inputFormat.parse(timestamp) ?: return ""
        return outputFormat.format(date)
    }
}