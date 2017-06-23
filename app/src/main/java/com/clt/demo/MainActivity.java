package com.clt.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import com.clt.demo.util.Base64Utils;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {
    public WebView webView = null;
    public Button btn;

    //授权信息id
    private String msJurId;
    //员工id
    private String employeId;
    //印章图片
    private Bitmap bmp;
    //图片base64编码
    private String srcbase;
    public String  a = "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";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        //初始化webview
        initWebView("file:///android_asset/index.html");

        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                webView.loadUrl("javascript:changeImg("+"'"+"data:image/png;base64,"+a+"'"+")");
//                webView.loadUrl("javascript:changeText("+"'"+"我是"+"',"+"'"+"你是"+"'"+")");

                webView.loadUrl("javascript:changeImg("+"'"+"data:image/png;base64,"+srcbase+"'"+")");
                webView.loadUrl("javascript:changeText("+"'"+msJurId+"',"+"'"+employeId+"'"+")");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        bmp = intent.getParcelableExtra("bitmap");

        if(bmp!=null){
            srcbase = Base64Utils.ESSGetBase64Encode(bitmapTobyte(bmp));
            Bundle bundle = intent.getExtras();
            msJurId = bundle.getString("arg1");
            employeId = bundle.getString("arg2");
        }

        webView.loadUrl("javascript:changeImg("+"'"+"data:image/png;base64,"+srcbase+"'"+")");
        webView.loadUrl("javascript:changeText("+"'"+msJurId+"',"+"'"+employeId+"'"+")");
    }

    private void initWebView(String url) {

        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
//        webSettings.setBuiltInZoomControls(true);
        //设置自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
            //页面加载结束后可执行
//
//            }
        });
        //提供js调用
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }
    @Override
    protected void onResume() {
        super.onResume();


//        webView.loadUrl(args.toString());
//        webView.loadUrl("javascript:changeImg("+"'"+"data:image/png;base64,"+srcbase+"'"+")");

    }


    public byte[] bitmapTobyte(Bitmap bitmap){
        ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
        bitmap.recycle();//自由选择是否进行回收
        byte[] result = output.toByteArray();//转换成功了
        try {
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    class JSInterface {

        @JavascriptInterface
        public void changeImg(){
//
        }
        //供H5页面调用
        @JavascriptInterface
        public void cellSeal(){
            Intent intent=new Intent("Demo1");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            ComponentName cn=new ComponentName("com.clt.execute","com.clt.execute.callSealActivity");
            intent.setComponent(cn);
            startActivity(intent);
        }



    }
}
