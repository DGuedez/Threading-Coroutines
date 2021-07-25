package com.david.threading_coroutines

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL

/*
* Creating a thread using the initial way
* */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image =  findViewById<ImageView>(R.id.image_view)

        //Runnable object is necessary to execute the thread operation

        //looper is used later on comments
        val looper = mainLooper //or Looper.getMainLooper
        Thread(Runnable {
            val url = URL("http://s1.picswalls.com/wallpapers/2014/08/08/home-hd-wallpaper_015622481_147.jpg")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            //connection type
            val inputStream = connection.inputStream

            //decode image
            val bitmap = BitmapFactory.decodeStream(inputStream)

            /*ways to communicate result to UI /Main Thread
            * 1. From Activity we can use RunOnUiThread methods
            * 2. Usign loopers to loop through thread signals /messages and act upon it
            *  2.1 Loopers must be associated to a thread. E.g main Looper
            *  2.2 To communicate looper with the thread we need a handler object and post the result
            * */

            //1.
            //runOnUiThread { image.setImageBitmap(bitmap) }

            //2.
            Handler(looper).post { image.setImageBitmap(bitmap) }

            //threads are idle by default so they need to be started
        }).start()

    }
}