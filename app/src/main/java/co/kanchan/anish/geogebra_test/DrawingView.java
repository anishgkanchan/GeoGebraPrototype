package co.kanchan.anish.geogebra_test;

import android.view.View;

import android.util.Log;
import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import java.util.ArrayList;

public class DrawingView extends View
{
    static int flag=-1;//0 move; 1 point; 2 line; 3 circle
    static boolean lineflag=false;
    static int move=-1;//0 move point; 1 move line's 1st point; 2 move line's 2nd point
                //3 move circle's centre; 4 move circle's other point; 5 move entire circle as it is
    static boolean moveflag=false;
    static int moveindex=-1;
    static float touchX=-1;
    static float touchY=-1;
    static int pctr=0;
    static int lctr=0;
    static double circlex, circley=-1;
    static boolean circleflag=false;
    static CanvasContents t;
    class CanvasContents
    {
        ArrayList<Float> pointx=new ArrayList<Float>();
        ArrayList<Float> pointy=new ArrayList<Float>();
        ArrayList<Float> line1x=new ArrayList<Float>();
        ArrayList<Float> line1y=new ArrayList<Float>();
        ArrayList<Float> line2x=new ArrayList<Float>();
        ArrayList<Float> line2y=new ArrayList<Float>();
        ArrayList<Float> circlex=new ArrayList<Float>();
        ArrayList<Float> circley=new ArrayList<Float>();
        ArrayList<Float> circlepx=new ArrayList<Float>();
        ArrayList<Float> circlepy=new ArrayList<Float>();
    }

