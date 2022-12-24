package com.app.memorymaster;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class ImageData extends BaseAdapter {
    private Context context;
    private int numberofcards;
    public ImageData(Context context,int numberofcards){
       this.context=context;
       this.numberofcards=numberofcards;
    }



    @Override
    public int getCount() {
        return this.numberofcards;
    }



    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view==null){
           imageView=new ImageView(this.context);
           imageView.setLayoutParams(new ViewGroup.LayoutParams(300,400));
           imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }else{
            imageView=(ImageView) view;


        }
        imageView.setImageResource(R.drawable.hogwarts_logo_png_transparent);

        return imageView;
    }
}
