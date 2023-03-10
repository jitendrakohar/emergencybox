package com.example.emergencybox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.emergencybox.adapter.OnboardingAdapter;
import com.example.emergencybox.adapter.OnboardingItem;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    List<OnboardingItem> onboardingItems;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnBoardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnBoardingAction);
        setupOnboardingItems();
        ViewPager2 onboardingViewPager = findViewById(R.id.onBoardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);
        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);

            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);

                } else {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finish();
                }
            }
        });

    }

    private void setupOnboardingItems() {
        onboardingItems = new ArrayList<>();
        OnboardingItem itemPayOnline = new OnboardingItem();
        itemPayOnline.setTitle("Lets build our community safe and help them during Emergency");
        itemPayOnline.setDescription("Our aim is to take care of the society ongoing emergency");
        itemPayOnline.setImage(R.drawable.community);

        OnboardingItem itemOnTheWay = new OnboardingItem();
        itemOnTheWay.setTitle("Lets promise to create a safer community");
        itemOnTheWay.setDescription("we promise each other to have a safer community ");
        itemOnTheWay.setImage(R.drawable.social_community);

        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("Protecting the women, child is our priority");
        itemEatTogether.setDescription("Call to hospital or parents when some emergency condition happens. \n Don't forget to rate us ");
        itemEatTogether.setImage(R.drawable.emergencycall);

        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOnTheWay);
        onboardingItems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);


    }

    public void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT

        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);

        }
    }


    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
            if(index==onboardingAdapter.getItemCount()-1){
                buttonOnboardingAction.setText("Start");
            }
            else{
                buttonOnboardingAction.setText("Next");
            }
        }
    }


}
