package com.example.miquan.upgrade;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class DownloadReceiver extends BroadcastReceiver {
    public DownloadReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppUpdateTool tool = new AppUpdateTool.Builder(context).build();
        long reference = tool.getDownloadId();
        if (intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == reference) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(reference);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            VersionInfo versionInfo = tool.getVersionInfo();
            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                tool.downloadComplete(Uri.parse("file://" + name), versionInfo);
            }
        }

    }
}
