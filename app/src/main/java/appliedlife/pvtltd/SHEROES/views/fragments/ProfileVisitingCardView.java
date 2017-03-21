package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 14/03/17.
 */

public class ProfileVisitingCardView extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(ProfileVisitingCardView.class);
    private final String SCREEN_NAME = "Profile_Visiting_card_screen";
    String dest_file_path = "visiting_card.pdf";
    int downloadedSize = 0, totalsize;
    String download_file_url = "http://www.princexml.com/howcome/2016/samples/magic6/magic.pdf";
    float per = 0;
    @Bind(R.id.tv_download_visiting_card)
    TextView mTv_download_visiting_card;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_profile_visiting_card, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    void downloadAndOpenPDF() {
        new Thread(new Runnable() {
            public void run() {
                Uri path = Uri.fromFile(downloadFile(download_file_url));
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                } catch (ActivityNotFoundException e) {


                    //mTv_loading.setError("PDF Reader application is not installed in your device");
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
            //setText("Starting PDF download...");

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
              /*  setText("Total PDF File size  : "
                        + (totalsize / 1024)
                        + " KB\n\nDownloading PDF " + (int) per
                        + "% complete");*/
            }
            // close the output stream when complete //
            fileOutput.close();
           // setText("Download Complete. Open PDF Application installed in the device.");

        } catch (final MalformedURLException e) {
           /* setTextError("Some error occured. Press back and try again.",
                    Color.RED);*/
        } catch (final IOException e) {

            /*setTextError("Some error occured. Press back and try again.",
                    Color.RED);*/
        } catch (final Exception e) {
          /*  setTextError(
                    "Failed to download image. Please check your internet connection.",
                    Color.RED);*/
        }
        return file;
    }

    @OnClick(R.id.tv_download_visiting_card)
    protected  void  ClickOnTexview()
    {

      downloadAndOpenPDF();
      /*Intent myIntent = new Intent(getActivity(), VisitingCardActivity.class);
      startActivity(myIntent);*/

    }

}
