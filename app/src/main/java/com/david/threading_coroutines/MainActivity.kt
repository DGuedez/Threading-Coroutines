package com.david.threading_coroutines

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

/*
* Fetching data from url while using coroutines
* The coroutine framework enhance the tread management. Using it more efficiently via function cooperation
* All the async/background operations could be treated as sync
* To start coroutines we need to use a coroutine scope to attach it to a life cycle to avoid leaks & a builder to start it
* Also we need to use Dispatchers objects to indicate in which thread we want to process the task
* */


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image = findViewById<ImageView>(R.id.image_view)
        Log.d("ThreadTask", Thread.currentThread().name)

        /*
        * the global scope live across all the app life cycle
        * launch is the default builder extension function to use coroutines. Also it uses a
          default thread pool limited by the number of processor cores of the device. (2,4,6)
        * if we use a dispatcher I.O the thread limit is increased to 64 or number of processor cores if higher
        * io dispatcher and default dispatcher shares threads
        * context inside launch constructor indicates a set of rules about how the coroutines will be constructed. Dispatchers are one part of it
        * */

        GlobalScope.launch(context = Dispatchers.IO) {
            Log.d("ThreadTask", Thread.currentThread().name)
            val url = URL("http://s1.picswalls.com/wallpapers/2014/08/08/home-hd-wallpaper_015622481_147.jpg")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            //connection type
            val inputStream = connection.inputStream

            //decode image
            val bitmap = BitmapFactory.decodeStream(inputStream)

            launch(Dispatchers.Main) {
                Log.d("ThreadTask", Thread.currentThread().name)
                image.setImageBitmap(bitmap)
            }

        }

    }
}