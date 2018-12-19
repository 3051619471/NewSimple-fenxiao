package com.astgo.naoxuanfeng.update;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;
import static com.astgo.naoxuanfeng.MainActivity.context;


/**
 * Created by Teprinciple on 2016/12/13.
 */
class DownloadAppUtils {
    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径
    private static String apkLocalPath;

    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void download(final Context context, String url, final String serverVersionName) {

        String packageName = context.getPackageName();
        String filePath = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            Log.i(TAG, "没有SD卡");
            return;
        }

        apkLocalPath = filePath + File.separator + packageName + "_" + serverVersionName + ".apk";

        downloadUpdateApkFilePath = apkLocalPath;

        FileDownloader.setup(context);
      Log.d("rzjss",url+"");
        FileDownloader.getImpl().create(url)
                .setPath(apkLocalPath)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Log.d("sss","正在下载");
                        installApk();
                        send(context, (int) (soFarBytes * 100.0 / totalBytes), serverVersionName);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.d("sss","下载完成");
                        installApk();


                        send(context, 100, serverVersionName);

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("zzzxxxx","下载出错");
                        Toast.makeText(context, "下载出错", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }


    private static void send(Context context, int progress, String serverVersionName) {
        Intent intent = new Intent("teprinciple.update");
        intent.putExtra("progress", progress);
        intent.putExtra("title", serverVersionName);
        context.sendBroadcast(intent);
    }
    public static void installApk() {
        File apkFile = new File(apkLocalPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, PACKAGE_NAME + ".provider", apkFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "applicationnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "applicationnd.android.package-archive");
        }
        context.startActivity(intent);
    }


}