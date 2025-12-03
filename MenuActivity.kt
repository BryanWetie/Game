package com.example.FinalProject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MenuActivity : AppCompatActivity() {


    private lateinit var movieBT: Button
    private lateinit var resturantBT: Button
    private lateinit var genBT: Button
    private lateinit var userNameET : TextView
    private lateinit var pref: SharedPreferences
    private lateinit var homeB: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        pref = getSharedPreferences(packageName + "_gameParam", Context.MODE_PRIVATE)

        movieBT = findViewById(R.id.movieBT)
        resturantBT = findViewById(R.id.resturantsBT)
        genBT = findViewById(R.id.genBT)
        userNameET = findViewById(R.id.userET)
        homeB = findViewById(R.id.homeB)

        var userName = pref.getString("name", "Guest");

        try {
            MainActivity.game.toString()
        } catch (e: UninitializedPropertyAccessException) {
            MainActivity.Companion.game = EmojiGame()
        }


        if (userName != null) {
            MainActivity.game.setName(userName)
        }
        userNameET.text = "Welcome $userName!!!"



        movieBT.setOnClickListener { getPref("MOVIES") }
        resturantBT.setOnClickListener { getPref("RESTAURANTS") }
        genBT.setOnClickListener { getPref("GENERAL") }

        homeB.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun getPref(category: String){
        var game = MainActivity.game
        game.setCategory(category)
        var intent = Intent(this, GuessingActivity::class.java)
        startActivity(intent)
    }
}


