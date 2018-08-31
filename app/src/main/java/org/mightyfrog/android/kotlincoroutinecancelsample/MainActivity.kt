package org.mightyfrog.android.kotlincoroutinecancelsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * @author Shigehiro Soejima
 */
class MainActivity : AppCompatActivity() {
//    private val rootJob = Job()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener {
//            rootJob.cancel()
//            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
//        }
//
//        launch(UI, parent = rootJob) {
//            textView.text = withContext(DefaultDispatcher) { load() }
//        }
//    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            job?.cancel()
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }

        job = launch(UI) {
            textView.text = withContext(DefaultDispatcher) { load() }
        }
    }

    private fun load(): String {
        Thread.sleep(10 * 1000L)
        return "Hello, Coroutine!"
    }
}
