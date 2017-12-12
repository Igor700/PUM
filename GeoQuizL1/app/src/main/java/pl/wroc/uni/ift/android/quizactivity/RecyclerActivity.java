package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
/**
 * Created by Igorow3 on 2017-12-09.
 */
import java.util.ArrayList;
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    public static Intent newIntent(Context context) {
        return new Intent(context, RecyclerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_activty);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(new QuestionAdapter((ArrayList<Question>) QuestionBank.getInstance().getQuestions(), mRecyclerView));

    }
}
