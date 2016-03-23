package com.bozon.kg.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;




public class IntroActivity extends Activity {
    private ViewPager viewPager;
    private ImageView topImage1;
    private ImageView topImage2;
    private ViewGroup bottomPages;
    private int lastPage = 0;
    private boolean justCreated = false;
    private boolean startPressed = false;
    private int[] icons;
    private int[] messages;
    public static final String APP_PREFERENCES = "FirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.intro_layout);








        icons = new int[]{
                R.drawable.intro1,
                R.drawable.intro2,
                R.drawable.intro3,
                R.drawable.intro4

        };


        messages = new int[]{
                R.string.Page1Message,
                R.string.Page2Message,
                R.string.Page3Message,
                R.string.Page4Message,
                R.string.Page5Message,
                R.string.Page6Message,
                R.string.Page7Message
        };

        viewPager = (ViewPager) findViewById(R.id.intro_view_pager);
        TextView startButton = (TextView) findViewById(R.id.start_button);
        startButton.setText("");

        topImage1 = (ImageView) findViewById(R.id.icon_image1);
        topImage2 = (ImageView) findViewById(R.id.icon_image2);

        bottomPages = (ViewGroup) findViewById(R.id.bottom_pages);
        topImage2.setVisibility(View.GONE);

        viewPager.setAdapter(new IntroAdapter());
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCROLL_STATE_IDLE || i == ViewPager.SCROLL_STATE_SETTLING) {
                    if (lastPage != viewPager.getCurrentItem()) {
                        lastPage = viewPager.getCurrentItem();

                        final ImageView fadeoutImage;
                        final ImageView fadeinImage;


                        if (topImage1.getVisibility() == View.VISIBLE) {
                            fadeoutImage = topImage1;
                            fadeinImage = topImage2;

                        } else {
                            fadeoutImage = topImage2;
                            fadeinImage = topImage1;
                        }

                        fadeinImage.bringToFront();
                        fadeinImage.setImageResource(icons[lastPage]);
                        fadeinImage.clearAnimation();
                        fadeoutImage.clearAnimation();


                        Animation outAnimation = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.icon_anim_fade_out);
                        outAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                fadeoutImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        Animation inAnimation = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.icon_anim_fade_in);
                        inAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                fadeinImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });


                        fadeoutImage.startAnimation(outAnimation);
                        fadeinImage.startAnimation(inAnimation);
                    }
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startPressed) {
                    return;
                }
                startPressed = true;
                Intent intent2 = new Intent(IntroActivity.this, MainActivity.class);
                intent2.putExtra("fromIntro", true);
                startActivity(intent2);
                finish();
            }
        });

        justCreated = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent intent3 = new Intent(IntroActivity.this, MainActivity.class);
//        startActivity(intent3);

        SharedPreferences pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        boolean b = pref.getBoolean("FirstTime",false);
        if(b)
        { new Handler().postDelayed(new Runnable() {
            public void run() {
                viewPager.setCurrentItem(0);
                lastPage = 0;
                justCreated = false;
            }
        }, 100);

        }

    }


    private class IntroAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 4;
        }


        public Spannable replaceTags(String str) {
            try {
                int start = -1;
                int startColor = -1;
                int end = -1;
                StringBuilder stringBuilder = new StringBuilder(str);
                while ((start = stringBuilder.indexOf("<br>")) != -1) {
                    stringBuilder.replace(start, start + 4, "\n");
                }
                while ((start = stringBuilder.indexOf("<br/>")) != -1) {
                    stringBuilder.replace(start, start + 5, "\n");
                }
                ArrayList<Integer> bolds = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                while ((start = stringBuilder.indexOf("<b>")) != -1 || (startColor = stringBuilder.indexOf("<c")) != -1) {
                    if (start != -1) {
                        stringBuilder.replace(start, start + 3, "");
                        end = stringBuilder.indexOf("</b>");
                        stringBuilder.replace(end, end + 4, "");
                        bolds.add(start);
                        bolds.add(end);
                    } else if (startColor != -1) {
                        stringBuilder.replace(startColor, startColor + 2, "");
                        end = stringBuilder.indexOf(">", startColor);
                        int color = Color.parseColor(stringBuilder.substring(startColor, end));
                        stringBuilder.replace(startColor, end + 1, "");
                        end = stringBuilder.indexOf("</c>");
                        stringBuilder.replace(end, end + 4, "");
                        colors.add(startColor);
                        colors.add(end);
                        colors.add(color);
                    }
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
                for (int a = 0; a < bolds.size() / 2; a++) {

                }
                for (int a = 0; a < colors.size() / 3; a++) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(colors.get(a * 3 + 2)), colors.get(a * 3), colors.get(a * 3 + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                return spannableStringBuilder;
            } catch (Exception e) {

            }
            return new SpannableStringBuilder(str);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), R.layout.intro_view_layout, null);

            TextView messageTextView = (TextView) view.findViewById(R.id.message_text);
            container.addView(view, 0);


            messageTextView.setText(this.replaceTags(getString(messages[position])));

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            int count = bottomPages.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = bottomPages.getChildAt(a);
                if (a == position) {
                    child.setBackgroundColor(0xff2ca5e0);
                } else {
                    child.setBackgroundColor(0xffbbbbbb);
                }
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }


}
