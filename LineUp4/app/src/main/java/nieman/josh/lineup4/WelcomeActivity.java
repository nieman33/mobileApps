package nieman.josh.lineup4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mStartButton = (Button)findViewById(R.id.welcome_button);
        mStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.welcome_button:
                //Log.d("TAG","called the welcome button");
                startActivity(new Intent(this,FindGameActivity.class));
                break;
        }
    }

}
