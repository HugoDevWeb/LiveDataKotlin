package com.example.td2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.edit_task.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.item_task.*
import java.util.*



class TaskActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task)


        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            edit_description.setText(it)
        }



        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Ceci est un message test provenant de l'appli kotlin")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)



        val imageUris: ArrayList<Uri> = arrayListOf(
            // Add your image URIs here

        )

        val shareIntent2 = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
            type = "image/*"
        }
        startActivity(Intent.createChooser(shareIntent2, "Share images to.."))

//
        val task = intent.getSerializableExtra("task") as? Task
        if (task != null) {
            edit_title.setText(task.title)
            edit_description.setText(task.description)
        }


        create_full_task.setOnClickListener {
            val newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = edit_title.text.toString(), description = edit_description.text.toString())
            intent.putExtra(TASK_KEY, newTask)
            setResult(Activity.RESULT_OK, intent)
            finish()
           }


    }



    companion object {
        const val TASK_KEY = "reply_key"
    }


}