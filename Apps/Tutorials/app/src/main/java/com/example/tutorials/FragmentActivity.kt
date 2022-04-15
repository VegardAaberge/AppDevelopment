package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tutorials.fragments.FirstFragment
import com.example.tutorials.fragments.SecondFragment
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        ReplaceFragment(firstFragment)

        btnFragment1.setOnClickListener {
            ReplaceFragment(firstFragment, true)
        }

        btnFragment2.setOnClickListener {
            ReplaceFragment(secondFragment, true)
        }
    }

    fun ReplaceFragment(fragment: Fragment, addToBackStack: Boolean = false){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            if(addToBackStack) addToBackStack(null)
            commit()
        }
    }
}