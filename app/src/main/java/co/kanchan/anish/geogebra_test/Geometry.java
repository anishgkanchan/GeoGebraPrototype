package co.kanchan.anish.geogebra_test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Geometry extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.geometry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void move(View v){
        DrawingView.reset();
        DrawingView.flag=0;
        Toast.makeText(this,"Tap on a point and then tap elsewhere on the screen to move it",Toast.LENGTH_SHORT).show();
    }
    public void point(View v){
        DrawingView.reset();
        DrawingView.flag=1;
        Toast.makeText(this,"Touch anywhere on the screen to draw a point",Toast.LENGTH_SHORT).show();
    }
    public void line(View v){
        DrawingView.reset();
        DrawingView.flag=2;
        Toast.makeText(this,"Tap on the screen at any two places to draw a line",Toast.LENGTH_SHORT).show();
    }
    public void circle(View v){
        DrawingView.reset();
        DrawingView.flag=3;
        Toast.makeText(this,"Tap on the centre and a circumferential point of the circle you wish to draw",Toast.LENGTH_SHORT).show();
    }
}
