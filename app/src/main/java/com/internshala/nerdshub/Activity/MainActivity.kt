package com.internshala.nerdshub.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.nerdshub.*
import com.internshala.nerdshub.Fragment.AboutAppFragment
import com.internshala.nerdshub.Fragment.DashboardFragment
import com.internshala.nerdshub.Fragment.FavouritesFragment
import com.internshala.nerdshub.Fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView:NavigationView
    var previousMenuItem:MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.CoordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openDashboard()
        navigationView.setNavigationItemSelectedListener{
            if(previousMenuItem !=null)
            {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable=true
            it.isChecked = true
            previousMenuItem = it
            when(it.itemId){
               R.id.dashboard ->openDashboard()
                R.id.profile ->openProfile()
                R.id.about_app ->openAbout()
                R.id.favourite ->openFavourite()
            }
            return@setNavigationItemSelectedListener true }
    }
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Nerd's Hub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openDashboard()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, DashboardFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }
    fun openProfile()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ProfileFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Profile"
    }
    fun openFavourite()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FavouritesFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Favourite"
    }
    fun openAbout()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, AboutAppFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "About App"
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when(frag)
        {
            !is DashboardFragment ->openDashboard()
            else-> super.onBackPressed()
        }
    }
}