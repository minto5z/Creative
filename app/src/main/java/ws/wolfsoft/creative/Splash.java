package ws.wolfsoft.creative;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ldoublem.loadingviewlib.view.LVBlock;

public class Splash extends AppCompatActivity {


    LVBlock mLVBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        mLVBlock = (LVBlock) findViewById(R.id.lv_block);

        mLVBlock.setViewColor(Color.rgb(245,209,22));
        mLVBlock.setShadowColor(Color.BLACK);
        mLVBlock.startAnim();


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 3000);

    }
}
