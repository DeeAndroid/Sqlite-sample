package mobi.task.Fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_room_booking.view.*
import mobi.task.R
import mobi.task.Sql.DbHandler
import java.util.*

class RoomBookingFragment : Fragment() {

    var num: Int? = 1
    var maxroom: Int? = 10
    val sharedPrefFile = "sharedpreference"

    var dayOfMonth: Int? = null
    var year: Int? = null
    var month: Int? = null
    var datePickerDialog: DatePickerDialog? = null
    var calendar: Calendar? = null
    var selecteddate: String? = ""
    var userid: String? = ""
    var db: DbHandler? = null
    var available: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_room_booking, container, false)


        if (num!! <= 0) {
            num = 0
        }

        db = DbHandler(context!!);
        var sharedPreferences: SharedPreferences = context!!.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        userid = sharedPreferences.getString("userid", "")

        view.addbutton.setOnClickListener {


            if ((maxroom!!.toInt() - (db!!.checkhotelavailable(selecteddate!!))!!.toInt()) == -10){
                Toast.makeText(context, "Rooms Not Available", Toast.LENGTH_SHORT).show()
            }else {

                if (selecteddate.isNullOrEmpty()) {
                    Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show()
                }
                if (!selecteddate.isNullOrEmpty()) {
                    if (num!! <= 0) {
                        num = 1
                        view.quantity.text = (num.toString())
                    }

                    view.addbutton.visibility = View.GONE
                    view.layio.visibility = View.VISIBLE
                }
            }

        }

        view.plus.setOnClickListener {

            //  addtocart()

            if (num!!.toInt() < maxroom!!.toInt()) {
                if (num!! >= 0) {
                    num = num?.inc()
                    view.quantity.text = (num.toString())
                }
            } else {
                Toast.makeText(context, "Only $maxroom Rooms Available", Toast.LENGTH_SHORT).show()
            }
        }

        view.minus.setOnClickListener {

            //    addtocart()

            if (num!! > 0) {
                num = num?.dec()
                view.quantity.text = (num.toString())
            }

            if (num!! <= 0) {
                view.addbutton.visibility = View.VISIBLE
                view.layio.visibility = View.GONE

            }
        }

        view.booknow.setOnClickListener {



            if (num!!.toInt() < 1) {
                Toast.makeText(
                    context!!,
                    "Please Select min 1 Rooms To Proceed ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (selecteddate.isNullOrEmpty()) {
                Toast.makeText(
                    context!!,
                    "Please Select Date To Proceed ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!(num!!.toInt() < 1) && !(selecteddate.isNullOrEmpty())) {
                db!!.Hotelinsert(userid!!, selecteddate, num!!.toString())
                Toast.makeText(context!!, "Success", Toast.LENGTH_SHORT).show()
                num=0
                maxroom=10
                view.addbutton.visibility=View.VISIBLE
                view.layio.visibility=View.GONE
                getdb()

            }
        }

        view.selectdate.isFocusable = false;
        view.selectdate.isClickable = true;
        view.selectdate.setOnClickListener {
            maxroom = 10

            calendar = Calendar.getInstance()
            year = calendar!!.get(Calendar.YEAR)
            month = calendar!!.get(Calendar.MONTH)
            dayOfMonth = calendar!!.get(Calendar.DAY_OF_MONTH)
            datePickerDialog = DatePickerDialog(
                context!!,
                { datePicker, year, month, day ->

                    view.selectdate.setText("$day-" + (month + 1) + "-" + year)
                    selecteddate = ("$day-" + (month + 1) + "-" + year)
                    view.quantity.text = ("1")
                    num=1
                    if ((db!!.checkhotelavailable(selecteddate!!)).isNullOrEmpty()) {
                        db!!.Hotelinsert("0", selecteddate, "0".toString())
                    } else {
                        getdb()
                    }


                }, year!!, month!!, dayOfMonth!!
            )
            // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog?.show()
            datePickerDialog?.getDatePicker()?.setMinDate(System.currentTimeMillis());
        }

        return view
    }

    private fun getdb() {
        if (db!!.checkhotelavailable(selecteddate!!).isNullOrEmpty()) {
            Log.d("TAG", "onCreateView: illa")
            available = true
            view!!.addbutton.isEnabled = true
            view!!.booknow.isEnabled = true
        } else {

            view!!.availablerooms.text =
                "${maxroom!!.toInt() - (db!!.checkhotelavailable(selecteddate!!))!!.toInt()}/10"
            maxroom = maxroom!!.toInt() - (db!!.checkhotelavailable(selecteddate!!))!!.toInt()
        }
    }

}