package ricardo.android_files_gallery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import ricardo.android_files_gallery.MainActivity;
import ricardo.android_files_gallery.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FileManagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FileManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
FileManagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> ElementEliminar;
    private ImageButton bCrearCarpeta, bBorrarCarpeta,
            bCopiarCarpeta, selecteItemRemove;

    public FileManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FileManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FileManagerFragment newInstance(String param1, String param2) {
        FileManagerFragment fragment = new FileManagerFragment();
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
        String pathtext = getArguments().getString("path");
        String roottext = getArguments().getString("root");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.file_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ElementEliminar = new ArrayList<String>(100);
        bCrearCarpeta = (ImageButton) getActivity().findViewById(R.id.imageButton_crear_carpeta);
        bBorrarCarpeta = (ImageButton) getActivity().findViewById(R.id.imageButton_borrar_carpeta);
        bCopiarCarpeta = (ImageButton) getActivity().findViewById(R.id.imageButton_copiar_carpeta);
        selecteItemRemove = (ImageButton) getActivity().findViewById(R.id.remove);

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

    private void getImatge(String name, ImageView imatge) {
        String extencionFile = name.substring(name.lastIndexOf(".") + 1);
        //Log.d("Contenido extencionFile", extencionFile);

        if (extencionFile.equalsIgnoreCase("jpg")) {
            imatge.setImageResource(R.drawable.jpg);

        } else if (extencionFile.equalsIgnoreCase(("ai"))) {
            imatge.setImageResource(R.drawable.ai);

        } else if (extencionFile.equalsIgnoreCase(("apk"))) {
            imatge.setImageResource(R.drawable.apk);

        } else if (extencionFile.equalsIgnoreCase(("avi"))) {
            imatge.setImageResource(R.drawable.avi);

        } else if (extencionFile.equalsIgnoreCase(("bmp"))) {
            imatge.setImageResource(R.drawable.bmp);

        } else if (extencionFile.equalsIgnoreCase(("cad"))) {
            imatge.setImageResource(R.drawable.cad);

        } else if (extencionFile.equalsIgnoreCase(("cdr"))) {
            imatge.setImageResource(R.drawable.cdr);

        } else if (extencionFile.equalsIgnoreCase(("css"))) {
            imatge.setImageResource(R.drawable.css);

        } else if (extencionFile.equalsIgnoreCase(("dat"))) {
            imatge.setImageResource(R.drawable.dat);

        } else if (extencionFile.equalsIgnoreCase(("dll"))) {
            imatge.setImageResource(R.drawable.dll);

        } else if (extencionFile.equalsIgnoreCase(("dmg"))) {
            imatge.setImageResource(R.drawable.dmg);

        } else if (extencionFile.equalsIgnoreCase(("doc"))) {
            imatge.setImageResource(R.drawable.doc);

        } else if (extencionFile.equalsIgnoreCase(("eps"))) {
            imatge.setImageResource(R.drawable.eps);

        } else if (extencionFile.equalsIgnoreCase(("fla"))) {
            imatge.setImageResource(R.drawable.fla);

        } else if (extencionFile.equalsIgnoreCase(("flv"))) {
            imatge.setImageResource(R.drawable.flv);

        } else if (extencionFile.equalsIgnoreCase(("gif"))) {
            imatge.setImageResource(R.drawable.gif);

        } else if (extencionFile.equalsIgnoreCase(("html"))) {
            imatge.setImageResource(R.drawable.html);

        } else if (extencionFile.equalsIgnoreCase(("indd"))) {
            imatge.setImageResource(R.drawable.indd);

        } else if (extencionFile.equalsIgnoreCase(("iso"))) {
            imatge.setImageResource(R.drawable.iso);

        } else if (extencionFile.equalsIgnoreCase(("js"))) {
            imatge.setImageResource(R.drawable.js);

        } else if (extencionFile.equalsIgnoreCase(("midi"))) {
            imatge.setImageResource(R.drawable.midi);

        } else if (extencionFile.equalsIgnoreCase(("mkv"))) {
            imatge.setImageResource(R.drawable.mkv);

        } else if (extencionFile.equalsIgnoreCase(("mov"))) {
            imatge.setImageResource(R.drawable.mov);

        } else if (extencionFile.equalsIgnoreCase(("mp3"))) {
            imatge.setImageResource(R.drawable.mp3);

        } else if (extencionFile.equalsIgnoreCase(("mpg"))) {
            imatge.setImageResource(R.drawable.mpg);

        } else if (extencionFile.equalsIgnoreCase(("php"))) {
            imatge.setImageResource(R.drawable.php);

        } else if (extencionFile.equalsIgnoreCase(("pdf"))) {
            imatge.setImageResource(R.drawable.pdf);

        } else if (extencionFile.equalsIgnoreCase(("png"))) {
            imatge.setImageResource(R.drawable.png);

        } else if (extencionFile.equalsIgnoreCase(("ppt"))) {
            imatge.setImageResource(R.drawable.ppt);

        } else if (extencionFile.equalsIgnoreCase(("ps"))) {
            imatge.setImageResource(R.drawable.ps);

        } else if (extencionFile.equalsIgnoreCase(("psd"))) {
            imatge.setImageResource(R.drawable.psd);

        } else if (extencionFile.equalsIgnoreCase(("rar"))) {
            imatge.setImageResource(R.drawable.rar);

        } else if (extencionFile.equalsIgnoreCase(("raw"))) {
            imatge.setImageResource(R.drawable.raw);

        } else if (extencionFile.equalsIgnoreCase(("sql"))) {
            imatge.setImageResource(R.drawable.sql);

        } else if (extencionFile.equalsIgnoreCase(("svg"))) {
            imatge.setImageResource(R.drawable.svg);

        } else if (extencionFile.equalsIgnoreCase(("tif"))) {
            imatge.setImageResource(R.drawable.tif);

        } else if (extencionFile.equalsIgnoreCase(("txt"))) {
            imatge.setImageResource(R.drawable.txt);

        } else if (extencionFile.equalsIgnoreCase(("wmv"))) {
            imatge.setImageResource(R.drawable.wmv);

        } else if (extencionFile.equalsIgnoreCase(("xls"))) {
            imatge.setImageResource(R.drawable.xls);

        } else if (extencionFile.equalsIgnoreCase(("xml"))) {
            imatge.setImageResource(R.drawable.xml);

        } else if (extencionFile.equalsIgnoreCase(("zip"))) {
            imatge.setImageResource(R.drawable.zip);

        } else if (extencionFile.equalsIgnoreCase(("aac"))) {
            imatge.setImageResource(R.drawable.aac);

        } else if (extencionFile.equalsIgnoreCase(("docx"))) {

            imatge.setImageResource(R.drawable.docx);
        } else if (extencionFile.equalsIgnoreCase(("odt"))) {

            imatge.setImageResource(R.drawable.odt);
        } else if (extencionFile.equalsIgnoreCase(("m4a"))) {

            imatge.setImageResource(R.drawable.m4a);
        } else if (extencionFile.equalsIgnoreCase(("jar"))) {

            imatge.setImageResource(R.drawable.jar);
        } else if (extencionFile.equalsIgnoreCase(("jad"))) {

            imatge.setImageResource(R.drawable.jad);
        } else if (extencionFile.equalsIgnoreCase(("vcf"))) {

            imatge.setImageResource(R.drawable.vcf);
        } else if (extencionFile.equalsIgnoreCase(("mp4"))) {

            imatge.setImageResource(R.drawable.mp4);
        } else if (extencionFile.equalsIgnoreCase(("jpeg"))) {

            imatge.setImageResource(R.drawable.jpeg);
        } else if (extencionFile.equalsIgnoreCase(("opus"))) {

            imatge.setImageResource(R.drawable.opus);
        } else {
            imatge.setImageResource(R.drawable.unknown);
        }
    }
}
