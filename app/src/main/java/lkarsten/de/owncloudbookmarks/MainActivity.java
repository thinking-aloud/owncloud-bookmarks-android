package lkarsten.de.owncloudbookmarks;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;

public class MainActivity extends AppCompatActivity {

    private OwnCloudClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri serverUri = Uri.parse(getString(R.string.server_base_url));
        mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, this, true);
        mClient.setCredentials(
                OwnCloudCredentialsFactory.newBasicCredentials(
                        getString(R.string.username),
                        getString(R.string.password)
                )
        );
    }
}
