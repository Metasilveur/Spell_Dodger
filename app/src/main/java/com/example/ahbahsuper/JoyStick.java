package com.example.ahbahsuper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoyStick extends SurfaceView implements SurfaceHolder.Callback , View.OnTouchListener{

    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoyStickListener joysickCallback;


    public interface JoyStickListener{
        void onJoyStickMoved(float xPercent, float yPercent, int source);
    }

    private void setupDimensions()
    {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 5;
    }

    public JoyStick(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoyStickListener)
            joysickCallback = (JoyStickListener) context;
    }

    public JoyStick(Context context, AttributeSet attributes, int style){
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoyStickListener)
            joysickCallback = (JoyStickListener) context;
    }

    public JoyStick (Context context, AttributeSet attributes){
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoyStickListener)
            joysickCallback = (JoyStickListener) context;
    }

    private void drawJoystick(float newX,float newY){
        Canvas myCanvas = this.getHolder().lockCanvas();
        Paint colors = new Paint();
        //myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        myCanvas.drawARGB(255,0,0,0);
        colors.setARGB(255, 50 ,50, 50);
        myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
        colors.setARGB(255,0,0,255);
        myCanvas.drawCircle(newX, newY, hatRadius, colors);
        getHolder().unlockCanvasAndPost(myCanvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        if(v.equals(this))
        {
            if(e.getAction() != e.ACTION_UP)
            {
                float displacement = (float) Math.sqrt((Math.pow(e.getX() - centerX, 2)) + Math.pow(e.getY() - centerY, 2));
                if(displacement < baseRadius)
                    drawJoystick(e.getX(), e.getY());
                else
                {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);
                    joysickCallback.onJoyStickMoved((constrainedX-centerX)/baseRadius, (constrainedY-centerY)/baseRadius, getId());
                }
            }
            else
                drawJoystick(centerX, centerY);
        }
        return true;
    }
}
