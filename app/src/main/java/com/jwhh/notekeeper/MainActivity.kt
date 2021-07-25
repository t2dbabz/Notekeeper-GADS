package com.jwhh.notekeeper

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.jwhh.notekeeper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val tag = this::class.simpleName
    private var notePosition = POSITION_NOT_SET
    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)


        val adapterCourses = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                             DataManager.courses.values.toList())

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.content.spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else{
            createNewNote()
        }

        Log.d(tag, "onCreate")

    }

    private fun createNewNote() {
        DataManager.notes.add(NoteInfo())
        notePosition = DataManager.notes.lastIndex
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {

        if (notePosition > DataManager.notes.lastIndex){
            showMessage("Note Not Found")
            Log.e(tag, "Invalid note position $notePosition. max valid position ${ DataManager.notes.lastIndex}")
        }

        Log.i(tag, "Displaying note for position $notePosition")
       val note = DataManager.notes[notePosition]
        binding.content.textNoteTitle.setText(note.title)
        binding.content.textNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        binding.content.spinnerCourses.setSelection(coursePosition)
    }

    private fun showMessage(message: String){
        Snackbar.make(binding.content.textNoteTitle, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
                if (menuItem != null){
                    menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                    menuItem.isEnabled = false
                }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
        Log.d(tag, "onPause")
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = binding.content.textNoteTitle.text.toString()
        note.text = binding.content.textNoteText.text.toString()
        note.course = binding.content.spinnerCourses.selectedItem as CourseInfo


    }



}
