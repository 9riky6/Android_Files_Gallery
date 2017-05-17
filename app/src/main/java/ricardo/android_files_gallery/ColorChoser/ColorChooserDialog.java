package ricardo.android_files_gallery.ColorChoser;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import ricardo.android_files_gallery.R;


public class ColorChooserDialog extends Dialog{
    public ColorChooserDialog(Context context) {
        super(context);
    }

    private ImageButton one;
    private ImageButton two;
    private ImageButton three;
    private ImageButton four;
    private ImageButton five;
    private ImageButton six;
    private ImageButton seven;
    private ImageButton eight;
    private ImageButton nine;
    private ImageButton ten;
    private ImageButton eleven;
    private ImageButton twelve;
    private ImageButton thirteen;
    private ImageButton fourteen;
    private ImageButton fifteen;
    private ImageButton sixteen;
    private ImageButton seventeen;
    private ImageButton eighteen;
    private ImageButton nineteen;
    private ImageButton twenty;
    private ImageButton twentyone;
    private ImageButton twentytwo;
    private ImageButton twentythree;
    private ImageButton twentyfour;
    private ImageButton twentyfive;
    private ImageButton twentysix;
    private ImageButton twentyseven;
    private ImageButton twentyeight;
//    private Button twentyOne;

    private List<Integer> colors;
    private List<ImageButton> buttons;

