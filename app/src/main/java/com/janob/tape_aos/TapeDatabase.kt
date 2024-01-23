package com.janob.tape_aos



import android.annotation.SuppressLint
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities=[Reply::class, Tape::class, Song::class, IncludedSong::class, LoginUser::class, User::class], version = 4)
@TypeConverters(StringListConverters::class, TapeListConverters::class)
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.text.Collator.PRIMARY


abstract class TapeDatabase : RoomDatabase(){

    //필요한 Dao 추가
    abstract fun replyDao() : ReplyDao
    abstract fun tapeDao() : TapeDao
    abstract fun songDao() : SongDao
    abstract fun songDaos() : SongDaos
    abstract fun loginuserDao() : LoginUserDao


    abstract fun userDao() : UserDao
    companion object{


        var instance: TapeDatabase? = null


        fun Instance(context: Context): TapeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TapeDatabase::class.java,
                    "user-database"
                )
                    //.addMigrations(migration_1_2, migration_2_3, migration_3_4, migration_4_5)
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }


        val migration_1_2: Migration = object : Migration(1, 2) {
            @SuppressLint("Range")
            override fun migrate(database: SupportSQLiteDatabase) {
                // Check if 'userID' column exists in LoginUser table
                val cursor = database.query("PRAGMA table_info(LoginUser)")
                var userIDColumnExists = false

                if (cursor.moveToFirst()) {
                    do {
                        val columnName = cursor.getString(cursor.getColumnIndex("name"))
                        if (columnName == "userID") {
                            userIDColumnExists = true
                            break
                        }
                    } while (cursor.moveToNext())
                }
                cursor.close()

                // Add 'userID' column if it doesn't exist in LoginUser table
                if (!userIDColumnExists) {
                    database.execSQL("ALTER TABLE LoginUser ADD COLUMN userID INTEGER DEFAULT 0")
                }

                // Create LoginUser_new table with 'userID' column
                database.execSQL("CREATE TABLE IF NOT EXISTS LoginUser_new " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "nickname TEXT, " +
                        "profileimg TEXT, " +
                        "profileintro TEXT, " +
                        "userID INTEGER DEFAULT 0, " +
                        "token TEXT)")

                // Copy data from LoginUser to LoginUser_new
                database.execSQL("INSERT INTO LoginUser_new (id, nickname, profileimg, profileintro, userID, token) " +
                        "SELECT id, nickname, profileimg, profileintro, userID, token FROM LoginUser")

                // Drop the old LoginUser table
                database.execSQL("DROP TABLE IF EXISTS LoginUser")

                // Rename LoginUser_new to LoginUser
                database.execSQL("ALTER TABLE LoginUser_new RENAME TO LoginUser")
            }
        }

        val migration_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val cursor = database.query("SELECT * FROM LoginUser LIMIT 0")
                val hasNicknameColumn = cursor.getColumnIndex("nickname") != -1
                cursor.close()
                if (!hasNicknameColumn) {
                    database.execSQL("ALTER TABLE LoginUser ADD COLUMN nickname TEXT DEFAULT null")
                }
            }
        }

        val migration_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val cursor = database.query("SELECT * FROM LoginUser LIMIT 0")
                val hasprofileimgColumn = cursor.getColumnIndex("nickname") != -1
                cursor.close()
                if (!hasprofileimgColumn) {
                    database.execSQL("ALTER TABLE LoginUser ADD COLUMN profileimg TEXT DEFAULT null")
                }
            }
        }

        val migration_4_5: Migration = object : Migration(4, 5) {

            override fun migrate(database: SupportSQLiteDatabase) {
                val cursor = database.query("SELECT * FROM LoginUser LIMIT 0")
                val hasprofileintroColumn = cursor.getColumnIndex("nickname") != -1
                cursor.close()
                if (!hasprofileintroColumn) {
                    database.execSQL("ALTER TABLE LoginUser ADD COLUMN profileintro TEXT DEFAULT null")
                }
            }

        }
    }
}