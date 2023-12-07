package com.myfinalproject.hydrobill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class RecordManager extends SQLiteOpenHelper {

    public RecordManager(Context context) {
        super(context, "BarangayDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the BarangayTable to store barangay names
        db.execSQL("CREATE TABLE BarangayTable (BarangayName TEXT PRIMARY KEY)");

        // Other initialization logic for your database

        // Add sample data to BarangayTable for testing
        db.execSQL("INSERT INTO BarangayTable (BarangayName) VALUES ('Barangay_1')");
        db.execSQL("INSERT INTO BarangayTable (BarangayName) VALUES ('Barangay_2')");

        // Create consumer tables for each barangay
        Cursor cursor = db.rawQuery("SELECT * FROM BarangayTable", null);
        while (cursor.moveToNext()) {
            String barangayName = cursor.getString(cursor.getColumnIndex("BarangayName"));

            // Replace spaces with underscores and remove other invalid characters
            String tableName = barangayName.replaceAll("[^a-zA-Z0-9_]", "_");

            // Create a consumer table for the newly created barangay
            createConsumerTable(db, barangayName);
        }
        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate tables on upgrade if needed
        db.execSQL("DROP TABLE IF EXISTS BarangayTable");

        // Call onCreate to recreate the tables
        onCreate(db);
    }

    public boolean saveBarangayTable(String BarangayName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BarangayName", BarangayName);

        // Replace spaces with underscores and remove other invalid characters
        String tableName = BarangayName.replaceAll("[^a-zA-Z0-9_]", "_");

        long result = db.insert("BarangayTable", null, contentValues);

        // Create a consumer table for the newly created barangay
        createConsumerTable(db, BarangayName);

        return result != -1;
    }

    public Boolean updateBarangay(String BarangayName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BarangayName", BarangayName);
        Cursor cursor = db.rawQuery("SELECT * from BarangayTable where BarangayName = ?", new String[]{BarangayName});
        if (cursor.getCount() > 0) {
            long result = db.update("BarangayTable", contentValues, "BarangayName=?", new String[]{BarangayName});
            return result != 1;
        } else {
            return false;
        }
    }

    public void createConsumerTable(SQLiteDatabase db, String barangayName) {
        // Replace spaces with underscores and remove other invalid characters
        String tableName = barangayName.replaceAll("[^a-zA-Z0-9_]", "_");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "Consumers (id INTEGER PRIMARY KEY, name TEXT)");
    }

    public static final String COLUMN_NAME = "name";

    public boolean saveConsumer(String consumerName, String barangayName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, consumerName);

        // Replace spaces with underscores and remove other invalid characters
        String tableName = barangayName.replaceAll("[^a-zA-Z0-9_]", "_");

        // Check if the corresponding barangay exists
        if (checkIfBarangayExists(barangayName)) {
            try {
                long result = db.insert(tableName + "Consumers", null, contentValues);
                return result != -1;
            } catch (Exception e) {
                // Handle the exception (e.g., log or display an error message)
                e.printStackTrace();
                return false;
            } finally {
                db.close(); // Close the database connection
            }
        } else {
            // Handle the case where the barangay doesn't exist
            return false;
        }
    }



    public Cursor getConsumersForBarangay(String barangayName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = barangayName.replaceAll("[^a-zA-Z0-9_]", "_");

        // Modify this query to retrieve consumers for the given barangay
        String query = "SELECT * FROM " + tableName + "Consumers";

        return db.rawQuery(query, null);
    }


    public void updateConsumer(String consumer, String barangay) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Replace spaces with underscores and remove other invalid characters
        String tableName = barangay.replaceAll("[^a-zA-Z0-9_]", "_");

        db.execSQL("UPDATE " + tableName + "Consumers SET name = '" + consumer + "' WHERE id = 1");
    }

    public void deleteConsumer(String consumer, String barangay) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Replace spaces with underscores and remove other invalid characters
        String tableName = barangay.replaceAll("[^a-zA-Z0-9_]", "_");

        db.execSQL("DELETE FROM " + tableName + "Consumers WHERE id = 1");
    }

    public boolean deleteBarangay(String BarangayName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Replace spaces with underscores and remove other invalid characters
        String tableName = BarangayName.replaceAll("[^a-zA-Z0-9_]", "_");

        // Delete the barangay from BarangayTable
        int result = db.delete("BarangayTable", "BarangayName = ?", new String[]{BarangayName});

        // Delete the corresponding consumers table
        if (result != -1) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName + "Consumers");
        }

        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BarangayTable", null);
        return cursor;
    }

    public boolean checkIfBarangayExists(String barangayName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BarangayTable WHERE BarangayName = ?", new String[]{barangayName});

        boolean exists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return exists;
    }
}
