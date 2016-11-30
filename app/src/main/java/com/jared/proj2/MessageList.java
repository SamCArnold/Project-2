package com.jared.proj2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Sam on 11/29/2016.
 */
public class MessageList extends Activity implements AdapterView.OnItemClickListener {
    private static MessageList inst;
    ListView receivedListView;
    Button btCompose;

    @Override
    public void onStart(){
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        receivedListView = (ListView) findViewById(R.id.EncryptedSMSList);
        btCompose = (Button) findViewById(R.id.bCompose);
    }

    public void composeClick(View v){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id){
        Toast.makeText(this, "List item clicked", Toast.LENGTH_LONG).show();
    }
}
