package com.example.apponometry.drag2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;


public class MainActivity extends Activity {


    String msg;
    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView blastit = (ImageView)findViewById(R.id.tryagain);
        blastit.setVisibility(View.GONE);

        RelativeLayout layout =(RelativeLayout) findViewById(R.id.brick1);
        layout.setBackgroundResource(R.drawable.brick1);
        //layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.ready));
        //blink();


        final ImageView lightit  =(ImageView)findViewById(R.id.lightit);
        lightit.setVisibility(View.GONE);

        final ImageView writing  =(ImageView)findViewById(R.id.strikeit);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        writing.startAnimation(animation);

        ImageView screencracked = (ImageView) findViewById(R.id.screencracked);
        screencracked.setVisibility(View.GONE);

        final ImageView img = (ImageView) findViewById(R.id.sticklighted);
        img.setVisibility(View.GONE);

        final ImageView imageView = (ImageView) findViewById(R.id.stickonly);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writing.clearAnimation();
                writing.setVisibility(View.GONE);
                imageView.setVisibility(View.INVISIBLE);
                img.setVisibility(View.VISIBLE);
                lightit.setVisibility(View.VISIBLE);
                final MediaPlayer mediaPlayer2 = MediaPlayer.create(MainActivity.this, R.raw.matchstickburn);
                mediaPlayer2.start();
                StartStick();
                //start series

                img.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                        ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                        View.DragShadowBuilder myShadow = new View.DragShadowBuilder(img);

                        v.startDrag(dragData, myShadow, null, 0);

                        img.setVisibility(View.INVISIBLE);
                        return true;
                    }
                });

                img.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        switch (event.getAction()) {
                            case DragEvent.ACTION_DRAG_STARTED:
                                layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                                Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");

                                // Do nothing
                                break;

                            case DragEvent.ACTION_DRAG_ENTERED:
                                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                                int x_cord = (int) event.getX();
                                int y_cord = (int) event.getY();
                                break;

                            case DragEvent.ACTION_DRAG_EXITED:
                                Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                                x_cord = (int) event.getX();
                                y_cord = (int) event.getY();
                                layoutParams.leftMargin = x_cord;
                                layoutParams.topMargin = y_cord;
                                v.setLayoutParams(layoutParams);
                                //startBomb();
                                break;

                            case DragEvent.ACTION_DRAG_LOCATION:
                                Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                                x_cord = (int) event.getX();
                                y_cord = (int) event.getY();
                                //startBomb();
                                break;

                            case DragEvent.ACTION_DRAG_ENDED:
                                Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");

                                startBomb();
                                // Do nothing
                                break;

                            case DragEvent.ACTION_DROP:
                                Log.d(msg, "ACTION_DROP event");

                                // Do nothing
                                //startBomb();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

                img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);

                            img.startDrag(data, shadowBuilder, img, 0);
                            //img.setVisibility(View.INVISIBLE);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

            }
        });


    }

    public void StartStick() {
        ImageView myAnimation = (ImageView) findViewById(R.id.sticklighted);
        final AnimationDrawable myAnimationDrawable = (AnimationDrawable) myAnimation.getDrawable();
        myAnimationDrawable.start();
    }

    public void startBomb() {
        final ImageView myAnimation = (ImageView) findViewById(R.id.bomb1);
        final AnimationDrawable myAnimationDrawable = (AnimationDrawable) myAnimation.getDrawable();
        myAnimationDrawable.start();

        final ImageView blastitagain = (ImageView)findViewById(R.id.tryagain);

        final Vibrator v;
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                final MediaPlayer mediaPlayer3 = MediaPlayer.create(MainActivity.this, R.raw.explode);
                mediaPlayer3.start();

            }
        }, 5600);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myAnimation.setVisibility(View.GONE);
                final ImageView img1 = (ImageView) findViewById(R.id.sticklighted);
                img1.setVisibility(View.GONE);

                final ImageView lightit1 = (ImageView) findViewById(R.id.lightit);
                lightit1.setVisibility(View.GONE);
                RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.brick1);
                layout1.setVisibility(View.GONE);
                //layout.setBackgroundResource(R.drawable.brick1);

                ImageView myAnimation2 = (ImageView) findViewById(R.id.screencracked);
                myAnimation2.setVisibility(View.VISIBLE);
                final AnimationDrawable myAnimationDrawable2 = (AnimationDrawable) myAnimation2.getDrawable();
                myAnimationDrawable2.start();



                /*final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ScreenCracked.class);
                        startActivity(intent);
                    }
                }, 1700);
*/

            }
        }, 6800);

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.vibrate(800);
            }
        }, 6050);

        final Handler handler6  =new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
                final MediaPlayer mediaPlayer22 = MediaPlayer.create(MainActivity.this, R.raw.glassbreak);
                mediaPlayer22.start();
            }
        }, 6800);


        final Handler handler7 = new Handler();
        handler7.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_LONG).show();
                myAnimation.setVisibility(View.GONE);
                blastitagain.setVisibility(View.VISIBLE);
                fade();
                blastitagain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }, 10000);


    }

    public void blink(){
        ImageView image = (ImageView)findViewById(R.id.strikeit);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        image.startAnimation(animation);
    }

    public void fade(){
        ImageView image = (ImageView)findViewById(R.id.tryagain);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        image.startAnimation(animation);

    }

}
/*
    public void progressbar(View view){
        ProgressBar mProgress;
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork();

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
    }

        /*ProgressDialog progressBar;
        progressBar = new ProgressDialog(view.getContext());
        Context mContext ;
        mContext = new Context(this);
        Resources res = mContext.getResources();
        Drawable drawable = res.getDrawable(R.drawable.customprogressbar);
// set the drawable as progress drawable
        progressBar.setProgressDrawable(drawable);

    }*/
