package com.example.home_rent_app.ui.wanthome.step1

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeFirstStepBinding
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import com.example.home_rent_app.util.RangeValidator
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class WantHomeFirstStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeFirstStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_want_home_first_step,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController = findNavController()
        goBack()
        showDatePicker()
        goSecondStep(navigationController)
    }

    private fun goSecondStep(navController: NavController) {
        binding.btnGoSecondStep.setOnClickListener {
            navController.navigate(
                R.id.action_wantHomeFirstStepFragment_to_wantHomeSecondStepFragment
            )
        }
    }

    private fun goBack() {
        binding.btnClose.setOnClickListener {
            val activity = activity as WantHomeActivity
            activity.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePicker() {
        binding.btnGoIn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(limitRange().build())
                .setTitleText("입주 희망일")
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener {
                val date =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                binding.btnGoIn.text = date
            }
        }
        binding.btnGoOut.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(limitRange().build())
                .setTitleText("퇴거 희망일")
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener {
                val date =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                binding.btnGoOut.text = date
            }
        }
    }

    // 오늘 날짜 이전은 선택할 수 없도록 설정
    @RequiresApi(Build.VERSION_CODES.O)
    private fun limitRange(): CalendarConstraints.Builder {

        val constraintsBuilderRange = CalendarConstraints.Builder()

        val calendarStart: Calendar = GregorianCalendar.getInstance()
        val calendarEnd: Calendar = GregorianCalendar.getInstance()

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)
        val currentDate = formatted.split("-")

        calendarStart.set(
            currentDate[0].toInt(),
            currentDate[1].toInt() - 1,
            currentDate[2].toInt() - 1
        )
        calendarEnd.set(2999, 12, 31)

        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)

        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))

        return constraintsBuilderRange
    }
}
