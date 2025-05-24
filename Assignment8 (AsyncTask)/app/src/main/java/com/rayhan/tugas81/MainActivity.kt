package com.rayhan.tugas81

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.rayhan.tugas81.databinding.ActivityMainBinding
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var myVariable = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDoAsync.setOnClickListener {
            val task = MyAsyncTask(this)
            task.execute(10)
        }
    }

    companion object {
        class MyAsyncTask internal constructor(context: MainActivity) : AsyncTask<Int, String, String>() {

            private var resp: String? = null
            private val activityReference: WeakReference<MainActivity> = WeakReference(context)

            override fun onPreExecute() {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                activity.binding.progressBar.visibility = View.VISIBLE
            }

            override fun doInBackground(vararg params: Int?): String? {
                publishProgress("Thread mulai berhenti") // Calls onProgressUpdate()
                try {
                    val time = params[0]?.times(1000)
                    time?.toLong()?.let { Thread.sleep(it / 2) }

                    publishProgress("Setengah jalan...") // Calls onProgressUpdate()
                    time?.toLong()?.let { Thread.sleep(it / 2) }

                    publishProgress("Thread aktif lagi") // Calls onProgressUpdate()
                    resp = "Android berhasrat selama " + params[0] + " detik"
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    resp = e.message
                } catch (e: Exception) {
                    resp = e.message
                }
                return resp
            }

            override fun onPostExecute(result: String?) {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                activity.binding.progressBar.visibility = View.GONE
                activity.binding.textView.text = result
                activity.myVariable = 100
            }

            override fun onProgressUpdate(vararg text: String?) {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return

                Toast.makeText(activity, text.firstOrNull(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}