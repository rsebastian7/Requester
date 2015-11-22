package requester.rohan.com.requester;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnDownloadListener {

    private TextView mTvResponse;
    private ImageView mImgVwResponse;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupControls();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (isConnected()){
                    startDownload();
                } else {
                    turnOnErrorMessage();
                }
            }
        });
    }

    public void startDownload(){
        String strUrl = "http://www.google.com";
        String imgUrl = "http://icons.iconarchive.com/icons/position-relative/social-1/128/google-icon.png";
        DownloadTask dt = new DownloadTask(this);
        dt.execute(imgUrl); // strUrl
    }

    @Override
    public void onResponseDownloaded(String response) {
        mTvResponse.setText(response);
    }

    @Override
    public void onImageDownloaded(Bitmap bmp) {
        mImgVwResponse.setImageBitmap(bmp);
    }

    private void setupControls(){
        mTvResponse = (TextView) findViewById(R.id.MainActivity_tvResponse);
        mImgVwResponse = (ImageView) findViewById(R.id.MainActivity_imgVwResponse);

    }

    private void turnOnErrorMessage(){
        mTvResponse.setText(R.string.connect_error_msg);
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()){
            // Connected to network, perform operations;
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
