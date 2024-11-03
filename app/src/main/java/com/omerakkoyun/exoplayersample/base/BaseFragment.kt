package com.omerakkoyun.exoplayersample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * Created by Omer AKKOYUN on 3.11.2024.
 */

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    // ViewModel
    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[initViewModel()]
    }

    // Fragment Binding
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    // Initialize view model
    abstract fun initViewModel(): Class<VM>

    // Fragment methods
    protected abstract fun onCreateFinished()
    protected abstract fun attachListeners()
    protected abstract fun observeEvents()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateFinished()
        attachListeners()
        observeEventsWithLifecycleOwner()
    }

    private fun observeEventsWithLifecycleOwner() {
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                observeEvents()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // clera binding
    }

}
