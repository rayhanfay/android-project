package com.rayhan.tugas5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.rayhan.tugas5.Fragment.DraftFragment
import com.rayhan.tugas5.Fragment.InboxFragment
import com.rayhan.tugas5.Fragment.SentFragment
import com.rayhan.tugas5.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbarAwal
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""  // Using safe call operator instead of !!

        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager = binding.viewPager

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.tambahFragment(InboxFragment(), "Inbox")
        viewPagerAdapter.tambahFragment(DraftFragment(), "Draft")
        viewPagerAdapter.tambahFragment(SentFragment(), "Sent")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(
        fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val juduls: ArrayList<String> = ArrayList()

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun tambahFragment(fragment: Fragment, judul: String) {
            fragments.add(fragment)
            juduls.add(judul)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return juduls[position]
        }
    }
}