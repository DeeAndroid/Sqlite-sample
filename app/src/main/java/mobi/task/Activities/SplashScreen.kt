package mobi.task.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import mobi.task.MainActivity
import mobi.task.R

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)




            Handler().postDelayed({
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()

                //the current activity will get finished.
            }, 2000)

        }




}