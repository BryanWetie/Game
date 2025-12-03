package com.example.FinalProject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var nameET : EditText
    private lateinit var enterBT : Button
    private lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pref = getSharedPreferences(packageName + "_gameParam", Context.MODE_PRIVATE)

        game = EmojiGame()
        setContentView(R.layout.activity_main)


        nameET = findViewById(R.id.nameET)
        enterBT = findViewById(R.id.enterBT)


        enterBT.setOnClickListener { validInput(it) }
    }


    fun validInput (click : View){
        var name = nameET.text.toString().trim()
        if(name.isNotEmpty()){
            pref.edit().putString("name", name).apply();
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Enter Text", Toast.LENGTH_LONG).show()
        }
    }


    companion object{
        public lateinit var game : EmojiGame
    }




}

