package com.jared.proj2;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sam on 11/29/2016.
 */
public class PasswordGate extends AppCompatActivity {
    Button btSubmitPass;
    EditText etEnterPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btSubmitPass = (Button) findViewById(R.id.B_button_submit);
        etEnterPass = (EditText) findViewById(R.id.ET_password_entry);
    }

    public void eventHandler(View v){
        String password = getResources().getString(R.string.password);

        if(etEnterPass.getText().toString().equals(password)){
            Intent intent = new Intent(this, MessageList.class);
            startActivity(intent);
        }
    }
}
