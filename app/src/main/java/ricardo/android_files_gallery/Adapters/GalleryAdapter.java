package ricardo.android_files_gallery.Adapters;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Ricardo on 28/05/2017.
 */

public class GalleryAdapter extends BaseAdapter {

    /** The context. */
    private ArrayList<String> images;
    private Activity context;

    /**
     * Instantiates a new image adapter.
     *
     * @param localContext
     *            the local context
     */
    public GalleryAdapter(Activity localContext) {
        context = localContext;
        images = getAllShownImagesPath(context);
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        ImageView picturesView;
        if (convertView == null) {
            picturesView = new ImageView(context);
            picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            picturesView
                    .setLayoutParams(new GridView.LayoutParams(550, 550));

        } else {
            picturesView = (ImageView) convertView;
        }

        Glide.with(context).load(images.get(position)).into(picturesView);
//            Glide.with(context).load(images.get(position)).centerCrop()
//                    .into(picturesView);

        return picturesView;
    }

    /**
     * Getting All Images Path.
     *
     * @param activity
     *            the activity
     * @return ArrayList with images Path
     */
    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}