package com.example.lifecycleviewtoast;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.CountDownLatch;

public class TimerToast implements LifecycleObserver {

    private boolean stated = false;
    private Context context;
    public Lifecycle lifecycle;

    public TimerToast(Context context,LifecycleOwner lifecycleOwner){
        this.context =context;
        this.lifecycle = lifecycleOwner.getLifecycle();
        lifecycle.addObserver(this);
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED))
                Toast.makeText(context, millisUntilFinished+"", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show();
        }
    };

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start(){
        if (!stated){
            countDownTimer.start();
            stated = true;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void stop(){
        countDownTimer.cancel();
    }

}
