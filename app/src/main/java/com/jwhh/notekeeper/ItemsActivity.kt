package com.jwhh.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jwhh.notekeeper.databinding.ActivityItemsBinding



class ItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    private val noteRecyclerAdapter by lazy {
        NoteRecyclerAdapter(this, DataManager.notes)
    }

    private val coursesLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    private lateinit var binding: ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarItems.toolbar)

        binding.appBarItems.fab.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }

      val listItem =  binding.appBarItems.noteListContent.listItems

        displayNotes(listItem)


        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBarItems.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)


    }

    private fun displayNotes(listItem: RecyclerView) {
        listItem.layoutManager = noteLayoutManager
        listItem.adapter = noteRecyclerAdapter

        binding.navView.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses(listItem: RecyclerView){
        listItem.layoutManager = coursesLayoutManager
        listItem.adapter = courseRecyclerAdapter

        binding.navView.menu.findItem(R.id.nav_courses).isChecked = true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        binding.appBarItems.noteListContent.listItems.adapter?.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_notes -> {
                displayNotes(binding.appBarItems.noteListContent.listItems)
            }

            R.id.nav_courses ->{
                displayCourses(binding.appBarItems.noteListContent.listItems)
            }

            R.id.nav_share -> {
                handleSelection("Don't think you've shared enough")
            }
            R.id.nav_send -> {
                handleSelection("Send")
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true


    }

    private fun handleSelection(message: String) {
        Snackbar.make(binding.appBarItems.fab, message, Snackbar.LENGTH_LONG).show()
    }

}