package ricardo.android_files_gallery.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Ricardo on 21/05/2017.
 */

public class Storage extends RelativeLayout {
    public Storage(Context context) {
        super(context);
        init(context, null, 0);
    }

    public Storage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public Storage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }


}
