package com.iti.EShopper.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.iti.EShopper.R;
import com.iti.EShopper.helper.BaseActivity;
import com.iti.EShopper.helper.FilePickerUtilities;
import com.iti.EShopper.helper.PermissionUtilities;
import com.iti.EShopper.helper.WebEngine;
import com.iti.EShopper.helper.WebListener;

public class CustomUrlActivity extends BaseActivity {

    private Activity mActivity;
    private Context mContext;
    private String mPageTitle, mPageUrl;

    private WebView mWebView;
    private WebEngine mWebEngine;

    private boolean mFromPush = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_url);

        initVar();
        initView();
        initFunctionality();
    }

    private void initVar() {
        mActivity = CustomUrlActivity.this;
        mContext = mActivity.getApplicationContext();

        Intent intent = getIntent();
        if (intent != null) {
            mPageTitle = intent.getStringExtra("title");
            mPageUrl = intent.getStringExtra("url");
            mFromPush = intent.getBooleanExtra("from_push", false);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_custom_url);

        initWebEngine();

        //initLoader();
        //initToolbar(true);
        //setToolbarTitle(mPageTitle);
        //enableUpButton();
    }


    public void initWebEngine() {

        mWebView = (WebView) findViewById(R.id.web_view);

        mWebEngine = new WebEngine(mWebView, mActivity);
        mWebEngine.initWebView();


        mWebEngine.initListeners(new WebListener() {
            @Override
            public void onStart() {
                showLoading("");
            }

            @Override
            public void onLoaded() {
                hideLoading();
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onNetworkError() {
                //showEmptyView();
            }

            @Override
            public void onPageTitle(String title) {
            }
        });
    }

    private void initFunctionality() {

        mWebEngine.loadPage(mPageUrl);

        // show full-screen ads
        //AdsUtilities.getInstance(mContext).showFullScreenAd();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtilities.isPermissionResultGranted(grantResults)) {
            if (requestCode == PermissionUtilities.REQUEST_WRITE_STORAGE_UPLOAD) {
                if (mWebEngine != null) {
                    mWebEngine.invokeImagePickerActivity();
                }
            } else if (requestCode == PermissionUtilities.REQUEST_WRITE_STORAGE_DOWNLOAD) {
                if (mWebEngine != null) {
                    mWebEngine.downloadFile();
                }
            }
        } else {
            showSnackbar(getString(R.string.permission_not_granted));
        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (reqCode == WebEngine.KEY_FILE_PICKER) {
                String picturePath = FilePickerUtilities.getPickedFilePath(this, data);
                if (mWebEngine != null) {
                    mWebEngine.uploadFile(data, picturePath);
                } else {
                    showSnackbar(getString(R.string.failed));
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (mWebEngine != null) {
                mWebEngine.cancelUpload();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goToHome();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        goToHome();
    }

    private void goToHome() {
        if (mFromPush) {
            Intent intent = new Intent(CustomUrlActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }
}
