package com.ardaisitan.dicerollerapp

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ardaisitan.dicerollerapp.databinding.ActivityMainBinding
import nl.dionsegijn.konfetti.models.Size

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var quessNumber = 0
    private var numberofTry = 0
    private var remainNumber = 0


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val getIntent = intent
        remainNumber = getIntent.getIntExtra("remain", 0)
        val getNumber = getIntent.getIntExtra("number", 0)
        quessNumber = getNumber

        binding.apply {
            myRemainText.text = "Kalan Hak:$remainNumber"
            diceImage.setImageResource(R.drawable.dice_1)
        }

        //Button Animation
        val animator =
            ObjectAnimator.ofInt(
                binding.rollButton,
                "backgroundColor",
                Color.BLUE,
                Color.RED,
                Color.GREEN,
            )

        animator.duration = 1000
        animator.setEvaluator(ArgbEvaluator())

        animator.repeatCount = Animation.REVERSE

        animator.repeatCount = Animation.INFINITE
        animator.start()




        binding.rollButton.setOnClickListener {
            remainNumber--
            if (remainNumber < 0) {
                Toast.makeText(this, "Hakkınız Bitmiştir.", Toast.LENGTH_SHORT).show()
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Uyarı Mesajı")
                alert.setMessage("Başa Dönmek İster Misiniz?")
                alert.setPositiveButton("Evet") { dialog, which ->
                    val goEntrancePage2 = Intent(this@MainActivity, entrancePage::class.java)
                    startActivity(goEntrancePage2)
                    finish()
                }
                alert.setNegativeButton("Hayır") { dialog, which ->
                    Toast.makeText(
                        this@MainActivity,
                        "Doğru sayıyı tahmin edemediniz.Oyun Bitmiştir.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                alert.show()

            } else {
                val randomNumber = (1..6).random()
                numberofTry++
                val myDrawable = when (randomNumber) {
                    1 -> R.drawable.dice_1
                    2 -> R.drawable.dice_2
                    3 -> R.drawable.dice_3
                    4 -> R.drawable.dice_4
                    5 -> R.drawable.dice_5
                    else -> R.drawable.dice_6
                }
                binding.apply {
                    diceImage.setImageResource(myDrawable)
                    resultText.text = "Gelen Sayi:$randomNumber"
                    myRemainText.text = "Kalan Hak:$remainNumber"
                }

                if (quessNumber == randomNumber) {

                    // Confetti Animation
                    binding.confetti.build()
                        .addColors(Color.RED,Color.GREEN,Color.MAGENTA,Color.YELLOW)
                        .setDirection(0.0,359.0)
                        .setSpeed(1f,5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(5000L)
                        .addShapes(nl.dionsegijn.konfetti.models.Shape.RECT, nl.dionsegijn.konfetti.models.Shape.CIRCLE)
                        .addSizes(Size(12, 5F))
                        .setPosition(-50f, binding.confetti.getWidth() + 50f, -50f, -50f)
                        .streamFor(400,500L)


                    Toast.makeText(this, "Doğru tahmin!!", Toast.LENGTH_SHORT).show()
                    val myAlert = AlertDialog.Builder(this)
                    myAlert.setTitle("Uyarı Mesajı")
                    myAlert.setMessage("Doğru tahmini $numberofTry. denemede buldunuz.Devam etmek ister misiniz ?")
                    myAlert.setPositiveButton("Evet") { dialog, which ->
                        val goEntrancePageIntent =
                            Intent(this@MainActivity, entrancePage::class.java)
                        startActivity(goEntrancePageIntent)
                    }
                    myAlert.setNegativeButton("Hayır") { dialog, which ->
                        Toast.makeText(this@MainActivity, "Oyun bitmiştir.", Toast.LENGTH_SHORT)
                            .show()
                        finishAndRemoveTask()
                    }
                    myAlert.show()

                }

            }

        }


    }
}