package com.example.home_rent_app.ui.wanthome.step1

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeFirstStepBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeViewModel
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import com.example.home_rent_app.util.RangeValidator
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

@AndroidEntryPoint
class WantHomeFirstStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeFirstStepBinding
    private val viewModel: WantHomeViewModel by activityViewModels()
    private var goInFlag = false
    private var goOutFlag = false
    private var depositFlag = false
    private var monthlyPayFlag = false

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
        binding.btnGoSecondStep.isEnabled = false
        val navigationController = findNavController()
        goBack()
        showDatePicker()
        goSecondStep(navigationController)
        binding.etGoIn.addTextChangedListener(goInListener)
        binding.etGoOut.addTextChangedListener(goOutListener)
        binding.etDeposit.addTextChangedListener(depositListener)
        binding.etMonthlyRent.addTextChangedListener(monthlyPayListener)
    }

    private fun goSecondStep(navController: NavController) {
        binding.btnGoSecondStep.setOnClickListener {
            setRangeAtViewModel()
            setDepositAtViewModel()
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
        binding.etGoIn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(limitRange().build())
                .setTitleText("입주 희망일")
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener {
                val date =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                binding.etGoIn.setText(date)
            }
        }
        binding.etGoOut.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(limitRange().build())
                .setTitleText("퇴거 희망일")
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener {
                val date =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                binding.etGoOut.setText(date)
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

    private fun setRangeAtViewModel() {
        val goIn = binding.etGoIn.text?.toString() ?: ""
        val goOut = binding.etGoOut.text?.toString() ?: ""
        val list = listOf(goIn, goOut)
        viewModel.setRange(list)
    }

    private fun setDepositAtViewModel() {
        val deposit = binding.etDeposit.text?.toString()?.toInt() ?: 0
        val monthlyRent = binding.etMonthlyRent.text?.toString()?.toInt() ?: 0
        val list = listOf(deposit, monthlyRent)
        viewModel.setDeposit(list)
    }

    private val goInListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                goInFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private val goOutListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                goOutFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private val depositListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                depositFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private val monthlyPayListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                monthlyPayFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private fun flagCheck() {
        binding.btnGoSecondStep.isEnabled = goInFlag && goOutFlag && depositFlag && monthlyPayFlag
    }
}
