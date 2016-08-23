package com.example.anya.playitrigth;

import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SoundPool.OnLoadCompleteListener {

    private SoundPool soundPool; // the sounds pool
    private int nNotes = 17;
    private int[] aIds;
    private boolean[] bLoaded = new boolean[nNotes];
    private int[] aSounds = new int[nNotes];
    private int[] aRaws;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aIds = new int[]{R.id.donote, R.id.do_sh,R.id.re,R.id.re_sh,R.id.mi,R.id.fa,R.id.fa_sh,R.id.sol,
                R.id.sol_sh,R.id.la,R.id.la_sh,R.id.ci, R.id.do1,R.id.do1_sh,R.id.re1,R.id.re1_sh,R.id.mi1};
        aRaws = new int[]{R.raw.do_oc4,R.raw.do_sh_oc4,R.raw.re_oc4,R.raw.re_sh_oc4,R.raw.mi_oc4,R.raw.fa_oc4,R.raw.fa_sh_oc4,R.raw.sol_oc4,R.raw.sol_sh_oc4,
                R.raw.la_oc4,R.raw.la_sh_oc4,R.raw.ci_oc4,R.raw.do_oc5,R.raw.do_sh_oc5,R.raw.re_oc5,R.raw.re_sh_oc5,R.raw.mi_oc5};
        Resources resources = getResources();
        for (int i=0; i<nNotes; i++)
        {
            findViewById(aIds[i]).setOnClickListener(this);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(2).build(); // Android 19 and above
        }
        else{
            soundPool = new SoundPool(2, AudioManager.STREAM_NOTIFICATION, 1); // Android below 19!
        }

        soundPool.setOnLoadCompleteListener(this);
        for (int i=0; i<nNotes; i++)
        {
            aSounds[i] = soundPool.load(this, aRaws[i], 1);
        }
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        for (int i=0; i<nNotes; i++)
        {
            if (id == aIds[i])
            {
                if (bLoaded[i]) {
                    soundPool.play(aSounds[i],
                            1, // leftVolume
                            1, // rightVolume
                            1, // priority
                            0, // loop
                            1); // rate ==> 0.5 - 2.0
                }
            }
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
    {
        for (int i = 0; i < nNotes; i++)
        {
            if (sampleId == aSounds[i])
            {
                bLoaded[i] = true;
            }
        }
    }
}