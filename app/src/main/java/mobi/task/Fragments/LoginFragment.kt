package mobi.task.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.password
import kotlinx.android.synthetic.main.fragment_login.view.userid
import kotlinx.android.synthetic.main.fragment_register.view.*
import mobi.task.R
import mobi.task.Sql.DbHandler


class LoginFragment : Fragment() {
    var db: DbHandler? = null
    val sharedPrefFile = "sharedpreference"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)


        db= DbHandler(context!!);
        val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        view.register.setOnClickListener {
            val activity = context as AppCompatActivity
            val myFragment: Fragment = RegisterFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_layout, myFragment).commit()
        }


        view.loginBtn.setOnClickListener {

            if (view.userid.text.toString().isNullOrEmpty()){
                view.userid.error="Enter User Id"
                view.userid.requestFocus()
            }
            if (view.password.text.toString().isNullOrEmpty()){
                view.password.error="Enter Password"
                view.userid.requestFocus()
            }

            if (!view.userid.text.toString().isNullOrEmpty() && !view.password.text.toString().isNullOrEmpty()){

                var userpass=view.password.text.toString()

                Log.d("TAG", "onCreateView:checking ${ db!!.checklogin1(view.userid.text.toString())}")

                if (view.password.text.toString()==   db!!.decrypt(db!!.checklogin1(view.userid.text.toString()).toString()) ) {



                 /*   if (db!!.checklogin(view.userid.text.toString(), view.password.text.toString())
                            .isNullOrEmpty()
                    ) {

                        Toast.makeText(context, "check Username and Password", Toast.LENGTH_SHORT)
                            .show()

                    }

                    else {*/
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                        val sharedPreferences: SharedPreferences =
                            this.activity!!.getSharedPreferences(
                                sharedPrefFile,
                                Context.MODE_PRIVATE
                            )
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("userid", view.userid.text.toString())
                        editor.apply()
                        editor.commit()

                        val activity = context as AppCompatActivity
                        val myFragment: Fragment = RoomBookingFragment()
                        myFragment.enterTransition = Slide(Gravity.TOP)
                        myFragment.exitTransition = Slide(Gravity.BOTTOM)
                        activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.activity_main_layout, myFragment).commit()

                        view.userid.text.clear()
                        view.password.text.clear()

                    }
                else{
                    Toast.makeText(context!!, "check user name and password", Toast.LENGTH_SHORT).show()
                }

            }




        }

        return view
    }


}