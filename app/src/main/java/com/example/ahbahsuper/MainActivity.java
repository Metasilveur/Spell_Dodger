package com.example.ahbahsuper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.xml.xpath.XPathException;

public class MainActivity extends AppCompatActivity implements JoyStick.JoyStickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //JoyStick joyStick = new JoyStick(this);
        setContentView(R.layout.activity_main);
        //setContentView(joyStick);
    }

    @Override
    public void onJoyStickMoved(float xPercent, float yPercent, int source) {
        Log.d("Main method", "X percent " + xPercent + " Y percent : " + yPercent);
    }
}
