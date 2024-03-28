import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.bingoassistant.Bingo
import com.example.bingoassistant.BingoEntry

class BingoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Add a context property
    private val context: Context = context.applicationContext
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BingoAssistant.db"

        // Table names
        const val TABLE_BINGOS = "Bingos"
        private const val TABLE_BINGO_ENTRIES = "BingoEntries"

        // Common column names
        private const val COLUMN_ID = "id"

        // Bingos table columns
        const val COLUMN_PRIZE = "prize"
        const val COLUMN_PRICE = "price"
        private const val COLUMN_IMAGE_PATH = "image_path"

        // BingoEntries table columns
        private const val COLUMN_BINGO_ID = "bingo_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_ENTRY_NUMBER = "entry_number"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create Bingos table if it doesn't exist
        val createBingosTableQuery = """
        CREATE TABLE IF NOT EXISTS $TABLE_BINGOS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_PRIZE TEXT,
            $COLUMN_PRICE INTEGER,
            $COLUMN_IMAGE_PATH BLOB
        )
    """.trimIndent()
        db.execSQL(createBingosTableQuery)

        // Create BingoEntries table if it doesn't exist
        val createBingoEntriesTableQuery = """
        CREATE TABLE IF NOT EXISTS $TABLE_BINGO_ENTRIES (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_BINGO_ID INTEGER,
            $COLUMN_NAME TEXT,
            $COLUMN_ENTRY_NUMBER INTEGER,
            FOREIGN KEY ($COLUMN_BINGO_ID) REFERENCES $TABLE_BINGOS($COLUMN_ID)
        )
    """.trimIndent()
        db.execSQL(createBingoEntriesTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BINGOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BINGO_ENTRIES")

        // Create tables again
        onCreate(db)
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_BINGOS")
        db.execSQL("DELETE FROM $TABLE_BINGO_ENTRIES")
        db.close()
    }

    @SuppressLint("Range")
    fun getAllBingos(): List<Bingo> {
        val bingos = mutableListOf<Bingo>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_BINGOS", null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(COLUMN_ID))
                val prize = it.getString(it.getColumnIndex(COLUMN_PRIZE))
                val price = it.getDouble(it.getColumnIndex(COLUMN_PRICE))
                val bingo = Bingo(id, prize, price)
                bingos.add(bingo)
            }
        }
        return bingos
    }

    @SuppressLint("Range")
    fun getAllBingoEntries(): List<BingoEntry> {
        val bingoEntries = mutableListOf<BingoEntry>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_BINGO_ENTRIES", null)
        cursor.use {
            while (it.moveToNext()) {
                val bingoId = it.getInt(it.getColumnIndex(COLUMN_BINGO_ID))
                val name = it.getString(it.getColumnIndex(COLUMN_NAME))
                val entryNumber = it.getInt(it.getColumnIndex(COLUMN_ENTRY_NUMBER))
                val bingoEntry = BingoEntry(bingoId, entryNumber, name)
                bingoEntries.add(bingoEntry)
            }
        }
        return bingoEntries
    }

    fun insertBingoEntry(entry: BingoEntry) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BINGO_ID, entry.bingoId)
            put(COLUMN_NAME, entry.name)
            put(COLUMN_ENTRY_NUMBER, entry.entryNumber)
        }
        db.insert(TABLE_BINGO_ENTRIES, null, values)
        db.close()
    }

    fun updateBingoEntry(bingoEntry: BingoEntry) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, bingoEntry.name)
            // Add other columns to update if needed
        }
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(bingoEntry.bingoId.toString())
        db.update(TABLE_BINGO_ENTRIES, contentValues, selection, selectionArgs)
        db.close()
    }

    fun saveChangesToEntries(bingoEntries: List<BingoEntry>) {
        // Iterate over the list of bingo entries and update the database with any changes
        for (entry in bingoEntries) {
            // Update the entry in the database
            updateBingoEntry(entry)
        }
        // Notify the user that changes have been saved (optional)
        Toast.makeText(context, "Changes saved successfully", Toast.LENGTH_SHORT).show()
    }
}
