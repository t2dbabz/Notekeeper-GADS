package com.jwhh.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jwhh.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        binding.fab.setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }

        binding.noteListContent.listItems.layoutManager = LinearLayoutManager(this)




    }

    override fun onResume() {
        super.onResume()


    }

}