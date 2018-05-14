package com.example.zanzibar.myapplication.frames;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

/**
 * Created by user on 11/05/18.
 */

public class ScegliPillola extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_sceglipillola = null;
    Button bu;

    ImageView pill_image;
    ImageView arrow_left_image;
    ImageView arrow_right_image;
    TextView name_pill;

    Drawable img_state = null;

    public ScegliPillola() {
        // Required empty public constructor
    }

    public ScegliPillola(FloatingActionButton fab_sceglipillola) {
        this.fab_sceglipillola = fab_sceglipillola;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_sceglipillola, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutsceglipillola);
        fab_sceglipillola.hide();
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_sceglipillola, linearLayout, false);
        linearLayout.addView(frame);

        pill_image = (ImageView) view.findViewById(R.id.img_pill);
        arrow_left_image = (ImageView) view.findViewById(R.id.image_arrow_left);
        arrow_right_image = (ImageView) view.findViewById(R.id.image_arrow_right);
        name_pill = (TextView) view.findViewById(R.id.txt_tipo_farmaco);

        final Drawable id_img_pill = getResources().getDrawable(R.drawable.pill_colored);
        final Drawable id_img_siringa = getResources().getDrawable(R.drawable.syringe);
        final Drawable id_img_gocce = getResources().getDrawable(R.drawable.gocce);
        final Drawable id_img_spray = getResources().getDrawable(R.drawable.spray);
        final Drawable id_img_sciroppo = getResources().getDrawable(R.drawable.sciroppo);
        final Drawable id_img_pomata = getResources().getDrawable(R.drawable.pomata);

        arrow_left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_state = pill_image.getDrawable();
                if(areDrawablesIdentical(img_state,id_img_pill)) {
                    pill_image.setImageResource(R.drawable.spray);
                    name_pill.setText("Spray");
                } else if(areDrawablesIdentical(img_state,id_img_spray)) {
                    pill_image.setImageResource(R.drawable.pomata);
                    name_pill.setText("Pomata");
                } else if(areDrawablesIdentical(img_state,id_img_pomata)) {
                    pill_image.setImageResource(R.drawable.gocce);
                    name_pill.setText("Gocce");
                } else if(areDrawablesIdentical(img_state,id_img_gocce)) {
                    pill_image.setImageResource(R.drawable.sciroppo);
                    name_pill.setText("Sciroppo");
                } else if(areDrawablesIdentical(img_state,id_img_sciroppo)) {
                    pill_image.setImageResource(R.drawable.syringe);
                    name_pill.setText("Iniezione");
                } else if(areDrawablesIdentical(img_state,id_img_siringa)) {
                    pill_image.setImageResource(R.drawable.pill_colored);
                    name_pill.setText("Pillola");
                }
            }
        });

        arrow_right_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_state = pill_image.getDrawable();
                if(areDrawablesIdentical(img_state,id_img_pill)) {
                    pill_image.setImageResource(R.drawable.syringe);
                    name_pill.setText("Iniezione");
                } else if(areDrawablesIdentical(img_state,id_img_siringa)) {
                    pill_image.setImageResource(R.drawable.sciroppo);
                    name_pill.setText("Sciroppo");
                } else if(areDrawablesIdentical(img_state,id_img_sciroppo)) {
                    pill_image.setImageResource(R.drawable.gocce);
                    name_pill.setText("Gocce");
                } else if(areDrawablesIdentical(img_state,id_img_gocce)) {
                    pill_image.setImageResource(R.drawable.pomata);
                    name_pill.setText("Pomata");
                } else if(areDrawablesIdentical(img_state,id_img_pomata)) {
                    pill_image.setImageResource(R.drawable.spray);
                    name_pill.setText("Spray");
                } else if(areDrawablesIdentical(img_state,id_img_spray)) {
                    pill_image.setImageResource(R.drawable.pill_colored);
                    name_pill.setText("Pillola");
                }
            }
        });

        pill_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AggiungiPillola aggiungiPillola = new AggiungiPillola(fab_sceglipillola,pill_image.getDrawable());
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiPillola).commit();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Scegli il tipo di pillola");
    }

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }

}
