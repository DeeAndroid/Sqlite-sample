package mobi.task.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_login.view.*
import mobi.task.R


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)

        view.register.setOnClickListener {
            val activity = context as AppCompatActivity
            val myFragment: Fragment = RegisterFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_layout, myFragment).commit()
        }

        return view
    }


}