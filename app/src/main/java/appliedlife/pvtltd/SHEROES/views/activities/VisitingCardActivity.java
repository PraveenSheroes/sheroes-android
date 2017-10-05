package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import butterknife.Bind;

/**
 * Created by priyanka on 14/03/17.
 */
public class VisitingCardActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "Visiting Card Screen";
    @Bind(R.id.tv_download_visiting_card)
    TextView mTv_loading;
    private String dest_file_path = AppConstants.PDF_VISITING_CARD;
    int downloadedSize = 0, totalsize;
    String download_file_url = AppConstants.VISITING_CARD_URL;
    float per = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_visitingcard);
        setContentView(mTv_loading);
        mTv_loading.setGravity(Gravity.CENTER);
        mTv_loading.setTypeface(null, Typeface.BOLD);
        downloadAndOpenPDF();

    }

 void downloadAndOpenPDF() {
        new Thread(new Runnable() {
            public void run() {
                Uri path = Uri.fromFile(downloadFile(download_file_url));
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, AppConstants.PDF_PATH);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e) {
                    mTv_loading.setError(AppConstants.ERROR_MESSAGE_OF_PDF_DOWNLOAD);
                }
            }
        }).start();

    }


    File downloadFile(String dwnload_file_path) {
        File file = null;
        try {

            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            // connect
            urlConnection.connect();

            // set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            // create a new file, to save the downloaded file
            file = new File(SDCardRoot, dest_file_path);

            FileOutputStream fileOutput = new FileOutputStream(file);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength();
            setText(AppConstants.STARTING_PDF_DOWNLOAD);

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
                setText("Total PDF File size  : "
                        + (totalsize / 1024)
                        + " KB\n\nDownloading PDF " + (int) per
                        + "% complete");
            }
            // close the output stream when complete //
            fileOutput.close();
            setText(AppConstants.DOWNLOAD_COMPLETE);

        } catch (final MalformedURLException e) {
            setTextError(AppConstants.ERROR_OCCUR,
                    Color.RED);
        } catch (final IOException e) {

            setTextError(AppConstants.ERROR_OCCUR,
                    Color.RED);
        } catch (final Exception e) {
            setTextError(
                    AppConstants.DOWNLOAD_COMPLETED_IMAGE,
                    Color.RED);
        }
        return file;
    }

    void setTextError(final String message, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                mTv_loading.setTextColor(color);
                mTv_loading.setText(message);
            }
        });

    }

    void setText(final String txt) {
        runOnUiThread(new Runnable() {
            public void run() {
                mTv_loading.setText(txt);
            }
        });

    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
