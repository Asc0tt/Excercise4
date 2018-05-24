package pro.ascott.excercise4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitOnClickStartActivity(R.id.async_button, AsyncTaskActivity.class);
        InitOnClickStartActivity(R.id.thread_button, ThreadActivity.class);
    }

    private void InitOnClickStartActivity(int button_id, final Class activityClass) {
        Button button = findViewById(button_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), activityClass);
                view.getContext().startActivity(intent);}
        });
    }

}
