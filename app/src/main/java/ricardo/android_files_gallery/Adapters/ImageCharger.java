package ricardo.android_files_gallery.Adapters;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ricardo on 23/05/2017.
 */

public class ImageCharger {
    final String MEDIA_PATH = Environment
            .getExternalStorageDirectory()
            .getPath() + "/Pictures/";
    private ArrayList<String> imageList = new ArrayList<String>();
    private String jpgPattern = ".jpg";
    private String pngPattern = ".png";

    //    Constructor
    public ImageCharger() {

    }

    public ArrayList<String> getPlayList() {
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    Log.d("PATH", file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addImageToList(file);
                    }
                }
            }
        }

        return imageList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addImageToList(file);
                    }
                }
            }
        }
    }

    private void addImageToList(File file) {
        if (file.getName().endsWith(jpgPattern) || file.getName().endsWith(pngPattern)) {
            imageList.add(file.getPath());
        }
    }
}
