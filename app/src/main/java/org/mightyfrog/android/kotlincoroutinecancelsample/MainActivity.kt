package org.mightyfrog.android.kotlincoroutinecancelsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

/**
 * @author Shigehiro Soejima
 */
@SuppressLint("LogNotTimber")
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

    private val completionHandler = object : CompletionHandler {
        override fun invoke(cause: Throwable?) {
            Log.d("coroutine", "Completed: $cause")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            Log.d("coroutine", "Canceled")
            job?.cancel()
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }

        job = launch(UI, onCompletion = completionHandler) {
            textView.text = withContext(DefaultDispatcher) { load() }
            // lines after this will not be executed once the coroutine is canceled
            Log.d("coroutine", "One")
            Log.d("coroutine", "Two")
            Log.d("coroutine", "Three")
            textView.text = withContext(DefaultDispatcher) { load2() }
        }
    }

    private fun load(): String {
        Thread.sleep(5 * 1000L)
        return "Hello, Coroutine!"
    }

    private fun load2(): String {
        Thread.sleep(5 * 1000L)
        return "Hello, Coroutine again!"
    }
}
