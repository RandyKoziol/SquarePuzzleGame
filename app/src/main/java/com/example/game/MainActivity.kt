package com.example.game

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

var box1 = 0
var box2 = 0
var box3 = 0
var box4 = 0

var box5 = 0
var box6 = 0
var box7 = 0
var box8 = 0

var box9 = 0
var box10 = 0
var box11 = 0
var box12 = 0

var box13 = 0
var box14 = 0
var box15 = 0
var box16 = 0

var firstRow = 0
var secondRow = 0

var correct1 = 0
var correct2 = 0
var correct3 = 0
var correct4 = 0

var correctCounter = 0
var incorrectCounter = 0

var isChronometerRunning = false
var isPopupOpen = false

var box = mutableListOf<ImageView>()

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        box.addAll(listOf<ImageView>(image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15, image16))

        rowOne()
        rowTwo()
        rowThree()
        rowFour()

    }

    fun clickBox(view: View) {

        if (!isChronometerRunning)
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        isChronometerRunning = true

        when(view.id){

            image1.id -> tabLogic(box13, findViewById(R.id.image1))
            image2.id -> tabLogic(box14, findViewById(R.id.image2))
            image3.id -> tabLogic(box15, findViewById(R.id.image3))
            image4.id -> tabLogic(box16, findViewById(R.id.image4))

            image5.id -> tabLogic(box9, findViewById(R.id.image5))
            image6.id -> tabLogic(box10, findViewById(R.id.image6))
            image7.id -> tabLogic(box11, findViewById(R.id.image7))
            image8.id -> tabLogic(box12, findViewById(R.id.image8))

            image9.id -> tabLogic(box5, findViewById(R.id.image9))
            image10.id -> tabLogic(box6, findViewById(R.id.image10))
            image11.id -> tabLogic(box7, findViewById(R.id.image11))
            image12.id -> tabLogic(box8, findViewById(R.id.image12))

            image13.id -> tabLogic(box1, findViewById(R.id.image13))
            image14.id -> tabLogic(box2, findViewById(R.id.image14))
            image15.id -> tabLogic(box3, findViewById(R.id.image15))
            image16.id -> tabLogic(box4, findViewById(R.id.image16))
        }

        if (correctCounter == 4) {
            chronometer.stop()
            txtWin.text = "You Win!"

            for (image in box){ makeBoxesUnclickable(image) }
        }
    }

    private fun makeBoxesUnclickable(imageView: ImageView){ imageView.isClickable = false }

    private fun tabLogic(box: Int, image: ImageView) {
        if (box == 1) {
            image.setImageResource(R.drawable.green)
            correctCounter++
            incorrectCounter--
            txtCorrect.text = "Correct Tiles: $correctCounter"
        } else
            image.setImageResource(R.drawable.red)
            incorrectCounter++
            txtIncorrect.text = "Incorrect Tiles: $incorrectCounter"

        image.isClickable = false
    }

    private fun rowOne() {

        val row1List = mutableListOf(1, 0, 0, 0).shuffled()

        box1 = row1List[0]
        box2 = row1List[1]
        box3 = row1List[2]
        box4 = row1List[3]

        correct1 = row1List.indexOfFirst { it == 1 }
    }

    private fun rowTwo() {

        val row2List = mutableListOf(1, 0, 0, 0).shuffled()

        box5 = row2List[0]
        box6 = row2List[1]
        box7 = row2List[2]
        box8 = row2List[3]

        correct2 = row2List.indexOfFirst { it == 1 }

        if (correct2 == correct1) rowTwo()

    }

    private fun rowThree() {

        val row3List = mutableListOf(1, 0, 0, 0).shuffled()

        box9 = row3List[0]
        box10 = row3List[1]
        box11 = row3List[2]
        box12 = row3List[3]

        correct3 = row3List.indexOfFirst { it == 1 }

        if (correct3 == correct1 || correct3 == correct2) rowThree()

    }

    private fun rowFour() {
        val winSum = correct1 + correct2 + correct3

        when (winSum) {
            6 -> box13 = 1
            5 -> box14 = 1
            4 -> box15 = 1
            3 -> box16 = 1
        }

        when (winSum) {
            6 -> correct4 = 0
            5 -> correct4 = 1
            4 -> correct4 = 2
            3 -> correct4 = 3
        }

    }

    fun reset(view: View) {
        //reset all box values
        box1 = 0
        box2 = 0
        box3 = 0
        box4 = 0

        box5 = 0
        box6 = 0
        box7 = 0
        box8 = 0

        box9 = 0
        box10 = 0
        box11 = 0
        box12 = 0

        box13 = 0
        box14 = 0
        box15 = 0
        box16 = 0

        firstRow = 0
        secondRow = 0

        correct1 = 0
        correct2 = 0
        correct3 = 0
        correct4 = 0

        //reset the correct and incorrect values
        correctCounter = 0
        incorrectCounter = 0

        txtCorrect.text = "Correct Tiles: 0"
        txtIncorrect.text = "Incorrect Tiles: 0"

        txtWin.text = ""

        //create a new pattern
        rowOne()
        rowTwo()
        rowThree()
        rowFour()

        //reset timer
        isChronometerRunning = false
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.stop()

        //make all boxes clickable again
        for (image in box){makeBoxesClickable(image)}

        //make all boxes grey again
        for (image in box){ resetBoxToGrey(image) }

    }

    private fun makeBoxesClickable(imageView: ImageView){
        imageView.isClickable = true
    }

    private fun resetBoxToGrey(imageView: ImageView){
        imageView.setImageResource(R.drawable.grey)
    }

    fun tutorial(view: View) {

        val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.tutorial_layout, null)
            window.contentView = view


        val dismiss = view.findViewById<TextView>(R.id.popupText)
        dismiss.setOnClickListener {
            isPopupOpen = false
            btnTutorial.isClickable = true
            window.dismiss()
        }

        if (!isPopupOpen) {
            window.showAsDropDown(btnTutorial)
            isPopupOpen = true
            btnTutorial.isClickable = false
        }
    }
}