package org.mightyfrog.android.kotlincoroutinecancelsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

/**
 * @author Shigehiro Soejima
 */
@SuppressLint("LogNotTimber")
class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job: Job = Job()

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
            job.cancel()
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }

        launch(onCompletion = completionHandler) {
            textView.text = withContext(Dispatchers.Default) { load() }
            // lines after this will not be executed once the coroutine is canceled
            Log.d("coroutine", "One")
            Log.d("coroutine", "Two")
            Log.d("coroutine", "Three")
            textView.text = withContext(Dispatchers.Default) { load2() }
        }
    }

    override fun onDestroy() {
        job.cancel()

        super.onDestroy()
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private fun load(): String {
        Thread.sleep(5 * 1000L)
        return "Hello, Coroutine!"
    }

    private fun load2(): String {
        Thread.sleep(5 * 1000L)
        return "Hello, Coroutine again!"
    }
}
