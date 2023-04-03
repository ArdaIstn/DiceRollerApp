package com.ardaisitan.dicerollerapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ardaisitan.dicerollerapp.databinding.ActivityEntrancePageBinding


class entrancePage : AppCompatActivity() {
    private lateinit var binding: ActivityEntrancePageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance_page)
        binding = ActivityEntrancePageBinding.inflate(layoutInflater)
        Toast.makeText(this, "Hoşgeldiniz!!", Toast.LENGTH_SHORT).show()
        val view = binding.root
        setContentView(view)

        //Button Animation
        val animator =
            ObjectAnimator.ofInt(
                binding.changePage,
                "backgroundColor",
                Color.GREEN,
                Color.DKGRAY,
                Color.RED,
            )

        animator.duration = 1000
        animator.setEvaluator(ArgbEvaluator())

        animator.repeatCount = Animation.REVERSE

        animator.repeatCount = Animation.INFINITE
        animator.start()

        binding.changePage.setOnClickListener {
            val myNumber = binding.myNumberText.text.toString().toIntOrNull()
            val myRemainText = binding.remainText.text.toString().toIntOrNull()

            if (myNumber == null || myRemainText == null) {
                Toast.makeText(
                    this,
                    "Lütfen zar tahmini ve hak sayinizi numara şeklinde giriniz",
                    Toast.LENGTH_LONG
                ).show()
            } else if ((myNumber > 6)) {
                Toast.makeText(
                    this,
                    "Lütfen zar tahmini bölümüne 6 veya 6'dan küçük bir sayi giriniz",
                    Toast.LENGTH_LONG
                ).show()
            } else if (myNumber <= 0 || myRemainText <= 0) {
                Toast.makeText(
                    this,
                    "Lütfen 0'dan büyük  bir sayi giriniz.",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                val myIntent = Intent(applicationContext, MainActivity::class.java)
                myIntent.putExtra("number", myNumber)
                myIntent.putExtra("remain", myRemainText)
                startActivity(myIntent)
                finish()
            }

        }

    }

}
