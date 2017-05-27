package ricardo.android_files_gallery.Settings;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import ricardo.android_files_gallery.R;

/**
 * Created by Ricardo on 21/05/2017.
 */


public class ColorChooserDialog extends DialogFragment
        implements View.OnClickListener {
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10,
            cardView11, cardView12, cardView13, cardView14, cardView15, cardView16, cardView17, cardView18, cardView19, cardView20,
            cardView21, cardView22, cardView23, cardView24, cardView25, cardView26, cardView27, cardView28, cardView29, cardView30;
    Button buttonDisagree, buttonAgree;
    View view;
    int currentTheme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityOptions options;
    Intent intent;
    Boolean themeChanged = false;

    /**
     * En este metodo recogeremos el color que queremos aplicar he inflaremos
     * un dialog donde aparecen los colores.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        currentTheme = sharedPreferences.getInt("THEME", 0);
        view = inflater.inflate(R.layout.theme_dialog, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogButtons();

        return view;
    }

    /**
     * Metodo donde buscamos todos los campos en el layout asociado a esta clase
     * y creamos los listeneres de cada campo.
     */
    private void dialogButtons() {
        cardView1 = (CardView) view.findViewById(R.id.card_view1);
        cardView2 = (CardView) view.findViewById(R.id.card_view2);
        cardView3 = (CardView) view.findViewById(R.id.card_view3);
        cardView4 = (CardView) view.findViewById(R.id.card_view4);
        cardView5 = (CardView) view.findViewById(R.id.card_view5);
        cardView6 = (CardView) view.findViewById(R.id.card_view6);
        cardView7 = (CardView) view.findViewById(R.id.card_view7);
        cardView8 = (CardView) view.findViewById(R.id.card_view8);
        cardView9 = (CardView) view.findViewById(R.id.card_view9);
        cardView10 = (CardView) view.findViewById(R.id.card_view10);
        cardView11 = (CardView) view.findViewById(R.id.card_view11);
        cardView12 = (CardView) view.findViewById(R.id.card_view12);
        cardView13 = (CardView) view.findViewById(R.id.card_view13);
        cardView14 = (CardView) view.findViewById(R.id.card_view14);
        cardView15 = (CardView) view.findViewById(R.id.card_view15);
        cardView16 = (CardView) view.findViewById(R.id.card_view16);
        cardView17 = (CardView) view.findViewById(R.id.card_view17);
        cardView18 = (CardView) view.findViewById(R.id.card_view18);
        cardView19 = (CardView) view.findViewById(R.id.card_view19);
        cardView20 = (CardView) view.findViewById(R.id.card_view20);
        cardView21 = (CardView) view.findViewById(R.id.card_view21);
        cardView22 = (CardView) view.findViewById(R.id.card_view22);
        cardView23 = (CardView) view.findViewById(R.id.card_view23);
        cardView24 = (CardView) view.findViewById(R.id.card_view24);
        cardView25 = (CardView) view.findViewById(R.id.card_view25);
        cardView26 = (CardView) view.findViewById(R.id.card_view26);
        cardView27 = (CardView) view.findViewById(R.id.card_view27);
        cardView28 = (CardView) view.findViewById(R.id.card_view28);
        cardView29 = (CardView) view.findViewById(R.id.card_view29);
        cardView30 = (CardView) view.findViewById(R.id.card_view30);
        buttonDisagree = (Button) view.findViewById(R.id.buttonDisagree);
        buttonAgree = (Button) view.findViewById(R.id.buttonAgree);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);
        cardView6.setOnClickListener(this);
        cardView7.setOnClickListener(this);
        cardView8.setOnClickListener(this);
        cardView9.setOnClickListener(this);
        cardView10.setOnClickListener(this);
        cardView11.setOnClickListener(this);
        cardView12.setOnClickListener(this);
        cardView13.setOnClickListener(this);
        cardView14.setOnClickListener(this);
        cardView15.setOnClickListener(this);
        cardView16.setOnClickListener(this);
        cardView17.setOnClickListener(this);
        cardView18.setOnClickListener(this);
        cardView19.setOnClickListener(this);
        cardView20.setOnClickListener(this);
        cardView21.setOnClickListener(this);
        cardView22.setOnClickListener(this);
        cardView23.setOnClickListener(this);
        cardView24.setOnClickListener(this);
        cardView25.setOnClickListener(this);
        cardView26.setOnClickListener(this);
        cardView27.setOnClickListener(this);
        cardView28.setOnClickListener(this);
        cardView29.setOnClickListener(this);
        cardView30.setOnClickListener(this);
        buttonDisagree.setOnClickListener(this);
        buttonAgree.setOnClickListener(this);
    }

    /**
     * Gestionamos el click en cada color del dialog guardar el nuevo tema,
     * para aplicarlo tendremos que pulsar en Agree
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view1:
                Toast.makeText(getContext(), "pulsado", Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(1);
                break;
            case R.id.card_view2:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(2);
                break;
            case R.id.card_view3:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(3);
                break;
            case R.id.card_view4:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(4);
                break;
            case R.id.card_view5:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(5);
                break;
            case R.id.card_view6:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(6);
                break;
            case R.id.card_view7:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(7);
                break;
            case R.id.card_view8:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(8);
                break;
            case R.id.card_view9:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(9);
                break;
            case R.id.card_view10:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(10);
                break;
            case R.id.card_view11:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(11);
                break;
            case R.id.card_view12:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(12);
                break;
            case R.id.card_view13:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(13);
                break;
            case R.id.card_view14:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(14);
                break;
            case R.id.card_view15:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(15);
                break;
            case R.id.card_view16:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(16);
                break;
            case R.id.card_view17:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(17);
                break;
            case R.id.card_view18:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(18);
                break;
            case R.id.card_view19:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(19);
                break;
            case R.id.card_view20:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(20);
                break;
            case R.id.card_view21:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(21);
                break;
            case R.id.card_view22:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(22);
                break;
            case R.id.card_view23:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(23);
                break;
            case R.id.card_view24:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(24);
                break;
            case R.id.card_view25:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(25);
                break;
            case R.id.card_view26:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(26);
                break;
            case R.id.card_view27:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(27);
                break;
            case R.id.card_view28:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(28);
                break;
            case R.id.card_view29:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(29);
                break;
            case R.id.card_view30:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(30);
                break;
            case R.id.buttonDisagree:
                sharedPreferences.edit().putBoolean("THEMECHANGED", false).apply();
                ((Settings) getActivity()).setThemeFragment(currentTheme);
                getDialog().dismiss();
                break;
            case R.id.buttonAgree:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
                break;
        }
    }
}