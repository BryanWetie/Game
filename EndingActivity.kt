package com.example.FinalProject


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class EndingActivity : AppCompatActivity() {
    private lateinit var phoneNumberET: EditText
    private lateinit var sendMessageButton: Button
    private lateinit var endingTitleTV: TextView
    private lateinit var resultMessageTV: TextView
    private lateinit var finalScoreTV: TextView
    private lateinit var bestScoreTV: TextView
    private lateinit var playAgainBT: Button
    private lateinit var homeBT: Button
    private lateinit var pref: SharedPreferences
    private lateinit var Egame : EmojiGame
    private var finalScore: Int = 0
    private var bestScore: Int = 0
    private var theme: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end)
        Egame = MainActivity.game
        theme = Egame.getCatgory()
        pref = getSharedPreferences(packageName + "_gameParam", Context.MODE_PRIVATE)


        endingTitleTV = findViewById(R.id.ending_title)
        resultMessageTV = findViewById(R.id.result_message)
        finalScoreTV = findViewById(R.id.final_score)
        bestScoreTV = findViewById(R.id.best_score)
        playAgainBT = findViewById(R.id.play_again_button)
        homeBT = findViewById(R.id.home_button)
        phoneNumberET = findViewById(R.id.phone_num_view)
        sendMessageButton = findViewById(R.id.message_button)


        finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        bestScore = pref.getInt(getScoreVal(theme), 0)




        finalScoreTV.text = "$finalScore/10"
        bestScoreTV.text = "$bestScore/10"
        setMessageDisplay(finalScore)


        playAgainBT.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }


        sendMessageButton.setOnClickListener{sendTextMessage()}


        homeBT.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun sendTextMessage(){
        var phone_num = phoneNumberET.text.toString()
        val textIntent = Intent(Intent.ACTION_VIEW)
        textIntent.setData(Uri.parse("smsto:"))
        textIntent.putExtra("address", phone_num)
        textIntent.putExtra("sms_body", "I got a score of " + "$finalScore/10" + " in the Guess The Phrase Game!")
        startActivity(textIntent)
        finish()
    }


    private fun setMessageDisplay(score: Int) {
        when {
            score == 10 -> {
                resultMessageTV.text = "Perfect Score! You're an Emoji Master!"
            }
            score >= 8 -> {
                resultMessageTV.text = "Great Job! Almost Perfect!"
            }
            score >= 6 -> {
                resultMessageTV.text = "Good Work! Keep Practicing!"
            }
            score >= 4 -> {
                resultMessageTV.text = "Not Bad! You're Getting There!"
            }
            else -> {
                resultMessageTV.text = "Keep Trying! Practice Makes Perfect!"
            }
        }
    }


    fun getScoreVal (theme:String): String{
        var value :String = ""


        if (theme == "MOVIES"){
            value = "bestMOVIES"
        }
        else if (theme == "RESTAURANT"){
            value = "bestRESTAURANT"
        }
        else {
            value = "bestGENERAL"
        }
        return value
    }
}
