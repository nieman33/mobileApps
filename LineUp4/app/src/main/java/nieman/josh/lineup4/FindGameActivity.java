package nieman.josh.lineup4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by joshnieman on 10/13/16.
 */
public class FindGameActivity extends AppCompatActivity implements View.OnClickListener{

    Button mFindGameButton;
    Button mGoToBoardButton;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game_find);

        mFindGameButton = (Button)findViewById(R.id.find_game_button);
        mFindGameButton.setText("Go back");
        mFindGameButton.setOnClickListener(this);

        mGoToBoardButton = (Button)findViewById(R.id.go_to_board_button);
        mGoToBoardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.find_game_button:
                startActivity(new Intent(this, WelcomeActivity.class));
                break;
            case R.id.go_to_board_button:
                //Log.d("TAG","clicked the go to board button");
                startActivity(new Intent(this, BoardGameActivity.class));
                break;
        }
    }
}
