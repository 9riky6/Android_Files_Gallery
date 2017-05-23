package ricardo.android_files_gallery.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import ricardo.android_files_gallery.Files.FileManager;
import ricardo.android_files_gallery.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Storage_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Storage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Storage_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public static String rutaInterna = null;
    public static String[] rutaExterna = null;

    TextView InternalStorage, ExternalStorage,IntTotal,IntOcupated,ExTotal,ExOcupated;
    private static final Pattern DIR_SEPORATOR = Pattern.compile("/");

    private RelativeLayout phoneStorage;
    private String numero;
    private int hasPermission;

    private RelativeLayout sdStorage;
    private File StadoMemoria;
    private String numero1;
    private FragmentManager fm;
    private String numero2;

    public Storage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Storage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Storage_Fragment newInstance(String param1, String param2) {
        Storage_Fragment fragment = new Storage_Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InternalStorage = (TextView) getActivity().findViewById(R.id.textTituloInter);
        IntTotal = (TextView)getActivity().findViewById(R.id.InternoNumTotal);
        IntOcupated=(TextView)getActivity().findViewById(R.id.InternoNumUsado);
        ExTotal=(TextView)getActivity().findViewById(R.id.espacioSD_Total);
        ExOcupated= (TextView)getActivity().findViewById(R.id.espacioSD_Usado);

        rutaInterna = RutaInterna();
        rutaExterna = RutaExterna();
        fm = getChildFragmentManager();

        phoneStorage = (RelativeLayout) getActivity().findViewById(R.id.phone_storage);
        numero1 = TamanyTotalMemoria(rutaInterna);
        numero2 =TamanyOcupatMemoria(rutaInterna);
        IntTotal.setText(numero1);
        IntOcupated.setText(numero2);

        if (Build.VERSION.SDK_INT > 23) {
            hasPermission = getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        phoneStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 23) {
                    if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                        showPhoneStorage();
                    } else {
                        Toast.makeText(getContext(), "No permission", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showPhoneStorage();
                }
            }
        });

        sdStorage = (RelativeLayout) getActivity().findViewById(R.id.sd_storage);
        numero1 = TamanyTotalMemoria(rutaExterna[0]);
        numero2= TamanyOcupatMemoria(rutaExterna[0]);
        ExTotal.setText(numero1);
        ExOcupated.setText(numero2);
        StadoMemoria = new File(rutaExterna[0]);
        numero = String.valueOf(StadoMemoria.getTotalSpace());
        if (Environment.getExternalStorageState(StadoMemoria).equalsIgnoreCase("removed") || rutaExterna[0].equals(rutaInterna)) {
            sdStorage.setVisibility(View.GONE);
        } else {
            sdStorage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT > 23) {
                        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                            showSdStorage();
                        } else {
                            Toast.makeText(getContext(), "No permission", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        showSdStorage();
                    }
                }
            });
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private String RutaInterna() {
        String ruta = null;
        ruta = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (ruta.contains("emulated")) {
            InternalStorage.setText("Memoria Interna");
        }if(ruta.contains("sdcard")){
            InternalStorage.setText("Memoria interna emulador");
        }
        return ruta;
    }
    private String[] RutaExterna() {
        // Final set of paths
        final Set<String> rv = new HashSet<String>();
        // Primary physical SD-CARD (not emulated)
        final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
        // All Secondary SD-CARDs (all exclude primary) separated by ":"
        final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
        // Primary emulated SD-CARD
        final String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
        if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
            // Device has physical external storage; use plain paths.
            if (TextUtils.isEmpty(rawExternalStorage)) {
                // EXTERNAL_STORAGE undefined; falling back to default.
                rv.add("/storage/sdcard0");
            } else {
                rv.add(rawExternalStorage);
            }
        } else {
            // Device has emulated storage; external storage paths should have
            // userId burned into them.
            final String rawUserId;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                rawUserId = "";
            } else {
                final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                final String[] folders = DIR_SEPORATOR.split(path);
                final String lastFolder = folders[folders.length - 1];
                boolean isDigit = false;
                try {
                    Integer.valueOf(lastFolder);
                    isDigit = true;
                } catch (NumberFormatException ignored) {
                }
                rawUserId = isDigit ? lastFolder : "";
            }
            // /storage/emulated/0[1,2,...]
            if (TextUtils.isEmpty(rawUserId)) {
                rv.add(rawEmulatedStorageTarget);
            } else {
                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId);
            }
        }
        // Add all secondary storages
        if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
            // All Secondary SD-CARDs splited into array
            final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
            Collections.addAll(rv, rawSecondaryStorages);
        }
        return rv.toArray(new String[rv.size()]);
    }
    public String TamanyTotalMemoria(String ruta) {
        DecimalFormat df = new DecimalFormat("###.##");
        File arxiu = new File(ruta);
        //ESPAI TOTAL
        String numero = null;//bytes
        float auxNum = arxiu.getTotalSpace();//numero temporal per pasar el valors i fer la combercio
        if (arxiu.getTotalSpace() <=1024) { //kilobyte
            numero = df.format(auxNum) + " B";
        } else if (arxiu.getTotalSpace() >1024  && arxiu.getTotalSpace() <= 1048576) {
            numero = df.format(auxNum / 1024) + " KB";
        } else if (arxiu.getTotalSpace() > 1048576  && arxiu.getTotalSpace() <= 1073741824 ) {
            numero = df.format(auxNum / 1048576) + " MB";
        } else {
            numero = df.format(auxNum / 1073741824) + " GB";
        }
        return numero;
    }
    public String TamanyOcupatMemoria(String ruta) {
        String numero1= "npi";
        DecimalFormat df = new DecimalFormat("###.##");
        File arxiu = new File(ruta);
        float num = arxiu.getTotalSpace()-arxiu.getFreeSpace();
        if (num <=1024) {
            numero1 = df.format(num) + " B";
        } else if (num >1024 && num <=1048576 ) {
            numero1 = df.format(num / 1024) + " KB";
        } else if (num > 1048576  && num <= 1073741824) {
            numero1 = df.format(num / 1048576) + " MB ";
        } else if (num > 1073741824) {
            numero1 = df.format(num / 1073741824) + " GB ";
        }
        return numero1;
    }
    private void showPhoneStorage() {
       // Toast.makeText(getContext(), numero, Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(),rutaInterna+"/", Toast.LENGTH_LONG).show();
        //Toast.makeText(MainActivity.this,mostrar, Toast.LENGTH_LONG).show();
        //File manager <3

        Intent intent = new Intent(getContext(), FileManager.class);
        intent.putExtra("path", rutaInterna + "/"); //rutaInterna
        intent.putExtra("root", rutaInterna + "/");
        startActivity(intent);
    }
    private void showSdStorage() {
        //Toast.makeText(getContext(), numero1, Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), rutaExterna[0] + "/", Toast.LENGTH_LONG).show();
        // Toast.makeText(MainActivity.this,Environment.getExternalStorageState(StadoMemoria).toString(), Toast.LENGTH_LONG).show();

        //File manager SD External
        Intent intent = new Intent(getContext(), FileManager.class);
        intent.putExtra("path", rutaExterna[0] + "/"); //rutaInterna
        intent.putExtra("root", rutaExterna[0] + "/");
        startActivity(intent);

    }
}
