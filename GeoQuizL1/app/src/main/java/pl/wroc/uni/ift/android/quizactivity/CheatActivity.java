package pl.wroc.uni.ift.android.quizactivity;

/**
 * Created by jurkiewicz on 07.11.17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    private final static String EXTRA_KEY_ANSWER = "Answer";
    private final static String KEY_QUESTION = "QUESTION";
    private final static String EXTRA_KEY_SHOWN = "Shown";
    TextView mTextViewAnswer;
    Button mButtonShow;


    boolean mAnswer;

    private void message() {
        String message = "Oszust";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheat_activity);

        mAnswer = getIntent().getBooleanExtra(EXTRA_KEY_ANSWER,false);

        mTextViewAnswer = (TextView) findViewById(R.id.text_view_answer);
        mButtonShow = (Button) findViewById(R.id.button_show_answer);
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswer) {
                    mTextViewAnswer.setText("Prawda");
                    QuizActivity.mTokens--;

                } else {
                    mTextViewAnswer.setText("Fałsz");
                    QuizActivity.mTokens--;

                }
                setAnswerShown(true);
                message();
            }
        });

        setAnswerShown(false);
    }


    public static boolean wasAnswerShown(Intent data)
    {
        return data.getBooleanExtra(EXTRA_KEY_SHOWN, false);
    }

    public static Intent newIntent(Context context, boolean answerIsTrue)
    {

        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_KEY_ANSWER, answerIsTrue);
        return intent;

    }

    private void setAnswerShown (boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra("wasShown", isAnswerShown);
        setResult(RESULT_OK, data);
    }



}