package com.example.pricecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var priceEditText:EditText
    private lateinit var discountSlider: Slider
    private lateinit var taxSlider:Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculateButton: Button = findViewById(R.id.calculate)
        calculateButton.setOnClickListener {
            showResult()
        }


    }

    private fun calculatePrice(): Double {
        /** get id**/
        priceEditText = findViewById(R.id.price_input)
        discountSlider = findViewById(R.id.discountSlider)
        taxSlider = findViewById(R.id.taxSlider)

        var discountRate = discountSlider.value.toDouble()
        var taxRate = taxSlider.value.toDouble()

        var original = priceEditText.text.toString()
        if (original.isEmpty()){
            return 0.0
        } else {
            val originalPrice = original.toDouble()
            print(originalPrice)
            return originalPrice * discountRate * (1 + taxRate / 100)
        }

    }
    private fun showResult() {
        val finalPrice = ("%.2f".format(calculatePrice()))
        val finalPriceView: TextView = findViewById(R.id.finalPriceView)

        finalPriceView.text = "Final Price: $finalPrice"
    }


}