    private ColorListener myColorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_dialog);

        one =      (ImageButton)findViewById(R.id.b1);
        two =      (ImageButton)findViewById(R.id.b2);
        three =    (ImageButton)findViewById(R.id.b3);
        four =     (ImageButton)findViewById(R.id.b4);
        five =     (ImageButton)findViewById(R.id.b5);
        six =      (ImageButton)findViewById(R.id.b6);
        seven =    (ImageButton)findViewById(R.id.b7);
        eight =    (ImageButton)findViewById(R.id.b8);
        nine =     (ImageButton)findViewById(R.id.b9);
        ten =      (ImageButton)findViewById(R.id.b10);
        eleven =   (ImageButton)findViewById(R.id.b11);
        twelve =   (ImageButton)findViewById(R.id.b12);
        thirteen = (ImageButton)findViewById(R.id.b13);
        fourteen = (ImageButton)findViewById(R.id.b14);
        fifteen =  (ImageButton)findViewById(R.id.b15);
        sixteen =  (ImageButton)findViewById(R.id.b16);
        seventeen =(ImageButton)findViewById(R.id.b17);
        eighteen = (ImageButton)findViewById(R.id.b18);
        nineteen = (ImageButton)findViewById(R.id.b19);
        twenty =   (ImageButton)findViewById(R.id.b20);
        twentyone = (ImageButton)findViewById(R.id.b21);
        twentytwo = (ImageButton)findViewById(R.id.b22);
        twentythree =  (ImageButton)findViewById(R.id.b23);
        twentyfour =  (ImageButton)findViewById(R.id.b24);
        twentyfive =(ImageButton)findViewById(R.id.b25);
        twentysix = (ImageButton)findViewById(R.id.b26);
        twentyseven = (ImageButton)findViewById(R.id.b27);
        twentyeight =   (ImageButton)findViewById(R.id.b28);



        colors = new ArrayList<>();
        colors.add(Constant.maraschino);
        colors.add(Constant.cayenne);
        colors.add(Constant.marron);
        colors.add(Constant.plum);
        colors.add(Constant.eggplant);
        colors.add(Constant.grape);
        colors.add(Constant.orchid);
        colors.add(Constant.lavender);
        colors.add(Constant.carnation);
        colors.add(Constant.strawberry);
        colors.add(Constant.bubblegum);
        colors.add(Constant.magenta);
        colors.add(Constant.salmon);
        colors.add(Constant.tangerine);
        colors.add(Constant.cantaloupe);
        colors.add(Constant.banana);
        colors.add(Constant.lemon);
        colors.add(Constant.honeydew);
        colors.add(Constant.lime);
        colors.add(Constant.spring);
        colors.add(Constant.clover);
        colors.add(Constant.fern);
        colors.add(Constant.moss);
        colors.add(Constant.flora);
        colors.add(Constant.seafoam);
        colors.add(Constant.spindrift);
        colors.add(Constant.teal);
        colors.add(Constant.sky);
        colors.add(Constant.tuquoise);

        buttons = new ArrayList<>();
        buttons.add(one);
        buttons.add(two);
        buttons.add(three);
        buttons.add(four);
        buttons.add(five);
        buttons.add(six);
        buttons.add(seven);
        buttons.add(eight);
        buttons.add(nine);
        buttons.add(ten);
        buttons.add(eleven);
        buttons.add(twelve);
        buttons.add(thirteen);
        buttons.add(fourteen);
        buttons.add(fifteen);
        buttons.add(sixteen);
        buttons.add(seventeen);
        buttons.add(eighteen);
        buttons.add(nineteen);
        buttons.add(twenty);
        buttons.add(twentyone);
        buttons.add(twentytwo);
        buttons.add(twentythree);
        buttons.add(twentyfour);
        buttons.add(twentyfive);
        buttons.add(twentysix);
        buttons.add(twentyseven);
        buttons.add(twentyeight);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Colorize();
        }else{
            ColorizeOld();
        }

        setListeners();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(myColorListener != null)
            myColorListener.OnColorClick(v, Integer.parseInt(v.getTag().toString()));
            dismiss();
        }
    };

    private void setListeners() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setTag(colors.get(i));
            buttons.get(i).setOnClickListener(listener);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void Colorize() {
        for (int i = 0; i < buttons.size(); i++) {
            ShapeDrawable d = new ShapeDrawable(new OvalShape());
            d.setBounds(32, 32, 32, 32);
            Log.e("Shape drown no", i + "");
            buttons.get(i).setVisibility(View.INVISIBLE);

                d.getPaint().setStyle(Paint.Style.FILL);
                d.getPaint().setColor(colors.get(i));

            buttons.get(i).setBackground(d);
        }
            animate();
    }

    private void ColorizeOld() {
        for (int i = 0; i < buttons.size(); i++) {
            ShapeDrawable d = new ShapeDrawable(new OvalShape());
            d.getPaint().setColor(colors.get(i));
            d.getPaint().setStrokeWidth(1f);
            d.setBounds(32, 32, 32, 32);
            buttons.get(i).setVisibility(View.INVISIBLE);
                d.getPaint().setStyle(Paint.Style.FILL);
                d.getPaint().setColor(colors.get(i));
        buttons.get(i).setBackgroundDrawable(d);
        }
        animate();
    }


    private void animate(){
        Log.e("animate","true");
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                Log.e("animator 1","r");
                animator(one);
                animator(two);
                animator(three);
                animator(four);
                animator(five);
                animator(six);
                animator(seven);
                animator(eight);
                animator(nine);
                animator(ten);
                animator(eleven);
                animator(twelve);
                animator(thirteen);
                animator(fourteen);
                animator(fifteen);
                animator(sixteen);
                animator(seventeen);
                animator(eighteen);
                animator(nineteen);
                animator(twenty);
                animator(twentyone);
                animator(twentytwo);
                animator(twentythree);
                animator(twentyfour);
                animator(twentyfive);
                animator(twentysix);
                animator(twentyseven);
                animator(twentyeight);
            }
        };
        android.os.Handler handler = new android.os.Handler();
        int counter = 85;
        handler.postDelayed(r1,counter);
    }


    private void animator(final ImageButton imageButton){
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.color_item);
        animation.setInterpolator(new AccelerateInterpolator());
        imageButton.setAnimation(animation);
        imageButton.setVisibility(View.VISIBLE);
        animation.start();
    }


    public void setColorListener(ColorListener listener){
        this.myColorListener = listener;
    }
}
