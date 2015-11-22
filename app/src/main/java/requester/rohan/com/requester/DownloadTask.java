package requester.rohan.com.requester;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rohan on 11/22/2015.
 */
public class DownloadTask extends AsyncTask<String, Void, Object> {

    private OnDownloadListener mListener;

    private static final String TAG = DownloadTask.class.getSimpleName();

    public DownloadTask(OnDownloadListener listener) {
        super();
        mListener = listener;
    }

    public String readIt(InputStream is, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(is, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public Object downloadURL(String strUrl) throws IOException { // String
        InputStream is = null;
        // Only display first 500 characters;
        int len = 500;

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "response code: " + response);
            is = conn.getInputStream();

            //String contentAsString = readIt(is, len);
            Bitmap b = readImage(is, len);
            //return contentAsString;
            return b;
        } finally {
            if (is != null){
                is.close();
            }
        }
    }

    private Bitmap readImage(InputStream is, int len){
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
       // mListener.onResponseDownloaded(o.toString());
        mListener.onImageDownloaded((Bitmap) o);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(String... urls) { // String
        try {
            return downloadURL(urls[0]);
        } catch (IOException e){
            Log.e(TAG, "doInBackground: " + e.getMessage());
        }
        return null;
    }
}
