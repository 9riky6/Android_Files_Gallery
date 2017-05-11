package ricardo.android_files_gallery.Database;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.util.Log;

import com.snappydb.DB;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

import java.lang.reflect.Field;

/**
 * Created by AMS2-22 on 11/05/2017.
 */

public class Database {
    private DB snappyDB;
    private Context context;

    public Database(Context context) {
        this.context = context;
    }

    private void openDB() throws SnappydbException {
        snappyDB = new SnappyDB.Builder(context)
                .directory(context.getFilesDir().getAbsolutePath()) //optional
                .name("Gallery")//optional
                .build();
    }

    private void closeDB() throws SnappydbException {
        snappyDB.close();
    }

    public void putString(String tag, String input) throws SnappydbException, NullPointerException {
        openDB();
        snappyDB.put(checkTag(tag), input);
        closeDB();
    }

    public void putInt(String tag, int input) throws SnappydbException, NullPointerException {
        openDB();
        snappyDB.putInt(checkTag(tag), input);
        closeDB();
    }

    public String getString(String tag) throws SnappydbException, NullPointerException {
        String r = null;
        openDB();
        r = snappyDB.get(checkTag(tag));
        closeDB();
        return r;
    }

    public Integer getInt(String tag) throws SnappydbException, NullPointerException {
        Integer r = null;
        openDB();
        Log.d("asdsfsdfsdf", (new Boolean(snappyDB == null)).toString());
        r = snappyDB.getInt(checkTag(tag));
        closeDB();
        return r;
    }

    private String checkTag(String tag) throws NullPointerException{
        String newTag = null;
        Field[] fields = DBAccess.class.getDeclaredFields();
        for (Field field : fields) {
            newTag = field.getName().equals(tag) ? tag : newTag;
        }
        if (newTag == null) {
            throw new NullPointerException("Non declared tag" + tag + " on DBAccess interface.");
        }
        return tag;
    }
}
