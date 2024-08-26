package com.example.grocerycalculator

import java.io.Serializable

data class CheckboxItem(
    var text: String = "",
    var isChecked: Boolean = false,
    var price: Double = 0.0
): Serializable




