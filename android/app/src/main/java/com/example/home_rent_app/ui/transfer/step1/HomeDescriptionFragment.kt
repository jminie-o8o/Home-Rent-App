package com.example.home_rent_app.ui.transfer.step1

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentHomeDescriptionBinding
import com.example.home_rent_app.ui.viewmodel.TransferViewModel
import com.example.home_rent_app.util.MoneyFormat
import com.example.home_rent_app.util.RentType
import com.example.home_rent_app.util.repeatOnStarted
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import hirondelle.date4j.DateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.TimeZone

@AndroidEntryPoint
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

        setRentTypeObserver()
        setStartDateClickListener()
        setEndDateClickListener()
        setIsCorrectDateObserver()
        viewModel.setHomeDescriptionState()
        setTitleObserver()
        setDepositObserver()
        setMaintenanceObserver()
        setMaintenanceDescriptionObserver()
        setMonthlyObserver()
        setStartDateObserver()
        setEndDateObserver()
        setHomeDescriptionStateObserver()
        setNextButtonClickListener()
        setMonthlyTextWatcher()
        setDepositTextWatcher()
        setMaintenanceTextWatcher()
        setBackClick()
    }

    private fun setMonthlyTextWatcher() {
        binding.petMonthly.addTextChangedListener(MoneyFormat(binding.petMonthly))
    }

    private fun setMaintenanceTextWatcher() {
        binding.petMaintenance.addTextChangedListener(MoneyFormat(binding.petMaintenance))
    }

    private fun setDepositTextWatcher() {
        binding.petDeposit.addTextChangedListener(MoneyFormat(binding.petDeposit))
    }

    // 전월세 구분
    private fun setRentTypeObserver() {
        repeatOnStarted {
            viewModel.rentType.collect {
                when (it) {
                    RentType.MONTHLY -> {
                        setMonthlyVisible()
                    }
                    RentType.JEONSE -> {
                        setMonthlyGone()
                    }
                }
            }
        }
    }

    // 월세 선택시 월세 보이기
    private fun setMonthlyVisible() {
        binding.apply {
            tvMonthlyLabel.visibility = View.VISIBLE
            otfMonthly.visibility = View.VISIBLE
        }
    }

    // 월세 가리기
    private fun setMonthlyGone() {
        binding.apply {
            tvMonthlyLabel.visibility = View.GONE
            otfMonthly.visibility = View.GONE
        }
    }

    // 입주일 클릭 시 달력
    private fun setStartDateClickListener() {
        binding.tietStartDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("입주 가능일")
                    .build()

            datePicker.show(childFragmentManager, "calendar")

            datePicker.addOnPositiveButtonClickListener {
                val select = requireNotNull(datePicker.selection)
                val dateTime = DateTime.forInstant(select, TimeZone.getTimeZone("Asia/Seoul"))
                val dateFormat = dateTime.format("YYYY-MM-DD")
                binding.tietStartDate.setText(dateFormat)

                CoroutineScope(Job()).launch {
                    viewModel.checkCorrectDate()
                }
            }
        }
    }

    // 만료일 클릭시 달력 뜨게하기
    private fun setEndDateClickListener() {
        binding.tietEndDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("계약 만료일")
                    .build()

            datePicker.show(childFragmentManager, "calendar")

            datePicker.addOnPositiveButtonClickListener {
                val select = requireNotNull(datePicker.selection)
                val dateTime = DateTime.forInstant(select, TimeZone.getTimeZone("Asia/Seoul"))
                val dateFormat = dateTime.format("YYYY-MM-DD")
                binding.tietEndDate.setText(dateFormat)

                CoroutineScope(Job()).launch {
                    viewModel.checkCorrectDate()
                }
            }
        }
    }

    // 입주일보다 만료일이 늦은지 계산
    private fun setIsCorrectDateObserver() {
        // 입주일이 크면 메세지 띄우고 리셋
        repeatOnStarted {
            viewModel.isCorrectDate.collect {
                if (!it) {
                    Toast.makeText(
                        binding.root.context,
                        "올바른 날짜를 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.tietStartDate.setText("")
                    binding.tietEndDate.setText("")
                }
            }
        }
    }

    // 제목 입력
    private fun setTitleObserver() {
        repeatOnStarted {
            viewModel.title.collect {
                viewModel.setHomeDescriptionState()
                binding.tvTitleLength.text = getString(R.string.title_length, it.length)
            }
        }
    }

    // 보증금 입력
    private fun setDepositObserver() {
        repeatOnStarted {
            viewModel.deposit.collect {
                viewModel.setHomeDescriptionState()
            }
        }
    }

    // 관리비 입력
    private fun setMaintenanceObserver() {
        repeatOnStarted {
            viewModel.maintenance.collect {
                viewModel.setHomeDescriptionState()
            }
        }
    }

    // 관리비 설명
    private fun setMaintenanceDescriptionObserver() {
        repeatOnStarted {
            viewModel.maintenanceDescription.collect {
                viewModel.setHomeDescriptionState()
            }
        }
    }

    // 월세 입력
    private fun setMonthlyObserver() {
        repeatOnStarted {
            viewModel.monthly.collect {
                viewModel.setHomeDescriptionState()
            }
        }
    }

    // 입주 일 입력
    private fun setStartDateObserver() {
        repeatOnStarted {
            viewModel.startDate.collect {
                viewModel.setHomeDescriptionState()
            }
        }
    }

    // 계약 만료일 입력
    private fun setEndDateObserver() {
        repeatOnStarted {
            viewModel.endDate.collect {
                viewModel.setHomeDescriptionState()
            }
        }
    }

    // 모든 내용이 입력되었는지 확인
    private fun setHomeDescriptionStateObserver() {
        repeatOnStarted {
            viewModel.homeDescriptionState.collect {
                if (it) {
                    binding.btnNext.apply {
                        isEnabled = true
                        backgroundTintList =
                            ColorStateList.valueOf(
                                binding.root.context.getColor(
                                    R.color.purple_200
                                )
                            )
                    }
                }
            }
        }
    }

    private fun setNextButtonClickListener() {
        binding.btnNext.setOnClickListener {
            // 다음 화면 이동 구현
            findNavController().navigate(R.id.action_homeDescriptionFragment_to_picChoiceFragment)
        }
    }

    private fun setBackClick() {
        binding.btnPre.setOnClickListener {
            requireActivity().finish()
        }
    }
}
