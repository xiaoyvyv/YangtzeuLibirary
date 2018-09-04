package net.custom.X5Web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.DownloadListener;

import java.net.URLDecoder;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.DownloadUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.Utils_Tool;

/**
 * Created by Administrator on 2018/4/10.
 *
 * @author 王怀玉
 * @explain X5DownloadListener
 */

public class X5DownloadListener implements DownloadListener {
    private final ProgressDialog progressDialog;
    private Context context;
    private Utils_Tool UtilsTool;
    private String FilePath;

    public X5DownloadListener(Context context) {
        this.context = context;
        UtilsTool = new Utils_Tool(context);

        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("文件下载中");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCanceledOnTouchOutside(false);
    }
    private String fileName;
    @SuppressLint("SetTextI18n")
    @Override
    public void onDownloadStart(final String s, String s1, final String s2, String s3, long l) {
        // s 下载地址,s1 UA ,s2 文件名字 l文件大小
        String FileName = s2.substring(s2.lastIndexOf("=") + 1, s2.length());
        try {
            fileName = URLDecoder.decode(FileName, "utf-8");
        } catch (Exception ignored) {}

        if (fileName.isEmpty()) {
            fileName = s.substring(s.lastIndexOf("/") + 1, s.length());
        }
        if (fileName.contains("?")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("?"));
        }
        @SuppressLint("InflateParams")
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_web_down, null);
        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.style_dialog)
                .setView(view).create();
        dialog.show();

        TextView text = view.findViewById(R.id.text);
        TextView download = view.findViewById(R.id.download);
        text.setText("文件名：" + fileName + "\n\n大小：" + UtilsTool.FormatSize(String.valueOf(l)));

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String RandNum = UtilsTool.GetMD5(UtilsTool.GetRand(10));
                RandNum = RandNum.substring(0, 5);
                RandNum = "文件_" + RandNum + "_";
                fileName = RandNum + fileName;

                //过SystemService 以获取 DownloadManager
                String SavePath = UtilsTool.getShareStringWithDefeat("DownloadInfo", "SavePath", "A_Tool/Download/");
                UtilsTool.createSDCardDir(SavePath);

                FilePath = Environment.getExternalStorageDirectory() + "/" + SavePath + fileName;

                progressDialog.show();

                //第二个是相对参数
                DownloadUtils.get().download(s, SavePath, fileName, new DownloadUtils.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        progressDialog.dismiss();

                        AlertDialog dialog = new AlertDialog.Builder(context, R.style.style_dialog)
                                .setTitle("提示")
                                .setMessage("下载位置：\n\n" + FilePath)
                                .setNegativeButton("打开", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UtilsTool.openFile(FilePath);
                                    }
                                })
                                .create();
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                    }

                    @Override
                    public void onDownloading(int progress) {
                        progressDialog.setProgress(progress);
                        progressDialog.show();
                    }

                    @Override
                    public void onDownloadFailed(String error) {
                        progressDialog.dismiss();
                        ToastUtils.showShort(R.string.download_error);
                    }
                });
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.BottomToBottom);  //添加动画
        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }
}