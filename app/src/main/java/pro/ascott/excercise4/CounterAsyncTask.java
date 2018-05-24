package pro.ascott.excercise4;

import android.os.AsyncTask;
import android.os.SystemClock;


public class CounterAsyncTask extends AsyncTask<Integer, Integer, Void> {

    public static final int maxCount = 10;
    private final ICounterPresenter _presenter;
    private final Integer _startIndex;

    public CounterAsyncTask(ICounterPresenter presenter, Integer startIndex) {
        _presenter = presenter;
        _startIndex = startIndex;
    }

    public CounterAsyncTask(ICounterPresenter presenter) {
        this(presenter, 1);
    }


    @Override
    protected Void doInBackground(Integer... integers) {
        for (int i = _startIndex; i < maxCount; i++) {
            if (isCancelled()) break;
            SystemClock.sleep(300);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        _presenter.OnProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Void v) {
        _presenter.OnPostProcessing("Done!");
    }
}