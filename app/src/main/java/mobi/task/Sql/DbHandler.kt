package mobi.task.Sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


private val DN = "hotel"
private val DV = 1
private val TN = "rooms"
private val LOGIN = "login"
private val BOOKING = "booking"


private val ID = "id"
private val USER = "user"
private val HOWMANYBKD = "howmanybkd"

private val USERNAME = "username"
private val PASSWORD = "password"

private val DATE="date"
private val BOOKEDROOMS="bookedrooms"


class DbHandler(context: Context) : SQLiteOpenHelper(context, DN, null, 1) {



    override fun onCreate(db: SQLiteDatabase?) {

        val query =
            "CREATE TABLE $TN($ID INTEGER PRIMARY KEY AUTOINCREMENT,$USER TEXT,$HOWMANYBKD TEXT)"
        val query1 =
            "CREATE TABLE $LOGIN($ID INTEGER PRIMARY KEY AUTOINCREMENT,$USERNAME TEXT,$PASSWORD TEXT)"


        val Booking =
            "CREATE TABLE $BOOKING($ID INTEGER PRIMARY KEY AUTOINCREMENT,$USERNAME TEXT,$DATE TEXT,$BOOKEDROOMS TEXT)"


        db!!.execSQL(query)
        db!!.execSQL(query1)
        db!!.execSQL(Booking)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TN");
        db!!.execSQL("DROP TABLE IF EXISTS $LOGIN");
        db!!.execSQL("DROP TABLE IF EXISTS $BOOKING");

        onCreate(db);
    }

    fun insert(user: String?, rooms: String?): String? {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER, user)
        values.put(HOWMANYBKD, rooms)
        db.insert(TN, null, values)
        db.close()
        return null
    }

    fun Register(username: String?, password: String?): String? {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USERNAME, username)
        values.put(PASSWORD, password)
        db.insert(LOGIN, null, values)
        db.close()
        return null
    }

    fun Hotelinsert(username: String?, date: String?, rooms: String?): String? {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USERNAME, username)
        values.put(DATE, date)
        values.put(BOOKEDROOMS, rooms)

        db.insert(BOOKING, null, values)
        db.close()
        return null
    }





    fun getlogin(Username: String): String? {
        val query = "SELECT * FROM $LOGIN WHERE $USERNAME =?"
        var result = ""
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, arrayOf(Username))
        cursor.moveToFirst()
        while (cursor.isAfterLast() === false) {
            result += cursor.getString(0).toString() + " " + cursor.getString(1) + "\n"
            cursor.moveToNext()
        }
        db.close()
        return result
    }

    fun checklogin(Username: String, pass: String?): String? {
        val query = "SELECT * FROM $LOGIN WHERE $USERNAME =? AND $PASSWORD =?"
        var result = ""
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, arrayOf(Username, pass))
        cursor.moveToFirst()
        while (cursor.isAfterLast() === false) {
            result += cursor.getString(0).toString() + " " + cursor.getString(1) + "\n"
            cursor.moveToNext()
        }
        db.close()
        return result
    }

    fun checklogin1(): String? {
        val query = "SELECT * FROM $LOGIN"
        var result = ""
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        while (cursor.isAfterLast() === false) {
            result += cursor.getString(0).toString() + " " + cursor.getString(1) + "\n"
            cursor.moveToNext()
        }
        db.close()
        return result
    }


    fun checkhotelavailable(date: String): String? {
     //   val query = "SELECT * FROM $BOOKING WHERE $DATE =?"

        val query =  "SELECT $ID, $USERNAME, $DATE, SUM($BOOKEDROOMS) as $BOOKEDROOMS FROM $BOOKING WHERE $DATE = ?"
        Log.d("TAG", "checkhotelavailable: $query")
        var result = ""
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, arrayOf(date))
        if (cursor.moveToFirst()) {
            do {

                val s3 = cursor.getString(cursor.getColumnIndex(BOOKEDROOMS))
if (s3!=null) {
    result = s3

}
            } while (cursor.moveToNext())
        }
        db.close()
        return result
    }



}