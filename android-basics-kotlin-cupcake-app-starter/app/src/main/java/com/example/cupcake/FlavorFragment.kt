/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.CupCakeViewModel
import com.example.cupcake.model.OrderViewModel

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class FlavorFragment : Fragment() {

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flavorFragment = this@FlavorFragment
        }
    }

    /**
     * Navigate to the next screen to choose pickup date.
     */
    fun goToNextScreen() {
        setDiffCupCakes()
        if (sharedViewModel.isNotQuantityCorrect()) {
            sharedViewModel.cupCakeList.clear()
            Toast.makeText(
                this.requireContext(),
                "Your cupCakes quantity is not correct!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }

    private fun setDiffCupCakes() {
        if (binding!!.root.findViewById<CheckBox>(R.id.vanilla).isChecked &&
            binding!!.root.findViewById<EditText>(R.id.vanilla_value).text != null
        ) {
            val quantityStr =
                binding!!.root.findViewById<EditText>(R.id.vanilla_value).text.toString()
            val quantity = quantityStr.toInt()
            sharedViewModel.setFlavorAndValueList(getString(R.string.vanilla), quantity)
        }
        if (binding!!.root.findViewById<CheckBox>(R.id.chocolate).isChecked &&
            binding!!.root.findViewById<EditText>(R.id.chocolate_value).text != null
        ) {
            val quantityStr =
                binding!!.root.findViewById<EditText>(R.id.chocolate_value).text.toString()
            val quantity = quantityStr.toInt()
            sharedViewModel.setFlavorAndValueList(getString(R.string.chocolate), quantity)
        }
        if (binding!!.root.findViewById<CheckBox>(R.id.red_velvet).isChecked &&
            binding!!.root.findViewById<EditText>(R.id.redVelvet_value).text != null
        ) {
            val quantityStr =
                binding!!.root.findViewById<EditText>(R.id.redVelvet_value).text.toString()
            val quantity = quantityStr.toInt()
            sharedViewModel.setFlavorAndValueList(getString(R.string.red_velvet), quantity)
        }
        if (binding!!.root.findViewById<CheckBox>(R.id.salted_caramel).isChecked &&
            binding!!.root.findViewById<EditText>(R.id.saltedCaramel_value).text != null
        ) {
            val quantityStr =
                binding!!.root.findViewById<EditText>(R.id.saltedCaramel_value).text.toString()
            val quantity = quantityStr.toInt()
            sharedViewModel.setFlavorAndValueList(getString(R.string.salted_caramel), quantity)
        }
        if (binding!!.root.findViewById<CheckBox>(R.id.coffee).isChecked &&
            binding!!.root.findViewById<EditText>(R.id.coffee_value).text != null
        ) {
            val quantityStr =
                binding!!.root.findViewById<EditText>(R.id.coffee_value).text.toString()
            val quantity = quantityStr.toInt()
            sharedViewModel.setFlavorAndValueList(getString(R.string.coffee), quantity)
        }
    }
}