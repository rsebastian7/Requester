package requester.rohan.com.requester;

import android.graphics.Bitmap;

/**
 * Created by Rohan on 11/22/2015.
 */
public interface OnDownloadListener {
    void onResponseDownloaded(String response);
    void onImageDownloaded(Bitmap bmp);
}
