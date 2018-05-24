package pro.ascott.excercise4;

import android.os.Handler;
import android.os.Looper;

public abstract class ThreadWrapper<T> {
    private Thread _thread;
    private Boolean _canceled;

    protected abstract void onPreExecute();
    protected abstract T doInBackground();
    protected abstract void onPostExecute();
    protected abstract void onProgressUpdate(T... values);

    private Runnable GetPreExecuter(){
        return new Runnable() {
            @Override
            public void run() {
                onPreExecute();
                InitThread().start();
            }
        };
    }

    private Runnable GetPostExecuteer(){
        return new Runnable() {
            @Override
            public void run() {
                onPostExecute();
            }
        };
    }

    private Thread InitThread()
    {
        _canceled = false;
        _thread = new Thread("Thread") {
            @Override
            public void run() {
                doInBackground();
                runOnUiThread(GetPostExecuteer());
            }
        };
        return _thread;
    }

    public void execute() {
        runOnUiThread(GetPreExecuter());
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }


    public Boolean isCanceled()
    {
        return _canceled;
    }

    public void cancel() {
        _canceled = true;
        if (_thread != null) {
            _thread.interrupt();
        }
    }


    protected void publishProgress(final T... values) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(values);
            }
        });
    }
}