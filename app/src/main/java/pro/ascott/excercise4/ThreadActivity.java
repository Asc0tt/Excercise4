package pro.ascott.excercise4;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadActivity extends AppCompatActivity  {

    ThreadWrapper<Integer> _task;
    TextView _textView;
    Integer _counter = 0;
    Integer maxCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        _textView = findViewById(R.id.thread_log);


        Button createButton = findViewById(R.id.create_thread);
        createButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createTask();
                    }
                }
        );

        Button startButton = findViewById(R.id.start_thread);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startTask();
                    }
                }
        );

        Button cancelButton = findViewById(R.id.cancel_thread);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tryCancelTask();
                    }
                }
        );
    }

    private void createTask() {
        createTask(1);
    }

    private void createTask(final Integer initialValue) {
        _counter = initialValue;
        tryCancelTask();

        _task = new ThreadWrapper<Integer>() {
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Integer doInBackground() {
                for (int i = initialValue; i < maxCount; i++) {
                    if (isCanceled()) break;
                    SystemClock.sleep(300);
                    _counter++;
                    publishProgress(i);
                }
                return null;
            }

            @Override
            protected void onPostExecute() {
                if(isCanceled())
                    return;
                _textView.setText("DONE!");
                _counter = 0;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                _counter = values[0];
                _textView.setText(_counter.toString());

            }
        };
        _textView.setText("CREATED!");
    }

    private void startTask() {
        try {
            if (_task != null) {
                _task.execute();
            }
        } catch (Exception exception) {
            Toast
                    .makeText(this, exception.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void tryCancelTask() {
        try {
            if (_task != null)
                _task.cancel();
        } catch (Exception exception) {
            Log.e("Exception", exception.toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", _counter);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _counter = savedInstanceState.getInt("count");
        if(_counter > 0) {
            createTask(_counter);
            startTask();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tryCancelTask();
    }
}
