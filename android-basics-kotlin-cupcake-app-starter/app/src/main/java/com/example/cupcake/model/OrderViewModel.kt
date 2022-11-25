package com.example.cupcake.model

import android.provider.Settings.Global.getString
import android.service.autofill.Validators.and
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.0
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.0

class OrderViewModel : ViewModel() {
    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity
    private var _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor
    private var _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    private var _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()

    private var _cupCakeName = MutableLiveData<String>()
    val cupCakeName: LiveData<String> = _cupCakeName
    val cupCakeList = mutableListOf<CupCakeViewModel>()

    init {
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
        _cupCakeName.value = ""

        cupCakeList.clear()
    }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (date.value == dateOptions[0]) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    fun isNotPickUpToday(notFlavor: String, notPickupDay: String): Boolean {
        if (flavor.value == notFlavor && date.value == notPickupDay) return true
        return false
    }

    fun setName(name: String) {
        _cupCakeName.value = name
    }

    fun setFlavorAndValueList(flavor: String, value: Int) {
        cupCakeList.add(CupCakeViewModel(flavor, value))
    }
}