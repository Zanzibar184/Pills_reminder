package com.example.zanzibar.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Akshay Raj on 7/28/2016.
 * Snow Corporation Inc.
 * www.snowcorp.org
 */
public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private Session session;

    SharedPreferences.Editor editor_privacy = null;
    SharedPreferences prefs_privacy = null;

    SharedPreferences pref = null;
    SharedPreferences.Editor editor = null;

    boolean privacyAccepted = false;
    boolean intro = false;
    CheckBox checkPrivacy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getApplicationContext().getSharedPreferences("snow-intro-slider", 0);
        editor = pref.edit();
        intro = pref.getBoolean("imposta_info_app", false);

        editor_privacy = getApplicationContext().getSharedPreferences("Privacy", MODE_PRIVATE).edit();
        prefs_privacy = getSharedPreferences("Privacy", MODE_PRIVATE);

        // Checking for first time launch - before calling setContentView()
        session = new Session(this);
        if (!session.isFirstTimeLaunch() && !intro) {
            launchHomeScreen();
            finish();
        } else if (session.isFirstTimeLaunch() || intro) {

            if (session.isFirstTimeLaunch()) {
                session.setFirstTimeLaunch(true);
            }

            // Making notification bar transparent
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

            setContentView(R.layout.activity_welcome);

            viewPager = (ViewPager) findViewById(R.id.view_pager);
            dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
            btnSkip = (Button) findViewById(R.id.btn_skip);
            btnNext = (Button) findViewById(R.id.btn_next);

            if (session.isFirstTimeLaunch()) {
                btnSkip.setVisibility(View.GONE);
            }


            // layouts of all welcome sliders
            // add few more layouts if you want
            layouts = new int[]{
                    R.layout.welcome_slide1,
                    R.layout.welcome_slide2,
                    R.layout.welcome_slide3,
                    R.layout.welcome_slide4,
                    R.layout.welcome_slide5};

            // adding bottom dots
            addBottomDots(0);

            // making notification bar transparent
            changeStatusBarColor();

            myViewPagerAdapter = new MyViewPagerAdapter();
            viewPager.setAdapter(myViewPagerAdapter);
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchHomeScreen();
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // checking for last page
                    // if last page home screen will be launched
                    int current = getItem(+1);
                    if (current < layouts.length) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {
                        launchHomeScreen();
                    }
                }
            });

            editor.putBoolean("imposta_info_app", false);
            editor.apply();
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        session.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            /*
            if(position==0) {
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setDuration(10000);
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                findViewById(R.id.img_welcome1).startAnimation(rotateAnimation);
                Log.i("LOG", "LOGGEE");
            }*/

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                checkPrivacy = (CheckBox) findViewById(R.id.checkPrivacy);
                btnNext.setText(getString(R.string.start));
                Log.i("accept", prefs_privacy.getBoolean("privacy_accepted", false) + "");
                privacyAccepted = prefs_privacy.getBoolean("privacy_accepted", false);
                if (privacyAccepted) {
                    checkPrivacy.setChecked(true);
                    btnNext.setVisibility(View.VISIBLE);
                } else {
                    btnNext.setVisibility(View.GONE);
                }
                if (intro) {
                    checkPrivacy.setVisibility(View.GONE);
                }
                checkPrivacy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkPrivacy.isChecked()){
                            editor_privacy.putBoolean("privacy_accepted", true);
                            editor_privacy.apply();
                            privacyAccepted = prefs_privacy.getBoolean("privacy_accepted", false);
                            btnNext.setVisibility(View.VISIBLE);
                        } else {
                            editor_privacy.putBoolean("privacy_accepted", false);
                            editor_privacy.apply();
                            privacyAccepted = prefs_privacy.getBoolean("privacy_accepted", false);
                            btnNext.setVisibility(View.GONE);
                        }
                    }
                });
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
            }
            if (!intro) {
                btnSkip.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);

            if (layouts[position] == R.layout.welcome_slide1) {
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setDuration(10000);
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                view.findViewById(R.id.img_welcome1).startAnimation(rotateAnimation);
            }

            if (layouts[position] == R.layout.welcome_slide5) {
                if (intro) {
                    ScrollView scroll_informativa_privacy = (ScrollView) view.findViewById(R.id.scroll_informativa);
                    scroll_informativa_privacy.setVisibility(View.GONE);
                    TextView txt_titolo_consenso = (TextView) view.findViewById(R.id.txt_titolo_consenso_informativa);
                    txt_titolo_consenso.setText(getString(R.string.txt_consenso_ok));
                }
            }

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}