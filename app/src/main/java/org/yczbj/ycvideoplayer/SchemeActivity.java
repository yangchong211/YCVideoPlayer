package org.yczbj.ycvideoplayer;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.yczbj.ycvideoplayer.list.TestListActivity;
import org.yczbj.ycvideoplayer.tiny.TestFullActivity;
import org.yczbj.ycvideoplayerlib.tool.utils.VideoLogUtils;

import java.util.List;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/11/29
 *     desc  : scheme协议页面
 *     revise: AppLink
 * </pre>
 */
public class SchemeActivity extends AppCompatActivity {

    /**
     * URL Scheme使用场景，目前1，2，5使用场景很广
     * 1.通过小程序，利用Scheme协议打开原生app
     * 2.H5页面点击锚点，根据锚点具体跳转路径APP端跳转具体的页面
     * 3.APP端收到服务器端下发的PUSH通知栏消息，根据消息的点击跳转路径跳转相关页面
     * 4.APP根据URL跳转到另外一个APP指定页面
     * 5.通过短信息中的url打开原生app
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getDataFromBrower();
        } catch (Exception e){
            e.printStackTrace();
            Intent intent = new Intent(this, MainActivity.class);
            readGoActivity(intent, this);
        }
        finish();
    }

    private void getDataFromBrower() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            VideoLogUtils.i("SchemeActivity---" + "url: " + uri);
            // scheme部分
            String scheme = uri.getScheme();
            VideoLogUtils.i("SchemeActivity---" + "scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            VideoLogUtils.i("SchemeActivity---" + "host: " + host);
            //port部分
            int port = uri.getPort();
            VideoLogUtils.i("SchemeActivity---" + "host: " + port);
            // 访问路劲
            String path = uri.getPath();
            VideoLogUtils.i("SchemeActivity---" + "path: " + path);
            // 获取参数
            List<String> pathSegments = uri.getPathSegments();
            VideoLogUtils.i("SchemeActivity---" + "pathSegments: " + pathSegments.size());
            // Query部分
            String query = uri.getQuery();
            VideoLogUtils.i("SchemeActivity---" + "query: " + query);
            //获取指定参数值
            String page = uri.getQueryParameter("page");
            VideoLogUtils.i("SchemeActivity---" + "page: " + page);

            //获取指定参数值，该方法获取值一直是空
            //String level = uri.getQueryParameter("level");
            ///Log.e( "UrlUtils","level: " + level);

            //String level = getValueByName(url, "level");
            //LoggerUtils.i( "SchemeActivity---","level: " + level);
            if (page==null || page.length()==0) {
                finish();
                return;
            }
            switch (page) {
                case "main":
                    //唤起客户端，进入首页
                    //https://yc.com?page=main
                    Intent intent1 = new Intent(this, MainActivity.class);
                    readGoActivity(intent1, this);
                    break;
                case "full":
                    //唤起客户端，进入A页面
                    //https://yc.com?page=full
                    Intent intent2 = new Intent(this, TestFullActivity.class);
                    readGoActivity(intent2, this);
                    break;
                case "list":
                    //唤起客户端，进入B页面，携带参数
                    //https://yc.com?page=list&id=520
                    Intent intent3 = new Intent(this, TestListActivity.class);
                    String id = getValueByName(url, "id");
                    intent3.putExtra("id",id);
                    readGoActivity(intent3, this);
                    break;
                case "small":
                    //唤起客户端，进入C页面
                    Intent intent4 = new Intent(this, TestRecyclerActivity.class);
                    readGoActivity(intent4, this);
                    break;
                default:
                    Intent intent = new Intent(this, MainActivity.class);
                    readGoActivity(intent, this);
                    break;
            }
        }
    }


    /***
     * 获取url 指定name的value;
     * @param url                       url
     * @param name                      参数名
     * @return 获取某个参数值
     */
    private String getValueByName(String url, String name) {
        String result = "";
        //如果不包含参数就直接返回，避免空指针异常
        if (!url.contains("?")) {
            return result;
        }
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        if (temp.contains("&")) {
            String[] keyValue = temp.split("&");
            for (String str : keyValue) {
                if (str.contains(name)) {
                    result = str.replace(name + "=", "");
                    break;
                }
            }
        }
        return result;
    }

    public void readGoActivity(Intent intent, Context context) {
        // 如果app 运行中，直接打开页面，没有运行中就先打开主界面，在打开
        if (isAppRunning(context, context.getPackageName())) {
            openActivity(intent, context);
        } else {
            //先打开首页，然后跳转指定页面
            reStartActivity(intent, context);
        }
    }

    public void openActivity(Intent intent, Context context) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 注意，为何要这样跳转，首先需要先跳转首页，然后在跳转到指定页面，那么回来的时候始终是首页Main页面
     * @param intent                            intent
     * @param context                           上下文
     */
    public void reStartActivity(Intent intent, Context context) {
        Intent[] intents = new Intent[2];
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[0] = mainIntent;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[1] = intent;
        context.startActivities(intents);
    }

    /**
     * 判断app是否正在运行
     * @param context                           上下文
     * @param packageName                       应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
