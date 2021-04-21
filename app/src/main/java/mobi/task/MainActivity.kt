package mobi.task

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import mobi.task.Fragments.RegisterFragment
import mobi.task.Fragments.RoomBookingFragment
import mobi.task.Sql.DbHandler


class MainActivity : AppCompatActivity() {
    var db: DbHandler? = null
    val sharedPrefFile = "sharedpreference"
    var userid: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPreferences: SharedPreferences = this@MainActivity!!.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        userid = sharedPreferences.getString("userid", "")

        if (userid.isNullOrEmpty()){
            loadFragment(RegisterFragment())
        }else if (userid!=null){
            loadFragment(RoomBookingFragment())
        }



        db= DbHandler(applicationContext);

     //   db!!.insert("Dee" ,"3")
      //     db!!.Register("Dee" ,"adsfasdf")
    }


    private fun loadFragment(fragment: Fragment): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_layout, fragment)
                .commit()
            return true
        }
        return false
    }


}