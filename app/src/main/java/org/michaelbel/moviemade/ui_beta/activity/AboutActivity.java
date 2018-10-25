package org.michaelbel.moviemade.ui_beta.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui_beta.fragment.AboutFragment;

public class AboutActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        startFragment(new AboutFragment(), R.id.fragment_view);
    }
}