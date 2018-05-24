package pro.ascott.excercise4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AsyncTaskActivity extends AppCompatActivity implements ICounterPresenter {
    CounterAsyncTask _task;
    TextView _textView;
    Integer _counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        _textView = findViewById(R.id.task_log);

        Button createButton = findViewById(R.id.create_task);
        createButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createTask();
                    }
                }
        );

        Button startButton = findViewById(R.id.start_task);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startTask();
                    }
                }
        );

        Button cancelButton = findViewById(R.id.cancel_task);
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

    private void createTask(Integer initialValue) {
        if(initialValue > 0)
            _counter = initialValue;
        tryCancelTask();

        _task = new CounterAsyncTask(this, _counter);
        _textView.setText("Created");
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
                _task.cancel(true);
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

    @Override
    public void OnProgressUpdate(Integer counter) {
        _textView.setText(counter.toString());
        _counter++;
    }

    @Override
    public void OnPreProcessing(String text) {
        _textView.setText(text);
    }

    @Override
    public void OnPostProcessing(String text) {
        _textView.setText(text);
    }
}
