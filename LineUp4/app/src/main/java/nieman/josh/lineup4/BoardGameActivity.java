package nieman.josh.lineup4;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by joshnieman on 10/14/16.
 */
public class BoardGameActivity extends Activity {

    private GameBoard mGameboard;
    //private View mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //setContentView(R.layout.activity_game_board);
        mGameboard = new GameBoard(getApplicationContext());
        mGameboard.setSizeOfBoard(7,7);

        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView player1Name = new TextView(getApplicationContext());
        player1Name.setText(mGameboard.getPlayer1Name());
        player1Name.setTextColor(mGameboard.getPlayer1Color());
        player1Name.setTextSize(24);
        TextView player2Name = new TextView(getApplicationContext());
        player2Name.setText(mGameboard.getPlayer2Name());
        player2Name.setTextColor(mGameboard.getPlayer2Color());
        player2Name.setTextSize(24);


        LinearLayout namesLayout = new LinearLayout(getApplicationContext());
        linearLayout.setGravity(Gravity.CENTER);
        namesLayout.setOrientation(LinearLayout.HORIZONTAL);
        namesLayout.addView(player1Name);
        namesLayout.addView(player2Name);
        
        //linearLayout.addView(player1Name, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(namesLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getApplicationContext(),Gravity.CENTER_VERTICAL);
        linearLayout.addView(mGameboard,1000,1000);//, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setContentView(linearLayout);
    }
}
