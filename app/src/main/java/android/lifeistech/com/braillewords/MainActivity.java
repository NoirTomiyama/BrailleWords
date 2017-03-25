package android.lifeistech.com.braillewords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toJ(View v){
        Intent intent = new Intent(this, toJActivity.class);
        startActivity(intent);
    }

    public void toB(View v){
        Intent intent = new Intent(this, toBActivity.class);
        startActivity(intent);
    }

    public void help(View v){
        Intent intent = new Intent(this, helpActivity.class);
        startActivity(intent);
    }
}


