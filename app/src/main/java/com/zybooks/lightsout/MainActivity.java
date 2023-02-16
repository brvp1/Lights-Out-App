package com.zybooks.lightsout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

// Controller
public class MainActivity extends AppCompatActivity {

    /**
     * instance variables
     **/
    private LightOutGame mGame; // model object reference
    private GridLayout mLightGrid; // view object reference
    private int mLightOnColor; // to hold light on color
    private int mLightOffColor; // to hold light off color
    private final String GAME_STATE = "gameState"; // will hold the state of the game
    private int mLightOnColorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLightOnColorId = R.color.yellow;

        mLightGrid = findViewById(R.id.light_grid);


        // Add the same click handler to all grid buttons
        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setOnClickListener(this::onLightButtonClick);
        }

        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightOutGame();

        if (savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setButtonColors();
        }


    } // end onCreate

    private void onLightButtonClick(View view) {
        // find the button's row and col
        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / LightOutGame.GRID_SIZE;
        int col = buttonIndex % LightOutGame.GRID_SIZE;

        mGame.selectLight(row, col);  // set model selected light and others around
        setButtonColors();  // reset the buttons views

        // Congratulates the user if the game is over
        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_LONG).show();
        }

    } // end onLightButtonClick

    private void startGame() {
        mGame.newGame(); // set up model
        setButtonColors(); // set up view objects

    }

    private void setButtonColors() {

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);

            // Find the button's row and column
            int row = buttonIndex / LightOutGame.GRID_SIZE;
            int col = buttonIndex % LightOutGame.GRID_SIZE;

            if (mGame.isLightOn(row, col)) {
                gridButton.setBackgroundColor(mLightOnColor);
            } else {
                gridButton.setBackgroundColor(mLightOffColor);
            }
        }

    } // end setButtonColors

    public void onNewGameClick(View view) {
        startGame();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }  // end onSaveInstanceState

    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }  // end onHelpClick

    public void onChangeColorClick(View view) {
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.EXTRA_COLOR, mLightOnColorId);
        mColorResultLauncher.launch(intent);
    }  // end onChangeColorClick

    ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() ==  Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            mLightOnColorId = data.getIntExtra(ColorActivity.EXTRA_COLOR,
                                    R.color.yellow);
                            mLightOnColor = ContextCompat.getColor(MainActivity.this, mLightOnColorId);
                            setButtonColors();
                        }
                    }
                }
    }
    );
}