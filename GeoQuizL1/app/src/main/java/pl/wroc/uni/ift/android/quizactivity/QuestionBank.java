package pl.wroc.uni.ift.android.quizactivity;
import java.util.ArrayList;
import java.util.List;

class QuestionBank {
    private static final QuestionBank ourInstance = new QuestionBank();

    static QuestionBank getInstance() {
        return ourInstance;
    }
    private List<Question> mQuestionBank;

    protected QuestionBank() {
        mQuestionBank = new ArrayList<>();
        mQuestionBank.add(new Question(R.string.question_stolica_polski, true));
        mQuestionBank.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionBank.add(new Question(R.string.question_sniezka, true));
        mQuestionBank.add(new Question(R.string.question_wisla, true));
    }

    public Question getQuestion(int index) {
        return mQuestionBank.get(index);
    }

    public List<Question> getQuestions() { return mQuestionBank; }
    public int size() { return mQuestionBank.size(); }

}