    //drawing path
    private Path path;
    //drawing and canvas paint
    private Paint paint,paintdot,colourpaintdot,colourpaint;
    //initial color
    private int paintColor = 0xFF660000;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);

        Log.v("Check This! ----------------->","Inside DrawingView Constructor");
        setupDrawing();
    }
    private void setupDrawing(){
        t=new CanvasContents();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(2f);
        paintdot=new Paint();
        paintdot.setAntiAlias(true);
        paintdot.setColor(Color.BLUE);
        paintdot.setStyle(Paint.Style.STROKE);
        paintdot.setStrokeJoin(Paint.Join.ROUND);
        paintdot.setStrokeWidth(10f);
        colourpaintdot=new Paint();
        colourpaintdot.setAntiAlias(true);
        colourpaintdot.setColor(Color.YELLOW);
        colourpaintdot.setStyle(Paint.Style.STROKE);
        colourpaintdot.setStrokeJoin(Paint.Join.ROUND);
        colourpaintdot.setStrokeWidth(10f);
        colourpaint=new Paint();
        colourpaint.setAntiAlias(true);
        colourpaint.setColor(Color.YELLOW);
        colourpaint.setStyle(Paint.Style.STROKE);
        colourpaint.setStrokeJoin(Paint.Join.ROUND);
        colourpaint.setStrokeWidth(2f);
        Log.v("Check This! ----------------->","Inside setupDrawing "+paint);
    }
    public static void reset(){
        flag=-1;
        lineflag=false;
        move=-1;
        moveflag=false;
        moveindex=-1;
        touchX=-1;
        touchY=-1;
        pctr=0;
        lctr=0;
        circlex=circley=-1;
        circleflag=false;
        if(t.line1x.size()!=t.line2x.size()){
            int temp=t.line1x.size()-1;
            t.line1x.remove(temp);
            t.line1y.remove(temp);
        }
        if(t.circlex.size()!=t.circlepx.size()){
            int temp=t.circlex.size()-1;
            t.circlex.remove(temp);
            t.circley.remove(temp);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        Log.v("Check This! ----------------->","Inside onTouchEvent Handler "+event.getX()+","+event.getY());
        //detect user touch
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (flag == 1) {
                t.pointx.add(event.getX());
                t.pointy.add(event.getY());
            } else if (flag == 2 && lineflag == false) {
                t.line1x.add(event.getX());
                t.line1y.add(event.getY());
                lineflag = true;
            } else if (flag == 2 && lineflag == true) {
                t.line2x.add(event.getX());
                t.line2y.add(event.getY());
                lineflag = false;
            }else if(flag==0){
                float x=event.getX();
                float y=event.getY();
                if(moveflag==false)
                {
                    for (int i = 0; i < t.pointx.size(); i++) {
                        if (Math.abs(t.pointx.get(i) - x) + Math.abs(t.pointy.get(i) - y) <= 25) {
                            moveflag = true;
                            move = 0;
                            moveindex = i;
                            Log.v("Check This! -------move 0---------->","Inside onTouchEvent Handler "+t.pointx.get(i)+","+t.pointy.get(i));
                            Log.v("Check This! -------move 0---------->","Inside onTouchEvent Handler "+x+","+y);
                            Log.v("Check This! -------move 0---------->","Inside onTouchEvent Handler value of i is "+i);

                            break;
                        }
                    }
                    if (moveindex == -1) {
                        for (int i = 0; i < t.line1x.size(); i++) {
                            if (Math.abs(t.line1x.get(i) - x) + Math.abs(t.line1y.get(i) - y) <= 25) {
                                moveflag = true;
                                move = 1;
                                moveindex = i;
                                Log.v("Check This! --------move 1--------->","Inside onTouchEvent Handler "+t.line1x.get(i)+","+t.line1y.get(i));
                                Log.v("Check This! -----move 1------------>","Inside onTouchEvent Handler "+x+","+y);
                                Log.v("Check This! ------move 1----------->","Inside onTouchEvent Handler value of i is "+i);
                                break;
                            }
                        }
                    }
                    if (moveindex == -1) {
                        for (int i = 0; i < t.line1x.size(); i++) {
                            if (Math.abs(t.line2x.get(i) - x) + Math.abs(t.line2y.get(i) - y) <= 25) {
                                moveflag = true;
                                move = 2;
                                moveindex = i;
                                Log.v("Check This! --------move 2--------->","Inside onTouchEvent Handler "+t.line2x.get(i)+","+t.line2y.get(i));
                                Log.v("Check This! -----move 2------------>","Inside onTouchEvent Handler "+x+","+y);
                                Log.v("Check This! ------move 2----------->","Inside onTouchEvent Handler value of i is "+i);
                                break;
                            }
                        }
                    }
                    if(moveindex==-1){
                        for (int i = 0; i < t.circlex.size(); i++) {
                            if (Math.abs(t.circlex.get(i) - x) + Math.abs(t.circley.get(i) - y) <= 25) {
                                moveflag = true;
                                move = 3;
                                moveindex = i;
                                Log.v("Check This! --------move 2--------->","Inside onTouchEvent Handler "+t.circlex.get(i)+","+t.circley.get(i));
                                Log.v("Check This! -----move 2------------>","Inside onTouchEvent Handler "+x+","+y);
                                Log.v("Check This! ------move 2----------->","Inside onTouchEvent Handler value of i is "+i);
                                break;
                            }
                        }
                    }
                    if(moveindex==-1){
                        for (int i = 0; i < t.circlepx.size(); i++) {
                            if (Math.abs(t.circlepx.get(i) - x) + Math.abs(t.circlepy.get(i) - y) <= 25) {
                                moveflag = true;
                                move = 4;
                                moveindex = i;
                                Log.v("Check This! --------move 4--------->","Inside onTouchEvent Handler "+t.circlepx.get(i)+","+t.circlepy.get(i));
                                Log.v("Check This! -----move 4------------>","Inside onTouchEvent Handler "+x+","+y);
                                Log.v("Check This! ------move 4----------->","Inside onTouchEvent Handler value of i is "+i);
                                break;
                            }
                        }
                    }
                    if(moveindex==-1){
                        for (int i = 0; i < t.circlepx.size(); i++) {
                            double cx=t.circlex.get(i);
                            double cy=t.circley.get(i);
                            double p1x=t.circlepx.get(i);
                            double p1y=t.circlepy.get(i);
                            double z=(cx-x)*(cx-x)+(cy-y)*(cy-y);
                            double r=(p1x-cx)*(p1x-cx)+(p1y-cy)*(p1y-cy);
                            double z1=r+40*Math.sqrt(r)+400;
                            double z2=r-40*Math.sqrt(r)+400;
                            Log.v("Check This! --------Inside move 5, before acceptance--------->","Inside onTouchEvent Handler z1"+z1+", z2"+z2+", z"+z);
                            if (z<z1&&z>z2) {
                                moveflag = true;
                                move = 5;
                                moveindex = i;
                                circlex = x;
                                circley=y;
                                Log.v("Check This! --------move 5--------->","Inside onTouchEvent Handler "+t.circlepx.get(i)+","+t.circlepy.get(i));
                                Log.v("Check This! -----move 5------------>","Inside onTouchEvent Handler "+x+","+y);
                                Log.v("Check This! ------move 5----------->","Inside onTouchEvent Handler value of i is "+i);
                                break;
                            }
                        }
                    }
                }
                else if(moveflag==true){
                    float tempx=event.getX();
                    float tempy=event.getY();
                    Log.v("Check This! ------moveflag true----------->","value of the new point is "+tempx+","+tempy);
                    Log.v("Check This! ------moveflag true----------->","value of move is "+move);
                    switch(move){
                        case 0:
                            t.pointx.set(moveindex,tempx);
                            t.pointy.set(moveindex,tempy);
                            break;
                        case 1:
                            t.line1x.set(moveindex,tempx);
                            t.line1y.set(moveindex,tempy);
                            break;
                        case 2:
                            t.line2x.set(moveindex,tempx);
                            t.line2y.set(moveindex,tempy);
                            break;
                        case 3:
                            t.circlex.set(moveindex,tempx);
                            t.circley.set(moveindex,tempy);
                            break;
                        case 4:
                            t.circlepx.set(moveindex,tempx);
                            t.circlepy.set(moveindex,tempy);
                            break;
                        case 5:
                            double dx=tempx-circlex;
                            double dy=tempy-circley;
                            float ocx=t.circlex.get(moveindex);
                            float ocy=t.circley.get(moveindex);
                            float opx=t.circlepx.get(moveindex);
                            float opy=t.circlepy.get(moveindex);
                            t.circlex.set(moveindex,(float)(ocx+dx));
                            t.circley.set(moveindex,(float)(ocy+dy));
                            t.circlepx.set(moveindex,(float)(opx+dx));
                            t.circlepy.set(moveindex,(float)(opy+dy));

                    }
                    moveflag=false;
                    moveindex=-1;
                }

            }else if(flag==3&&circleflag==false){
                t.circlex.add(event.getX());
                t.circley.add(event.getY());
                circleflag = true;

                Log.v("Check This! --------flag 3----first----->","Inside onTouchEvent Handler "+event.getX()+","+event.getY());
            }else if(flag==3&&circleflag==true){
                t.circlepx.add(event.getX());
                t.circlepy.add(event.getY());
                circleflag = false;

                Log.v("Check This! --------flag 3----second----->","Inside onTouchEvent Handler "+event.getX()+","+event.getY());
            }
        }
        invalidate();
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Log.v("Check This! ----------------->","Inside onDraw Before drawLine");
        for(int i=0;i<t.pointx.size();i++)
        {
            canvas.drawPoint(t.pointx.get(i),t.pointy.get(i),paintdot);
        }
        for(int i=0;i<t.line2x.size();i++)
        {
            canvas.drawPoint(t.line1x.get(i),t.line1y.get(i),paintdot);
            canvas.drawPoint(t.line2x.get(i),t.line2y.get(i),paintdot);
            canvas.drawLine(t.line1x.get(i),t.line1y.get(i),t.line2x.get(i),t.line2y.get(i),paint);
        }
        for(int i=0;i<t.circlepx.size();i++)
        {
            double a=t.circlex.get(i)-t.circlepx.get(i);
            double b=t.circley.get(i)-t.circlepy.get(i);
            double rad=Math.sqrt(a*a+b*b);
            canvas.drawPoint(t.circlex.get(i),t.circley.get(i),paintdot);
            canvas.drawPoint(t.circlepx.get(i),t.circlepy.get(i),paintdot);
            canvas.drawCircle(t.circlex.get(i),t.circley.get(i),(float)rad,paint);
        }
        if(flag==0&&moveflag==true){

            Log.v("Check This! ----------------->","This place is entered!");

            switch(move){
                case 0:
                    canvas.drawPoint(t.pointx.get(moveindex),t.pointy.get(moveindex),colourpaintdot);
                    break;
                case 1:
                    canvas.drawPoint(t.line1x.get(moveindex),t.line1y.get(moveindex),colourpaintdot);
                    break;
                case 2:
                    canvas.drawPoint(t.line2x.get(moveindex),t.line2y.get(moveindex),colourpaintdot);
                    break;
                case 3:
                    canvas.drawPoint(t.circlex.get(moveindex),t.circley.get(moveindex),colourpaintdot);
                    break;
                case 4:
                    canvas.drawPoint(t.circlepx.get(moveindex),t.circlepy.get(moveindex),colourpaintdot);
                    break;
                case 5:
                    double a=t.circlex.get(moveindex)-t.circlepx.get(moveindex);
                    double b=t.circley.get(moveindex)-t.circlepy.get(moveindex);
                    double rad=Math.sqrt(a*a+b*b);
                    canvas.drawCircle(t.circlex.get(moveindex),t.circley.get(moveindex),(float)rad,colourpaint);
                    break;
            }
        }
      //  canvas.drawLine(touchX, touchY, touchX + 150, touchY + 150, paint);
        Log.v("Check This! ----------------->","Inside onDraw After drawLine");
        //draw view
        //canvas.drawPath(path, paint);
    }

}