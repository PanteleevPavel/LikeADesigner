package com.example.likeadesigner.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.likeadesigner.R
import com.example.likeadesigner.databinding.MainFragmentBinding
import com.example.likeadesigner.view.util.hide
import com.example.likeadesigner.view.util.show
import com.example.likeadesigner.viewmodel.PictureOfTheDayData
import com.example.likeadesigner.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(requireActivity()).get(PictureOfTheDayViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                binding.mainFragmentLoadingLayout.hide()
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast(getString(R.string.empty_link))
                } else {
                    with(binding) {
                        photoOfTheDay.load(url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                        photoOfTheDayTitle.text = serverResponseData.title
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                binding.mainFragmentLoadingLayout.show()
            }
            is PictureOfTheDayData.Error -> {
            }

        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}