package gold.dev.com.githubusersearch.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.databinding.ObservableField
import gold.dev.com.githubusersearch.model.Users

class SQLiteHelper(private val context: Context, private val flag: String) :
    SQLiteOpenHelper(context, if (flag == "api") DATABASE_NAME_API else DATABASE_NAME_LOCAL, null,
        DATABASE_VERSION
    ) {

    companion object {
        const val DATABASE_VERSION: Int = 1
        const val DATABASE_NAME_API: String = "Api.db"
        const val DATABASE_NAME_LOCAL: String = "Local.db"
        const val KEY_ID: String = "id"
        const val TABLE_NAME_API: String = "API"
        const val TABLE_NAME_LOCAL: String = "LOCAL"
        const val USER_AVARTAR_URL: String = "avatarUrl"
        const val USER_NAME: String = "login"
        const val IS_LIKE: String = "like"
        val COLUMNS: Array<String> = arrayOf(
            KEY_ID,
            USER_AVARTAR_URL,
            USER_NAME,
            IS_LIKE
        )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATION_TABLE_API: String =
            ("CREATE TABLE $TABLE_NAME_API ( id INTEGER PRIMARY KEY AUTOINCREMENT, avatarUrl TEXT, login TEXT, like INTEGER DEFAULT 0)")

        val CREATION_TABLE_LOCAL: String =
            ("CREATE TABLE $TABLE_NAME_LOCAL ( id INTEGER PRIMARY KEY AUTOINCREMENT, avatarUrl TEXT, login TEXT, like INTEGER DEFAULT 0)")

        db?.execSQL(CREATION_TABLE_API)
        db?.execSQL(CREATION_TABLE_LOCAL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_API")
            it.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_LOCAL")
            this.onCreate(it)
        }
    }

    fun deleteData() {
        val db = this.writableDatabase
        when (flag) {
            "api" -> db.delete(TABLE_NAME_API, null, null)
            "local" -> db.delete(TABLE_NAME_LOCAL, null, null)
        }
    }

    fun saveData(data: List<Users.Item>) {
        val db = this.writableDatabase

        data.forEach {
            ContentValues().apply {
                put(USER_AVARTAR_URL, it.avatarUrl)
                put(USER_NAME, it.login)
                put(IS_LIKE, if (it.like.get()!!) 1 else 0)
            }.also {
                when (flag) {
                    "api" -> db.insert(TABLE_NAME_API, null, it)
                    "local" -> db.insert(TABLE_NAME_LOCAL, null, it)
                }
            }
        }
    }

    fun getStoredData(): ArrayList<Users.Item> {
        val db = this.readableDatabase
        val cursor = db.query(
            if (flag == "api") TABLE_NAME_API else TABLE_NAME_LOCAL,
            COLUMNS,
            null,
            null,
            null,
            null,
            null
        )

        val temp = ArrayList<Users.Item>()
        //val selectQuery = "SELECT * FROM $TABLE_NAME"

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Users.Item(
                    avatarUrl = cursor.getString(1),
                    login = cursor.getString(2),
                    like = ObservableField(cursor.getInt(3) == 1)
                ).also { temp.add(it) }
            } while (cursor.moveToNext())
        }

        cursor.close()
        return temp
    }

}