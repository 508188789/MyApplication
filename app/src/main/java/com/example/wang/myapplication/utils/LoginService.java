package com.example.wang.myapplication.utils;

import android.content.Context;
import android.util.Log;

import com.example.wang.myapplication.Application;
import com.example.wang.myapplication.modle.bean.LinkNode;
import com.example.wang.myapplication.modle.bean.User;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by ${151530502} on 2018/9/22.
 */
public class LoginService {

    public static String COOK3;
    private static String COOKIE = "";
    private static String COOKIE2 = "";
    private static String COOKIE_UIA = "";
    private static String COOKIE_asp = "";
    public static String Cookie_ASP;
    public static String Cookie_casauth;
    private static List<LinkNode> LinkNodes;
    private static HttpClient client;
    private static String cookie_lib;
    private static String course_vi = "";
    private static String link_course = "";
    private static String link_score = "";
    private static String password;
    private static HttpResponse response;
    private static String str_success;
    private static String username;



    /**
     * 采用httpclient post提交数据
     * @param username
     * @param Tmm
     * @return
     */
    public static String login(String username, String Tmm){
        try{


            //1.打开一个浏览器
            HttpClient client = new DefaultHttpClient();
            //2.输入地址
            //提取的单独的登陆界面
            String path = "http://jiaowu.pdsu.edu.cn/default.aspx";
           HttpResponse response = client.execute(new HttpPost(path));
          //  COOKIE=((AbstractHttpClient)client).getCookieStore().getCookies().get(0).getValue();
            Log.d("如果到这里，输出密文",Tmm);
            //读cookie
            CookieStore cookieStore = ((AbstractHttpClient)client).getCookieStore();
            List<Cookie> cookie = cookieStore.getCookies();
            if (cookie.isEmpty()){
                Log.d("cookie为空","----Cookie None----");
            }else {
                for (int i = 0;i<cookie.size();i++){

                    COOKIE = cookie.get(i).getValue();
                    Log.d("cookie为",cookie.get(i).getName()+ "=" +COOKIE);
                }
            }

           // AbstractHttpClient abstractHttpClient = null ;
          // abstractHttpClient.getCookieStore().getCookies().get(0).getValue();
            //指定要提交的数据实体
           // UrlEncodedFormEntity entity = null;
           // response.getEntity();
           // UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity();
            //entity.getContent();

           // Cookie cookie = null;
           // List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            //CookieStore cookieStore = null;
            //List<Cookie> cookies = cookieStore.getCookies();
          //  COOKIE = cookies.get(0).getValue();
           // COOKIE = abstractHttpClient.getCookieStore().getCookies().get(0).getValue();


           // HttpResponse httpResponse = client.execute(httpPost);
            //回车



            String a = StreamTools.readInputStream(response.getEntity().getContent());
            Log.d("长度",a.length()+"");
           // String b = a.substring(10000,a.length()-900).split("name=\"lt\" value=\"")[1].split("\"")[0];
            Log.d("回车COOK",COOKIE);
          //  Log.d("it",b);



            String url = "http://jiaowu.pdsu.edu.cn/default.aspx" + COOKIE + "http://jiaowu.pdsu.edu.cn/_data/login.aspx";

            HttpPost httpPost1 = new HttpPost(url);

            List<NameValuePair> list = new ArrayList<>();
            //ArrayList list = new ArrayList();
            list.add(new BasicNameValuePair("__VIEWSTATE","/wEPDwULLTE4ODAwNjU4NjBkZOixLCKq9hTIPtbKRaNKav6GDqHz0xHMnoiA6p6Tz812"));
            list.add(new BasicNameValuePair("__EVENTVALIDATION","/wEdAAIG6aRL4uLheMU1mdE7CkCZZ5IuKWa4Qm28BhxLxh2oFCeKLs8XYiEBTKrAYA3gIJ7xJkbwaGXicTMwuw7n38My"));
            list.add(new BasicNameValuePair("Sel_Type","STU"));
            list.add(new BasicNameValuePair("username",username));
            list.add(new BasicNameValuePair("password",Tmm));
            Log.d("TTT",Tmm);
            list.add(new BasicNameValuePair("submit", URLEncoder.encode("登 录","UTF-8")));
            //list.add(new BasicNameValuePair("_eventId","submit"));
            //list.add(new BasicNameValuePair("It",b));

            String c = "JSESSIONID="+COOKIE;
            httpPost1.setHeader("Cookie",c);
            httpPost1.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));


            client.getParams().setParameter("http.protocol.handle-redirects",Boolean.valueOf(false));
          // HttpUriRequest httpUriRequest = null;
           response = client.execute(httpPost1);

            int statusCode = response.getStatusLine().getStatusCode();
            Log.d("状态码",statusCode+"");
            if (statusCode == 302){
                //读cookie
                CookieStore cookieStore1 = ((AbstractHttpClient)client).getCookieStore();
                List<Cookie> cookie1 = cookieStore1.getCookies();
                if (cookie1.isEmpty()){
                    Log.d("cookie为空","----Cookie None----");
                }else {
                    for (int i = 0;i<cookie1.size();i++){

                       // COOKIE = cookie.get(i).getValue();
                        //Log.d("cookie为",cookie.get(i).getName()+ "=" +COOKIE);
                        COOKIE_UIA = cookie1.get(i).getValue();
                        Log.d("得到COOKIE_UIA", COOKIE_UIA);
                    }
                }
                return "success";

            }if (statusCode == 200){
                return "error";

            }
            return null;

        }catch (Exception e){
            Log.d("exception", "登录出错");
            e.printStackTrace();
        }
        return null;
    }

    public static void getstudent(Context context,String username, String password){
        try{



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void getcookie2(){

    }

    public static void loginbmob(String username, String password){
        BmobUser.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user!=null){
                    Log.i("smile", "用户登陆成功");
                    Application.setuser(BmobUser.getCurrentUser(User.class));
                    return;
                }
                 Log.i("smile", "用户登陆失败");
            }
        });

    }

    public static boolean logincourse(Context paramContext){
        return true;
    }

    public static boolean loginzfxt(Context paramContext){
        return true;
    }


   /* public static void updatastudent(Context context, String s){

    }*/


}
