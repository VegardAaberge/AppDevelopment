package com.example.tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.example.tutorials.Adapters.ViewPagerAdapter
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPager : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        val images = mutableListOf<Int>(
            R.drawable.kermit,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
        )

        val adapter = ViewPagerAdapter(images)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
            when(position){
                0 -> tab.setIcon(R.drawable.ic_messages)
                1 -> tab.setIcon(R.drawable.ic_profile)
                2 -> tab.setIcon(R.drawable.ic_home)
                3 -> tab.setIcon(R.drawable.ic_settings)
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@ViewPager, "Selected ${tab?.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@ViewPager, "Unselected ${tab?.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@ViewPager, "Reselected ${tab?.text}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}