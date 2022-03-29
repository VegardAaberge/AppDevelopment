package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mOptionsList: ArrayList<TextView> = ArrayList<TextView>()
    private var mCorrectAnswers: Int = 0

    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = Constants.GetQuestions()

        setQuestion()

        tv_option1.setOnClickListener(this)
        tv_option2.setOnClickListener(this)
        tv_option3.setOnClickListener(this)
        tv_option4.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    private fun setQuestion(){

        val question = mQuestionList!![mCurrentPosition - 1]

        defaultOptionsView()

        pb_progressBar.progress = mCurrentPosition
        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_progress.text = "${mCurrentPosition}/${pb_progressBar.max}"
        tv_option1.text = question.option1
        tv_option2.text = question.option2
        tv_option3.text = question.option3
        tv_option4.text = question.option4

        btn_submit.text = "Submit"
        mSelectedOptionPosition = 0
    }

    private fun defaultOptionsView(){

        mOptionsList = ArrayList<TextView>()
        mOptionsList.add(0, tv_option1)
        mOptionsList.add(1, tv_option2)
        mOptionsList.add(2, tv_option3)
        mOptionsList.add(3, tv_option4)

        for (option in mOptionsList){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun submitTapped(){

        if(mSelectedOptionPosition > 0){
            checkAnswer()
        }else if(mCurrentPosition >= mQuestionList!!.size ) {
            goToResultActivity()
        }else if (mSelectedOptionPosition == -1){
            mCurrentPosition++
            setQuestion()
        }else{
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT ).show()
        }
    }

    private fun goToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.USER_NAME, mUserName)
        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)

        startActivity(intent)
    }

    private fun checkAnswer(){
        val question = mQuestionList!![mCurrentPosition - 1]

        answerView(
            question.correctAnswer - 1,
            R.drawable.correct_option_border_bg
        )

        if(question.correctAnswer != mSelectedOptionPosition){
            answerView(
                mSelectedOptionPosition - 1,
                R.drawable.wrong_option_border_bg
            )
        }else{
            mCorrectAnswers++
        }

        if(mCurrentPosition == mQuestionList!!.size){
            btn_submit.text = "Finish"
        }
        else{
            btn_submit.text = "Go to the next question"
        }
        mSelectedOptionPosition = -1
    }

    private fun answerView(optionIndex: Int, optionBackground: Int){
        val selectedOption = mOptionsList[optionIndex];
        selectedOption.background = ContextCompat.getDrawable(
            this,
            optionBackground
        )
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option1 -> selectedOptionView(tv_option1, 1)
            R.id.tv_option2 -> selectedOptionView(tv_option2, 2)
            R.id.tv_option3 -> selectedOptionView(tv_option3, 3)
            R.id.tv_option4 -> selectedOptionView(tv_option4, 4)
            R.id.btn_submit -> submitTapped()
        }
    }

    private fun selectedOptionView(tv: TextView,
                                   selectedOptionNumber: Int){

        if(mSelectedOptionPosition != -1){
            defaultOptionsView()
            mSelectedOptionPosition = selectedOptionNumber

            tv.setTextColor(Color.parseColor("#363A43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.selected_option_border_bg
            )
        }
    }
}