package pl.wroc.uni.ift.android.quizactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    public static int mTokens = 2;
    private static final String KEY_QUESTIONS = "questions";

    private static final int CHEAT_REQEST_CODE = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    TextView mAPILevel;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };
    ;

    private Button mCheatButton;
    ;
    private int mCurrentIndex = 0;
    private int mNumber = 0;
    private int mPoints = 0;
    private boolean[] AnsQuestion = new boolean[mQuestionsBank.length];
    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {

            mCurrentIndex = savedInstanceState.getInt("index");
            mPoints = savedInstanceState.getInt("points");
            mNumber = savedInstanceState.getInt("number");
            AnsQuestion = savedInstanceState.getBooleanArray("ans");
            mTokens = savedInstanceState.getInt("token");
        }
            mQuestionTextView = (TextView) findViewById(R.id.question_text_view);


        mCheatButton = (Button) findViewById(R.id.button_cheat);
        mCheatButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateQuestion();
                        boolean currentAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                        Intent intent = CheatActivity.newIntent(QuizActivity.this, currentAnswer);
                        startActivityForResult(intent, mTokens);
                    }
                }
        );


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });






        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        //updateQuestion();

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentIndex == 0)
                {
                    mCurrentIndex = mQuestionsBank.length - 1;
                }else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                };
                updateQuestion();
            }
        });
        mAPILevel = (TextView) findViewById(R.id.APILEVEL);
        mAPILevel.setText(" Android Version : " + Build.VERSION.RELEASE + " and API Level : " + Build.VERSION.SDK);

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHEAT_REQEST_CODE) {
            if (data != null)
            {
                boolean answerWasShown = CheatActivity.wasAnswerShown(data);
                if (answerWasShown) {
                    Toast.makeText(this, R.string.message_for_cheaters, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mCurrentIndex);
        outState.putInt("points", mPoints);
        outState.putBooleanArray("ans", AnsQuestion);
        outState.putInt("number", mNumber);
        outState.putInt("token", mTokens);
        super.onSaveInstanceState(outState);
    }

    private void ifTheEnd() {
        if (mNumber == mQuestionsBank.length) {
            String gameOver_string = getString(R.string.the_end, mPoints);
            Toast.makeText(this, gameOver_string, Toast.LENGTH_LONG).show();


        }
    }
    private void setAnswerButtonsEnabled(boolean enabled) {
        mTrueButton.setEnabled(enabled);
        mFalseButton.setEnabled(enabled);
    }
    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (mTokens <= 0) {
            mCheatButton.setVisibility(View.INVISIBLE);
        }
        if (!AnsQuestion[mCurrentIndex]) { setAnswerButtonsEnabled(false); }
        setAnswerButtonsEnabled(!AnsQuestion[mCurrentIndex]);

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int toastMessageId = 0;
        mNumber += 1;
        if (userPressedTrue == answerIsTrue) {
            toastMessageId = R.string.correct_toast;
            mPoints += 1;
            setAnswerButtonsEnabled(false);
            AnsQuestion[mCurrentIndex]=true;

        } else {
            toastMessageId = R.string.incorrect_toast;
            setAnswerButtonsEnabled(false);
            AnsQuestion[mCurrentIndex]=true;

        }



        updateQuestion();
        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.TOP,0,200);
        ifTheEnd();
    }
}
