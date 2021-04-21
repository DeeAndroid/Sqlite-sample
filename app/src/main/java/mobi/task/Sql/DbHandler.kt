package mobi.task.Sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


private val DN = "hotel"
private val DV = 1
private val TN = "rooms"
private val LOGIN = "login"

private val ID = "id"
private val USER = "user"
private val HOWMANYBKD = "howmanybkd"

private val USERNAME = "username"
private val PASSWORD = "password"


class DbHandler(context: Context) : SQLiteOpenHelper(context, DN, null, 1) {



    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TN($ID INTEGER PRIMARY KEY AUTOINCREMENT,$USER TEXT,$HOWMANYBKD TEXT)"
        val query1 =
            "CREATE TABLE $LOGIN($ID INTEGER PRIMARY KEY AUTOINCREMENT,$USERNAME TEXT,$PASSWORD TEXT)"

        db!!.execSQL(query)
        db!!.execSQL(query1)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TN");
        db!!.execSQL("DROP TABLE IF EXISTS $LOGIN");

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


    fun getlogin(): String? {
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


}