package com.example.likeadesigner.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.likeadesigner.databinding.SettingsFragmentBinding

private const val DARK_THEME_ACTIVATED = "DARK THEME ACTIVATED"

class SettingsFragment : Fragment() {

    private var isDarkThemeActivated = false

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref: SharedPreferences =
            requireActivity().getPreferences(Context.MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = sharedPref.edit()
        readPreferences()
        binding.darkThemeSwitch.setOnClickListener {
            prefEditor.putBoolean(DARK_THEME_ACTIVATED, binding.darkThemeSwitch.isChecked)
                .apply()
            requireActivity().recreate()
        }
    }

    private fun readPreferences() {
        isDarkThemeActivated = requireActivity()
            .getPreferences(Context.MODE_PRIVATE)
            .getBoolean(DARK_THEME_ACTIVATED, false)
        binding.darkThemeSwitch.isChecked = isDarkThemeActivated
    }

}