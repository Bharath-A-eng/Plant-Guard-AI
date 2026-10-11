package com.rymec.plantguardai;

import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HistoryModel> historyList;
    HistoryAdapter adapter;
    HistoryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleHelper.loadLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyList = new ArrayList<>();

        adapter = new HistoryAdapter(historyList);

        recyclerView.setAdapter(adapter);

        dbHelper = new HistoryDatabaseHelper(this);

        loadHistory();
    }

    private void loadHistory() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                HistoryDatabaseHelper.TABLE_HISTORY,
                null,
                null,
                null,
                null,
                null,
                HistoryDatabaseHelper.COL_ID + " DESC"
        );

        while (cursor.moveToNext()) {

            HistoryModel model = new HistoryModel(

                    cursor.getInt(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_ID)),

                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_IMAGE)),

                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_PLANT)),

                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_DISEASE)),

                    cursor.getFloat(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_CONFIDENCE)),

                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_DATE)),

                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COL_TIME))

            );



            historyList.add(model);
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}