package com.example.project_1_java_new_team42.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.project_1_java_new_team42.Models.IItem;
import com.example.project_1_java_new_team42.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private List<Integer> imageResourceList;
    private Context context;

    public ImageSliderAdapter(Context context){
        this.context = context;
    }

    public void setData(List<IItem> itemDetails) {
        imageResourceList = new ArrayList<>();
        IItem itemDetail = itemDetails.get(0);

        List<String> imagePathList = itemDetail.getImagePaths();

        for (String imagePath : imagePathList){

            int imageResourceId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
            imageResourceList.add(imageResourceId);
        }

    }

    @Override
    public int getCount() {
        return imageResourceList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_slider_layout, container, false);

        ImageView image = view.findViewById(R.id.slider_image_view);

        image.setImageResource(imageResourceList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
