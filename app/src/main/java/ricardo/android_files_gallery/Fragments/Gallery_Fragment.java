package ricardo.android_files_gallery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ricardo.android_files_gallery.Adapters.GalleryAdapter;
import ricardo.android_files_gallery.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Gallery_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gallery_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gallery_Fragment extends Fragment{

    // region Constants
    public static final String KEY_IMAGES = "KEY_IMAGES";
    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_TITLE = "KEY_TITLE";

    List<String> imageArray = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> images;
    private RecyclerView recyclerView;
    private String title;
    private Toolbar toolbar;


    public Gallery_Fragment() {
        // Required empty public constructor
    }

    public static Gallery_Fragment newInstance(Bundle extras) {
        Gallery_Fragment fragment = new Gallery_Fragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public static Gallery_Fragment newInstance() {
        Gallery_Fragment fragment = new Gallery_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            images = getArguments().getStringArrayList(KEY_IMAGES);
            title = getArguments().getString(KEY_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    /**
     * En este metodo cuando estemos visualizando el fragment crearemos un GridView
     * donde mostraremos todas la imagenes que tenemos en la memoria interna del
     * telefono movil.
     * Para ello pasaremos un adapter a neustra gridView.
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gallery = (GridView) getActivity().findViewById(R.id.galleryGridView);

        gallery.setAdapter(new GalleryAdapter(getActivity()));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (null != images && !images.isEmpty())
                    Toast.makeText(
                            getContext(),
                            "position " + position + " " + images.get(position),
                            Toast.LENGTH_SHORT).show();
                ;

            }
        });

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

    //Fullscreen
//    @Override
//    public void onImageClick(int position) {
//        Intent intent = new Intent(getContext(), FullScreenImageGalleryActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, images);
//        bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, position);
//        intent.putExtras(bundle);
//
//        startActivity(intent);
//
//    }

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
}
