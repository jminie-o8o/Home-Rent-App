package com.example.home_rent_app.ui.wanthome.step2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.home_rent_app.R
import com.example.home_rent_app.data.session.ItemIdSession
import com.example.home_rent_app.data.session.UserSession
import com.example.home_rent_app.databinding.FragmentWantHomeSecondStepBinding
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.ui.dialogfragment.CancelDialogFragment
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import com.example.home_rent_app.ui.wanthome.detail.WantHomeDetailActivity
import com.example.home_rent_app.ui.wanthome.viewmodel.WantHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WantHomeSecondStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeSecondStepBinding
    private val viewModel: WantHomeViewModel by activityViewModels()
    private var detailAddressFlag = false
    private var titleFlag = false
    private var contentsFlag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_want_home_second_step,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.isEnabled = false
        goHomeActivity()
        register()
        setDetailLocation()
        setTitle()
        setDetailContents()
        binding.etDetailAddressSecondStep.addTextChangedListener(detailAddressListener)
        binding.etTitleSecondStep.addTextChangedListener(titleListener)
        binding.etDetailSecondStep.addTextChangedListener(contentsListener)
    }

    private fun goHomeActivity() {
        binding.btnClose.setOnClickListener {
            CancelDialogFragment().show(parentFragmentManager, "cancelDialog")
        }
    }

    private fun register() {
        binding.btnRegister.setOnClickListener {
            lifecycleScope.launch {
                val activity = activity as WantHomeActivity
                activity.clickRegister(viewModel.addWantHome())
            }
        }
    }

    private fun setDetailLocation() {
        binding.etDetailAddressSecondStep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val detailAddress = binding.etDetailAddressSecondStep.text?.toString() ?: ""
                viewModel.setDetailAddress(detailAddress)
            }
        })
    }

    private fun setTitle() {
        binding.etTitleSecondStep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val title = binding.etTitleSecondStep.text?.toString() ?: ""
                viewModel.setTitle(title)
            }
        })
    }

    private fun setDetailContents() {
        binding.etDetailSecondStep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val detailContents = binding.etDetailSecondStep.text?.toString() ?: ""
                viewModel.setDetailContents(detailContents)
            }
        })
    }

    private val detailAddressListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                detailAddressFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private val titleListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                titleFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private val contentsListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                contentsFlag = when {
                    s.isNotEmpty() -> true
                    else -> false
                }
                flagCheck()
            }
        }
    }

    private fun flagCheck() {
        binding.btnRegister.isEnabled = detailAddressFlag && titleFlag && contentsFlag
    }
}
