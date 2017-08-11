package com.clt.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.clt.demo.util.ImageUtil;
import com.clt.demo.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import static com.clt.demo.util.ImageUtil.transferAlpha;


public class MainActivity extends AppCompatActivity {
    public WebView webView = null;
    //授权信息id
    private String msJurId = "";
    //员工id
    private String employeId = "";
    //印章图片
    private Bitmap bmp;
    //图片base64编码
    private String mImgBase64;
    private int REQUESTCODE = 0x12;
    private  String data;


    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        //初始化webview
        initWebView("file:///android_asset/table.html");

        img = (ImageView) findViewById(R.id.img);
//        webView.loadUrl("javascript:changeImg("+"'"+"data:image/png;base64,"+mImgBase64+"'"+")");
//        Bitmap bm = StringUtil.stringToBitmap(mImgBase64);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.seal);
//        String aaa= StringUtil.bitmapToString(bm);



//        Bitmap bm1 = StringUtil.stringToBitmap(mImgBase64);
//        String SIGN_CODE = "1234567abcdefg";
////
////        Bitmap bm = StringUtil.stringToBitmap(seal.getImgBase64());
//
//        String code1 = SIGN_CODE.substring(0,7);
//        String code2 = SIGN_CODE.substring(7,14);
//
////                            bm = ImageUtil.drawTextToLeftTop(CallSealActivity.this,bm,code1,10, Color.RED,62,104);
//        bm1 = ImageUtil.drawTextToCenter(MainActivity.this,bm1,code1,10, Color.RED);
//
////                            bm = ImageUtil.drawTextToLeftTop(CallSealActivity.this,bm,code2,10, Color.RED,62,111);
//
//        mImgBase64 =  StringUtil.bitmapToString(transferAlpha(bm1,0,"w"));
//
////        bm = StringUtil.stringToBitmap(mImgBase64);
//        img.setImageBitmap(bm1);





//        Intent intent = new Intent("android.intent.action.VIEW");
//
//        intent.setClassName("com.clt.terminal","com.clt.terminal.CallSealActivity");
////            intent.putExtra("data","{\"SIGN_TYPE_00\":\"1\",\"SIGN_TYPE_01\":\"1\",\"SIGN_TYPE_02\":\"1\"}");
//        Bundle bundle = new Bundle();
//        bundle.putString("data","{\"SIGN_TYPE_00\":\"1\",\"SIGN_TYPE_01\":\"0\",\"SIGN_TYPE_02\":\"0\",\"SIGN_CODE\":\"1234567abcdefg\"}");
//
//        intent.putExtras(bundle);
//        startActivityForResult(intent, REQUESTCODE);




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
//        webView.getSettings().setUseWideViewPort(true);
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


    /**
     * Bitmap转byte
     * @param bitmap
     * @return
     */
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

        //供H5页面调用
        @JavascriptInterface
        public void cellSeal(){

            Intent intent = new Intent("android.intent.action.VIEW");

            intent.setClassName("com.clt.terminal","com.clt.terminal.CallSealActivity");
//            intent.putExtra("data","{\"SIGN_TYPE_00\":\"1\",\"SIGN_TYPE_01\":\"1\",\"SIGN_TYPE_02\":\"1\"}");
            Bundle bundle = new Bundle();
//            bundle.putString("data","{\"SIGN_TYPE_00\":\"1\",\"SIGN_TYPE_01\":\"0\",\"SIGN_TYPE_02\":\"0\",\"SIGN_CODE\":\"1234567\"}");
            bundle.putString("data","{\"SIGN_TYPE_00\":\"1\",\"SIGN_TYPE_01\":\"0\",\"SIGN_TYPE_02\":\"0\"}");
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUESTCODE);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent paramIntent) {
        super.onActivityResult(requestCode, resultCode, paramIntent);
        //结果码 0x13
        if (requestCode == REQUESTCODE)
        {
            //demo
            employeId = paramIntent.getStringExtra("arg1");
            msJurId = paramIntent.getStringExtra("arg2");

            //example ："{\"param2\":\"param2\",\"param1\":\"param1\"}"
            //示例格式 : {\"employeId\":\"value1\",\"msJurId\":\"value2",\"imageBase64\":\"value3"}
            data = paramIntent.getStringExtra("data");
            //js

            JSONObject jsonObj= null;
            try {
                jsonObj = new JSONObject(data);
                JSONArray js = jsonObj.getJSONArray("seals");

                mImgBase64 = js.getJSONObject(0).getString("imageBase64");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            webView.loadUrl("javascript:changeImg("+"'"+"data:image/png;base64,"+mImgBase64+"'"+")");
            webView.loadUrl("javascript:changeText("+"'"+msJurId+"',"+"'"+employeId+"'"+")");
//            jsCallback(data);
        }

    }
}
