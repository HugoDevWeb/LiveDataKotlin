package com.example.td2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.td2.network.Api
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.item_task.*
import kotlinx.android.synthetic.main.new_user_info_act.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.*
import java.util.*

class TasksFragment : Fragment() {
    val tasksAdapter = TasksAdapter()
    private val viewModel by lazy {
        ViewModelProvider(this).get(TasksViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View? {

        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.taskListLiveData.observe(this, Observer { newList ->
            tasksAdapter.list = newList.orEmpty()
            tasksAdapter.notifyDataSetChanged()
        })


        add_button.setOnClickListener {
            viewModel.addTask(Task(title = "task # ${tasksAdapter.list.count() + 1}"))
            tasksAdapter.notifyDataSetChanged()
            recycler_view_td2.scrollToPosition(tasksAdapter.list.count()-1)
        }

        add_button_arouf.setOnClickListener {
            Glide.with(it)
                .load("https://lastfm.freetls.fastly.net/i/u/avatar170s/3bedd9d81f7bdec4b5bae8397618d546")
                .circleCrop()
                .override(500, 500)
                .into(image_scroll)
            tasksAdapter.notifyDataSetChanged()
        }

        recycler_view_td2.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = tasksAdapter
        }


        val intent = Intent(activity, TaskActivity::class.java)
        val userInfoIntent = Intent(activity, UserInfoActivity::class.java)

        tasksAdapter.onEditClickListener = {

            intent.putExtra("task", it)
            startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
        }

        tasksAdapter.onDeleteClickListener = {
            viewModel.deleteTask(it)
        }

        button_intent.setOnClickListener {

            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
        img_api.setOnClickListener{
            startActivity(userInfoIntent)
        }


    }




    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
                viewModel.addTask(task)
            }
        }

        if (requestCode == EDIT_TASK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
                viewModel.editTask(task)
            }
        }
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val EDIT_TASK_REQUEST_CODE = 12
    }






    override fun onResume() {
        super.onResume()
        val glide = Glide.with(this)
        viewModel.loadTasks()
        lifecycleScope.launch {
            val userInfo = Api.INSTANCE.userService.getInfo().body()
            text_api.text = "${userInfo?.firstname} ${userInfo?.lastname} "
            glide.load(userInfo?.avatar)
                .circleCrop()
                .override(500, 500)
                .into(img_api)

        }

    }

}




interface TaskService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String?): Response<String>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("tasks/{id}")
    suspend fun updateTask(@Body task: Task, @Path("id") id: String? = task.id): Response<Task>
}