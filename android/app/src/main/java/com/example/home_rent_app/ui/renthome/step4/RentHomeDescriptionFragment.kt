package com.example.home_rent_app.ui.renthome.step4

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentRentHomeDescriptionBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.ui.renthome.viewmodel.RentHomeViewModel
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted

class RentHomeDescriptionFragment : Fragment() {

    private val binding: FragmentRentHomeDescriptionBinding by lazy {
        FragmentRentHomeDescriptionBinding.inflate(layoutInflater)
    }

    private val viewModel: RentHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setAddButtonListener()
        setBackButtonListener()
        setHomeOptionObserve()
        setElevatorObserve()
        setBalconyObserve()
        setParkingObserve()
        setSecurityObserve()
        setRentDetailPageStateObserve()
        setContent()
        setThisFloorObserve()
        setMaxFloorObserve()
        setHomIdObserve()
    }

    private fun setHomIdObserve() {
        repeatOnStarted {
            viewModel.homeId.collect {
                logger("homeId : $it")
                if (it != -1) {
                    val intent = Intent(requireContext(), DetailRentActivity::class.java)
                    intent.putExtra("homeId", it)
                    requireActivity().startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setAddButtonListener() {
        binding.btnAdd.setOnClickListener {
            viewModel.addAccountRent()
        }
    }

    private fun setBackButtonListener() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            viewModel.setBackPage()
        }
    }

    private fun setHomeOptionObserve() {
        binding.cgHomeOption.setOnCheckedStateChangeListener { _, checkedIds ->
            val list = checkedIds.map {
                when (it) {
                    R.id.chip_conditioner -> getString(R.string.conditioner)
                    R.id.chip_refrigerator -> getString(R.string.refrigerator)
                    R.id.chip_washer -> getString(R.string.washer)
                    R.id.chip_bed -> getString(R.string.bed)
                    R.id.chip_closet -> getString(R.string.closet)
                    else -> getString(R.string.tv)
                }
            }
            viewModel.setFacilitiesList(list)
            viewModel.setDetailPageState()
        }
    }

    private fun setElevatorObserve() {
        binding.rgElevator.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_elevator_have -> {
                    viewModel.setHasElevator(true)
                }
                else -> {
                    viewModel.setHasElevator(false)
                }
            }
        }
    }

    private fun setBalconyObserve() {
        binding.rgBalcony.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_balcony_have -> {
                    viewModel.setHasBalcony(true)
                }
                else -> {
                    viewModel.setHasBalcony(false)
                }
            }
        }
    }

    private fun setParkingObserve() {
        binding.rgParking.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_parking_have -> {
                    viewModel.setHasParking(true)
                }
                else -> {
                    viewModel.setHasParking(false)
                }
            }
        }
    }

    private fun setSecurityObserve() {
        binding.cgSecurity.setOnCheckedStateChangeListener { _, checkedIds ->
            val list = checkedIds.map {
                when (it) {
                    R.id.chip_cctv -> getString(R.string.cctv)
                    R.id.chip_video -> getString(R.string.video_phone)
                    else -> getString(R.string.entrance)
                }
            }
            viewModel.setSecurity(list)
            viewModel.setDetailPageState()
        }
    }

    private fun setRentDetailPageStateObserve() {
        repeatOnStarted {
            viewModel.rentDetailPageState.collect {
                binding.btnAdd.isEnabled = it
            }
        }
    }

    private fun setContent() {
        repeatOnStarted {
            viewModel.content.collect {
                viewModel.setDetailPageState()
            }
        }
    }

    private fun setThisFloorObserve() {
        repeatOnStarted {
            viewModel.thisFloor.collect {
                logger("thisFloor : $it")
                viewModel.setDetailPageState()
            }
        }
    }

    private fun setMaxFloorObserve() {
        repeatOnStarted {
            viewModel.maxFloor.collect {
                logger("maxFloor : $it")
                viewModel.setDetailPageState()
            }
        }
    }
}
