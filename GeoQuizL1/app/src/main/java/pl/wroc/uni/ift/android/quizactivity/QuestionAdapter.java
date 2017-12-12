package pl.wroc.uni.ift.android.quizactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Igorow3 on 2017-12-09.
 */

public class QuestionAdapter extends RecyclerView.Adapter {

    private ArrayList<Question> questions = new ArrayList<>();

    private RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.question_text);
        }
    }

    public QuestionAdapter(ArrayList<Question> questions, RecyclerView recyclerView) {
        this.questions = questions;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_questions, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {

        ((ViewHolder) viewHolder).mTextView.setText(questions.get(i).getTextResId());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
