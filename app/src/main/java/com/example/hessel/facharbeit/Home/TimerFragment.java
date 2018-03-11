package com.example.hessel.facharbeit.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hessel.facharbeit.R;

/**
 * Created by hessel on 18.12.2017.
 */

public class TimerFragment extends Fragment {
    private static final String Tag = "TimerFragment";
    private int hour=0;
    private int minute=0;
    private int second=0;
    private TextView tvTimer;
    private ProgressBar pbTimer;
    private int time=0;
    private Button btTimer;
    private CountDownTimer countDownTimer;
    private Button btTimerStop;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container,false);

        TextView [] tvTags = {view.findViewById(R.id.tvh),view.findViewById(R.id.tvmin),view.findViewById(R.id.tvs)};

        tvTimer = (TextView) view.findViewById(R.id.timertext);
        pbTimer = (ProgressBar) view.findViewById(R.id.timerprogress);
        pbTimer.setMax(50);
        btTimer = (Button) view.findViewById(R.id.timerbutton);

        btTimerStop = (Button) view.findViewById(R.id.timerbuttonstop);

        NumberPicker[] numberPickers = {view.findViewById(R.id.numberPicker0),view.findViewById(R.id.numberPicker1),view.findViewById(R.id.numberPicker2)};
        for (int i = 0; i<3; i++){
            numberPickers[i].setMinValue(0);
            numberPickers[i].setMaxValue(59);
            numberPickers[0].setMaxValue(24);
            numberPickers[i].setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Log.d(Tag,""+i1);
                    switch (numberPicker.getId()){
                        case R.id.numberPicker0:
                            hour=i1;
                            break;
                        case R.id.numberPicker1:
                            minute=i1;
                            break;
                        case R.id.numberPicker2:
                            second=i1;
                            break;
                    }
                    time= hour*3600+minute*60+second;
                }
            });
        }

        onclickTimer(numberPickers,tvTags);
        onclickStop();
        return view;
    }
    public void startTimer(final int number,final NumberPicker [] numberPickers, final TextView [] tvTags) {
        countDownTimer = new CountDownTimer(number * 1000+1000, 1000) {
            int counter = number;


            public void onTick(long millisUntilFinished) {
                pbTimer.setMax(number);
                int hour = counter / 3600;
                int seconds = counter % 3600;
                int min = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText(checkdigits(hour) + ":" + checkdigits(min) + ":" + checkdigits(seconds));
                ;
                pbTimer.setProgress(-(counter - number));
                counter--;
            }

            public void onFinish() {
                for (int i = 0; i<3; i++) {
                    tvTags[i].setVisibility(View.VISIBLE);
                    numberPickers[i].setVisibility(View.VISIBLE);
                }
                tvTimer.setVisibility(View.GONE);
                pbTimer.setProgress(0);
            }
        }.start();

    }
    public String checkdigits(int number){
        if (String.valueOf(number).length()==1){
            return "0"+String.valueOf(number);
        }else{
            return  String.valueOf(number);
        }
    }

    public void onclickTimer(final NumberPicker [] numberPickers, final TextView [] tvTags){
        btTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i<3; i++) {
                    tvTags[i].setVisibility(View.GONE);
                    numberPickers[i].setVisibility(View.GONE);
                }
                tvTimer.setVisibility(View.VISIBLE);
                startTimer(time,numberPickers,tvTags);
                btTimer.setText("Stop");

            }
        });

    }

    public void onclickStop(){
        btTimerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //countDownTimer.cancel();
                //countDownTimer.onFinish();


            }
        });

    }
}
