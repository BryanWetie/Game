package com.example.FinalProject

import kotlin.random.Random


class EmojiGame {
    private lateinit var category : String // save user's preference
    private lateinit var name : String
    private var isCorrect : Boolean = false
    private var guess : String = ""
    private var index : Int = 0
    private var minNum : Int = 1
    private var maxNum : Int = 20
    private var answer : String = ""
    private var picName : String = ""




    private  var emojisList : ArrayList<Int> =  ArrayList()


    fun EmojiGame(){
        this.category = "GENERAL"
    }


    fun getIsCorrect() : Boolean{
        return isCorrect
    }


    fun setCategory(category : String){
        this.category = category
        if(category.equals("MOVIES")){
            maxNum = 22
        }else if (category.equals("RESTAURANTS")){
            maxNum = 25
        }else{
            maxNum = 21
        }
        pickEmojis()
    }


    fun pickEmojis(){
        for(i in 0..9){
            var index : Int = Random.nextInt(0, maxNum )
            if(emojisList.contains(index) == false){
                emojisList.add(i, index)
            }else{
                var foundPlace : Boolean = false
                while(!foundPlace){
                    var index : Int = Random.nextInt(0, maxNum)
                    if(emojisList.contains(index) == false){
                        emojisList.add(i, index)
                        foundPlace = true
                    }
                }
            }
        }


    }


    fun setName(name : String){
        this.name = name
    }


    fun checkAnswer(guess : String){
        this.guess = guess


        var picture_name : String = ""
        if(category.equals("MOVIES")){
            picture_name = "emoji_movie_" + (emojisList.get(index) + 1)
        }else if (category.equals("RESTAURANTS")){
            picture_name = "emoji_resurants_" + (emojisList.get(index) + 1)
        }else{
            picture_name = "emoji_general_" + (emojisList.get(index) + 1)
        }
        picName = picture_name
    }


    fun setIsCorret(a : Boolean){
        isCorrect = a
    }


    fun increaseIndex(){
        index++
    }


    fun setAnswer(a : String){
        answer = a
    }


    fun getGuess() : String{
        return guess
    }


    fun getPicName() : String{
        return picName
    }


    fun getIndex() : Int{
        return if (index < emojisList.size) {
            emojisList.get(index)
        } else {
            // Return the first index or reset to beginning
            index = 0  // Reset index to beginning
            emojisList.get(0)  // Return first item
        }
    }


    fun getCatgory() : String{
        return category
    }

    companion object {
        public val GENERAL_PICS = listOf(
            R.drawable.emoji_general_1, R.drawable.emoji_general_2,
            R.drawable.emoji_general_3, R.drawable.emoji_general_4,
            R.drawable.emoji_general_5, R.drawable.emoji_general_6,
            R.drawable.emoji_general_7, R.drawable.emoji_general_8,
            R.drawable.emoji_general_9, R.drawable.emoji_general_10,
            R.drawable.emoji_general_11, R.drawable.emoji_general_12,
            R.drawable.emoji_general_13, R.drawable.emoji_general_14,
            R.drawable.emoji_general_15, R.drawable.emoji_general_16,
            R.drawable.emoji_general_17, R.drawable.emoji_general_18,
            R.drawable.emoji_general_19, R.drawable.emoji_general_20,
            R.drawable.emoji_general_21
        )


        public val MOVIES_PICS = listOf(
            R.drawable.emoji_movie_1, R.drawable.emoji_movie_2,
            R.drawable.emoji_movie_3, R.drawable.emoji_movie_4,
            R.drawable.emoji_movie_5, R.drawable.emoji_movie_6,
            R.drawable.emoji_movie_7, R.drawable.emoji_movie_8,
            R.drawable.emoji_movie_9, R.drawable.emoji_general_10,
            R.drawable.emoji_movie_11, R.drawable.emoji_movie_12,
            R.drawable.emoji_movie_13, R.drawable.emoji_movie_14,
            R.drawable.emoji_movie_15, R.drawable.emoji_movie_16,
            R.drawable.emoji_movie_17, R.drawable.emoji_movie_18,
            R.drawable.emoji_movie_19, R.drawable.emoji_movie_20,
            R.drawable.emoji_movie_21, R.drawable.emoji_movie_22
        )


        public val RESTAURANTS_PICS = listOf(
            R.drawable.emoji_resturants_1, R.drawable.emoji_resturants_2,
            R.drawable.emoji_resturants_3, R.drawable.emoji_resturants_4,
            R.drawable.emoji_resturants_5, R.drawable.emoji_resturants_6,
            R.drawable.emoji_resturants_7, R.drawable.emoji_resturants_8,
            R.drawable.emoji_resturants_9, R.drawable.emoji_resturants_10,
            R.drawable.emoji_resturants_11, R.drawable.emoji_resturants_12,
            R.drawable.emoji_resturants_13, R.drawable.emoji_resturants_14,
            R.drawable.emoji_resturants_15, R.drawable.emoji_resturants_16,
            R.drawable.emoji_resturants_17, R.drawable.emoji_resturants_18,
            R.drawable.emoji_resturants_19, R.drawable.emoji_resturants_20,
            R.drawable.emoji_resturants_21, R.drawable.emoji_resturants_22,
            R.drawable.emoji_resturants_23, R.drawable.emoji_resturants_24,
            R.drawable.emoji_resturants_25
        )
    }


}

