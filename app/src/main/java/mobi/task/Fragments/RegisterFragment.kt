package mobi.task.Fragments

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_register.view.*
import mobi.task.R
import mobi.task.Sql.DbHandler


class RegisterFragment : Fragment() {

    var db: DbHandler? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_register, container, false)


        db= DbHandler(context!!);


        view.loginpage.setOnClickListener {
            val activity = context as AppCompatActivity
            val myFragment: Fragment = LoginFragment()
            myFragment.enterTransition = Slide(Gravity.TOP)
            myFragment.exitTransition = Slide(Gravity.BOTTOM)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_layout, myFragment).commit()
        }

        view.registerbtn.setOnClickListener {

            if (view.userid.text.toString().isNullOrEmpty()){
                view.userid.error="Enter User Id"
                view.userid.requestFocus()
            }
            if (view.password.text.toString().isNullOrEmpty()){
                view.password.error="Enter Password"
                view.userid.requestFocus()
            }

            if (!view.userid.text.toString().isNullOrEmpty() && !view.password.text.toString().isNullOrEmpty()){



      //     db!!.Register( view.userid.text.toString(),view.password.text.toString())
                Log.d("TAG", "onCreateView: ${ db!!.getlogin(view.userid.text.toString())}")

          if ( db!!.getlogin(view.userid.text.toString()).isNullOrEmpty()){

              db!!.Register( view.userid.text.toString(),view.password.text.toString())
              Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
              view.userid.text.clear()
              view.password.text.clear()
          }else{
              Toast.makeText(context, "Already Exist Please Login", Toast.LENGTH_SHORT).show()
              view.userid.text.clear()
              view.password.text.clear()
          }


            }


        }

        return view
    }


}