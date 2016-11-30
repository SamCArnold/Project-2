package com.jared.proj2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sam on 11/29/2016.
 */
public class MessageList extends Activity implements AdapterView.OnItemClickListener {
    private static MessageList inst;
    Button btCompose;
    ArrayAdapter mArrayAdapter;
    ArrayList<String> encryptedMessagesList = new ArrayList<String>();
    ListView encryptedListView;

    @Override
    public void onStart(){
        super.onStart();
        inst = this;
    }

    public static MessageList instance(){
        return inst;
    }

    public void refresh(){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://sms/inbox"),null,
                null,null,null);
        int indexOfBody = cursor.getColumnIndex("body");
        int indexOfAddress = cursor.getColumnIndex("address");
        long timeInMillis = cursor.getColumnIndex("date");
        Date date = new Date(timeInMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        String dateText = simpleDateFormat.format(date);

        if(indexOfBody < 0 || !cursor.moveToFirst()) return;

        mArrayAdapter.clear();

        do{
            String string = cursor.getString(indexOfAddress) + " at" +
                    "\n" + cursor.getString(indexOfBody) + dateText + "\n";
            mArrayAdapter.add(string);
        }while (cursor.moveToNext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        encryptedListView = (ListView) findViewById(R.id.EncryptedSMSList);
        btCompose = (Button) findViewById(R.id.bCompose);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                encryptedMessagesList);
        encryptedListView.setAdapter(mArrayAdapter);
        encryptedListView.setOnItemClickListener(this);

        refresh();
    }

    public void updateList(final String message){
        mArrayAdapter.insert(message, 0);
        mArrayAdapter.notifyDataSetChanged();
    }

    public void composeClick(View v){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id){
        Toast.makeText(this, "List item clicked", Toast.LENGTH_LONG).show();
    }
}
