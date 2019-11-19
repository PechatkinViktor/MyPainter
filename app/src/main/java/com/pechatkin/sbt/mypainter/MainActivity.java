package com.pechatkin.sbt.mypainter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.pechatkin.sbt.mypainter.adapters.ColorAdapter;
import com.pechatkin.sbt.mypainter.adapters.TypeAdapter;
import com.pechatkin.sbt.mypainter.models.Colors;
import com.pechatkin.sbt.mypainter.models.Types;

import java.lang.reflect.Type;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawView mDrawView;
    private Spinner mColorsSpinner;
    private Spinner mTypesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initSpinners();
    }

    private void initSpinners() {
        ColorAdapter colorAdapter = new ColorAdapter(Arrays.asList(Colors.values()));
        mColorsSpinner.setAdapter(colorAdapter);
        mColorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDrawView.setColor(((Colors)mColorsSpinner.getItemAtPosition(position)).mColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TypeAdapter typeAdapter = new TypeAdapter(Arrays.asList(Types.values()));
        mTypesSpinner.setAdapter(typeAdapter);
        mTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawView.setType((Types)mTypesSpinner.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init() {
        mDrawView = findViewById(R.id.draw_view);
        mColorsSpinner = findViewById(R.id.color_spinner);
        mTypesSpinner = findViewById(R.id.type_spinner);
        findViewById(R.id.button_reset).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        mDrawView.reset();
    }
}
