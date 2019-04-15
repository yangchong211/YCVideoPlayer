package org.yczbj.ycvideoplayer.ui.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.yczbj.ycvideoplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyActivity extends AppCompatActivity {


    @BindView(R.id.jump_other)
    Button jumpOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);
        jumpOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmptyActivity.this, EmptyActivity.class));
            }
        });
    }


}
