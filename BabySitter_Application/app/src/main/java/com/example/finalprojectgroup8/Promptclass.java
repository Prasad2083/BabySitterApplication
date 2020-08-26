package com.example.finalprojectgroup8;

import com.example.finalprojectgroup8.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Promptclass extends AppCompatDialogFragment {
    private TextView datatext;
    private Button databutton;
    private SeekBar seekBar;
    int price,status=9;
    RadioGroup radioGroup;
    HomeFragment homeFragment;

    public Promptclass(HomeFragment homeFragment) {
        this.homeFragment=homeFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_prompt, null);
        builder.setView(view).setTitle("Filters").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Apply Filters", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    price = seekBar.getProgress();
                int checkedid=radioGroup.getCheckedRadioButtonId();
                if (checkedid==-1){
                    status=9;
                }
                else {
                    switch (checkedid){
                        case R.id.forchild:
                            status=1;
                            break;
                        case R.id.forold:
                            status=2;
                            break;
                        case R.id.forboth:
                            status=3;
                            break;
                    }


                }

                homeFragment.applyTexts(price,status);
            }
        });
        datatext = view.findViewById(R.id.weldata);
        seekBar = view.findViewById(R.id.seekbar);
        radioGroup = view.findViewById(R.id.promptgrp);
        seekBar.setMax(35);
        seekBar.setProgress(21);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                datatext.setText("Price : $ " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return builder.create();


    }

  }
