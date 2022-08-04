package com.example.home_rent_app.ui.transfer.step1

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.home_rent_app.databinding.FragmentHomeDescriptionBinding
import com.example.home_rent_app.ui.transfer.TransferViewModel
import com.example.home_rent_app.util.RentType
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import com.google.android.material.datepicker.MaterialDatePicker
import hirondelle.date4j.DateTime
import java.time.LocalDateTime
import java.util.*
import kotlin.math.log


class HomeDescriptionFragment : Fragment() {

    private val binding: FragmentHomeDescriptionBinding by lazy {
        FragmentHomeDescriptionBinding.inflate(layoutInflater)
    }

    private val viewModel: TransferViewModel by activityViewModels()

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

        repeatOnStarted {
            viewModel.roomType.collect {
                logger("room ${it}")
            }
        }

        repeatOnStarted {
            viewModel.rentType.collect {
                when (it) {
                    RentType.MONTHLY -> {
                        binding.apply {
                            tvMonthlyLabel.visibility = View.VISIBLE
                            tvMonthlyPriceMeasure.visibility = View.VISIBLE
                            otfMonthly.visibility = View.VISIBLE
                        }
                    }
                    RentType.JEONSE -> {
                        binding.apply {
                            tvMonthlyLabel.visibility = View.GONE
                            tvMonthlyPriceMeasure.visibility = View.GONE
                            otfMonthly.visibility = View.GONE
                        }
                    }
                }

            }
        }

        binding.tietStartDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            datePicker.show(childFragmentManager, "calendar")
            datePicker.addOnPositiveButtonClickListener {
                val select = requireNotNull(datePicker.selection)
                val dateTime = DateTime.forInstant(select, TimeZone.getTimeZone("Asia/Seoul"))
                val dateFormat = dateTime.format("YYYY-MM-DD")
                binding.tietStartDate.setText(dateFormat)
            }
        }
        binding.tietEndDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            datePicker.show(childFragmentManager, "calendar")
            datePicker.addOnPositiveButtonClickListener {
                val select = requireNotNull(datePicker.selection)
                val dateTime = DateTime.forInstant(select, TimeZone.getTimeZone("Asia/Seoul"))
                val dateFormat = dateTime.format("YYYY-MM-DD")
                binding.tietEndDate.setText(dateFormat)
            }
        }
    }

}