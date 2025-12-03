package com.example.FinalProject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdRequest

class GuessingActivity : AppCompatActivity() {
    private lateinit var emojiIV : ImageView
    private lateinit var enterB : Button
    private lateinit var skipB : Button
    private lateinit var homeB : Button
    private lateinit var guessET : EditText
    private lateinit var bestTV : TextView
    private lateinit var scoreTV : TextView
    private lateinit var pref: SharedPreferences
    private lateinit var Egame : EmojiGame
    private lateinit var progressBar : ProgressBar
    private lateinit var switch : SwitchMaterial
    private lateinit var adView : AdView

    var score: Int = 0
    var best: Int = 0
    var theme: String = ""
    var progressCount : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guessing)
        Egame = MainActivity.game
        theme = Egame.getCatgory()
        pref = getSharedPreferences(packageName + "_gameParam", Context.MODE_PRIVATE)


        emojiIV = findViewById(R.id.emoji_phrase)
        enterB = findViewById(R.id.enterBT)
        guessET = findViewById(R.id.guess_input)
        bestTV = findViewById(R.id.best)
        scoreTV = findViewById(R.id.current)
        progressBar = findViewById(R.id.determinateBar)
        skipB = findViewById(R.id.skipButton)
        switch = findViewById(R.id.material_switch)
        homeB = findViewById(R.id.home)

        // create AdView
        adView = AdView( this )
        var adSize : AdSize = AdSize( AdSize.FULL_WIDTH, AdSize. AUTO_HEIGHT )
        adView.setAdSize( adSize )
        var adUnitId : String = "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId


        // build an AdRequest
        var builder : AdRequest.Builder = AdRequest.Builder( )
        builder.addKeyword( "game" ).addKeyword( "guess" )
        var request : AdRequest = builder.build()


        // place adView in the LinearLayout
        Log.w("MenuActivity", "Inside Ad")
        var adLayout : LinearLayout = findViewById( R.id.ad_view )
        adLayout.addView( adView )


        // request ad from Google
        adView.loadAd( request )


        Log.w("MenuActivity", "outside Ad")

        var temp1 = pref.getInt(getScoreVal(theme) + "progress", 0)
        scoreTV.text = "Current Score: $temp1"

        var temp2 = pref.getInt(getScoreVal(theme), 0)
        bestTV.text =  "Best Score: $temp2"
//        best = pref.getInt("BestScore", 0)
        progressBar.progress = 0

        setup()
        enterB.setOnClickListener{check()}

        skipB.setOnClickListener {
            skip()
        }

        switch.setOnClickListener {
            if (switch.isChecked) {
                skipB.visibility = View.INVISIBLE
            } else {
                skipB.visibility = View.VISIBLE
            }
        }

        homeB.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun endGame() {
        val intent = Intent(this, EndingActivity::class.java)
        intent.putExtra("FINAL_SCORE", score)
        intent.putExtra("BEST_SCORE", best)
        intent.putExtra("GAME_THEME", theme)
        pref.edit().putInt(getScoreVal(theme) + "progress", 0).apply()
        pref.edit().putInt(getScoreVal(theme) + "progress", 0).apply()

        var temp = pref.getInt(getScoreVal(theme) + "progress", 0)
        Log.w("GuessingActivity", "FINAL_SCORE: " + score)
        Log.w("GuessingActivity", "BEST_SCORE: " + best)
        Log.w("GuessingActivity", "FINAL_SCORE: " + theme)
        Log.w("GuessingActivity", "Progress: " + progressCount)

        startActivity(intent)
        finish()
    }

    fun skip() {
        updateLevel()
        Egame.increaseIndex()
        setup()
    }
    fun check(){
        var game = MainActivity.game
        var guess = guessET.text.toString()


        game.checkAnswer(guess.lowercase())


        var firebase : FirebaseDatabase = FirebaseDatabase.getInstance()
        var reference : DatabaseReference = firebase.getReference(game.getPicName())
        var listener : DataListener = DataListener( )
        reference.addValueEventListener( listener )


        Log.w("GuessingActivity", "IsCorrect - " + game.getIsCorrect())
    }




    fun setup(){
        var game = MainActivity.game
        if(game.getCatgory().equals("MOVIES")){
            val image = EmojiGame.MOVIES_PICS.get(game.getIndex())
            Log.w("GuessingActivity", "Index : " + game.getIndex())
            Log.w("GuessingActivity", "Image : " + image)
            emojiIV.setImageResource(image)
        }else if (game.getCatgory().equals("RESTAURANTS")){
            val image = EmojiGame.RESTAURANTS_PICS.get(game.getIndex())
            emojiIV.setImageResource(image)
        }else{
            val image = EmojiGame.GENERAL_PICS.get(game.getIndex())
            emojiIV.setImageResource(image)
        }
    }




    fun updateLevel() {
        if (score >= best) {
            best = score
            pref.edit().putInt(getScoreVal(theme), score).apply()
            bestTV.text = "High Score: $best"
        }
        progressCount++;
        pref.edit().putInt(getScoreVal(theme) + "progress", progressCount).apply()

        progressBar.progress = progressCount * 10

        if (progressCount >= 10) {
            endGame()
        }
        Log.w("GuessingActivity", "Progress Count in Update Level" + progressCount)
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

    inner class DataListener : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if( snapshot.value != null ) {
                var correctAnswer: String = snapshot.value.toString()
                Egame.setAnswer(correctAnswer)


                Log.w("EmojiGame", "Key - " + correctAnswer)
                Log.w("EmojiGame", "Guess - " + Egame.getGuess())


                if(correctAnswer == Egame.getGuess()){
                    Log.w("EmojiGame", "Guess was correct")
                    Egame.increaseIndex()
                    // index++
                    Egame.setIsCorret(true)
                }else{
                    Egame.setIsCorret(false)
                }


                if(Egame.getIsCorrect() == true){
                    score++
                    scoreTV.text = "Score: $score"
                    updateLevel()
                    setup()
                    guessET.setText("")
                }else{
                    Toast.makeText(this@GuessingActivity, "Incorrect guess, please try again.", Toast.LENGTH_LONG).show()
                    guessET.setText("")
                }
            }
        }


        override fun onCancelled(error: DatabaseError) {
            Log.w("EmojiGame", "error")
        }
    }

}
