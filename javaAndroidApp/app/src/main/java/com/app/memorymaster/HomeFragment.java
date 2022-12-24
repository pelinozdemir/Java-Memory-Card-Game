package com.app.memorymaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button ikibtn = (Button) view.findViewById(R.id.ikigridbtn);
        Button dortbtn = (Button) view.findViewById(R.id.dortgridbtn);
        Button altibtn = (Button) view.findViewById(R.id.altigridbtn);
        GridView gridView=view.findViewById(R.id.grid);
        gridView.setNumColumns(2);
        gridView.setAdapter(new HomeFragment.ImageData(getActivity(),4));

        ikibtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               gridView.setNumColumns(2);
               gridView.setClickable(false);
               gridView.setAdapter(new HomeFragment.ImageData(getActivity(),4));
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                        .setTitle("2*2")
                        .setMessage("Start Game")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), Single2PlayerActivity.class));




                            }
                        })
                        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {





                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

              //  startActivity(new Intent(getActivity(), Single2PlayerActivity.class));

            }
        });
        dortbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridView.setNumColumns(4);
                gridView.setClickable(false);
                gridView.setAdapter(new HomeFragment.ImageData(getActivity(),16));

                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                        .setTitle("4*4")
                        .setMessage("Start Game")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), Single4PlayerActivity.class));




                            }
                        })
                        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {





                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
               // startActivity(new Intent(getActivity(),Single4PlayerActivity.class));
            }
        });

        altibtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridView.setNumColumns(6);
                gridView.setClickable(false);
                gridView.setAdapter(new HomeFragment.ImageData(getActivity(),36));


                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                        .setTitle("6*6")
                        .setMessage("Start Game")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), Single6PlayerActivity.class));




                            }
                        })
                        .setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {





                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                //startActivity(new Intent(getActivity(), Single6PlayerActivity.class));

            }
        });


        return view;
    }
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

                imageView.setLayoutParams(new ViewGroup.LayoutParams(150,250));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }else{


                imageView=(ImageView) view;



            }

            imageView.setImageResource(R.drawable.hogwarts_logo_png_transparent);

            return imageView;
        }
    }


}