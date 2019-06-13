package com.example.administrator.jx_new_jsoup.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.administrator.jx_new_jsoup.entity.News;
import com.example.administrator.jx_new_jsoup.entity.NewsDetail;
import com.example.administrator.jx_new_jsoup.entity.Prictrue;
import com.example.administrator.jx_new_jsoup.entity.ScrollImage;
import com.example.administrator.jx_new_jsoup.entity.Video;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupUtil {
    private static JsoupUtil util;
    private String url = "http://www.jiangxi.gov.cn";
    private String video = "http://jxtv.cncourt.org";

    public static JsoupUtil getInstance() {
        if (util == null) {
            util = new JsoupUtil();
        }
        return util;
    }

    public void getVideo(final Handler handler, final String weburl) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Connection con = Jsoup.connect(weburl);
                    con.timeout(5000);
                    Document doc = con.get();
                    Element script = doc.getElementsByTag("script").get(6);
                    String info = script.toString();
                    String[] texts = info.split("m4v");
                    String text = texts[1].split("\\}")[0];
                    String videourl = text.substring(2, text.length() - 1);
                    Message message = handler.obtainMessage();
                    message.what = 8;
                    message.obj = videourl;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getVideoList(final Handler handler, final String weburl) {
        final List<Video> oList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Connection con = Jsoup.connect(weburl);
                    con.timeout(5000);
                    Document doc = con.get();
                    Element div = doc.select("div.pageMain").first();
                    Elements boxs = div.select("div.vPicBox");
                    for (Element box : boxs) {
                        Element a = box.getElementsByTag("a").first();
                        String videourl = video + a.attr("href");
                        String videoimageurl = video + a.getElementsByTag(
                                "img").first().attr("src");
                        String text = a.text();
                        String[] texts = text.split("来源");
                        String name = texts[0];
                        String time = "时间" + texts[1].split("时间")[1];
                        Video video = new Video(videoimageurl, name, time, videourl);
                        oList.add(video);
                    }

                    Message message = handler.obtainMessage();
                    message.what = 7;
                    message.obj = oList;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getPrictrueList(final int what, final Handler handler, final String weburl, final int pageindex) {
        final List<Prictrue> oList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Connection con = Jsoup.connect(weburl);
                con.timeout(5000);
                try {
                    Document d = con.get();
                    Element div = d.getElementById("45663");
                    Element script = div.getElementsByTag("script").first();
                    String info = script.toString();
                    if (!TextUtils.isEmpty(info)) {
                        String[] infos = info.split("<record>");
                        for (int i = pageindex * 20; i < (pageindex + 1) * 20; i++) {
                            if (pageindex == 0 && i == 0) {
                                i = i + 1;
                            }
                            if (i < infos.length) {
                                String s = infos[i].substring(10, infos[i].length());
                                String str = s.split("]")[0];
                                Document doc = Jsoup.parse(str);
                                Element li = doc.getElementsByTag("li").first();
                                Element a = li.getElementsByTag("a").first();
                                //获取的是图片详情页面的地址，通过该地址再进行解析，获得图片地址
                                String imageurl = url + a.attr("href");
                                String name = a.text();
                                Prictrue prictrue = new Prictrue
                                        (name, "", "", imageurl);
                                oList.add(prictrue);
                            }
                        }
                    }
                    Message message = handler.obtainMessage();
                    message.what = what;
                    message.obj = oList;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getPrictrue(final Handler handler, final String weburl) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Connection con1 = Jsoup.connect(weburl);
                    con1.timeout(5000);
                    Document doc1 = null;
                    doc1 = con1.get();
                    Element imagediv = doc1.select(
                            "div.bt-article-02").first();
                    Element imagep = imagediv.getElementsByTag(
                            "p").first();
                    String name = imagep.text();
                    Element imagediv1 = imagediv.select(
                            "div.sp_time").first();
                    Element imagefont = imagediv1.getElementsByTag(
                            "font").first();
                    String time = imagefont.text();
                    Element imagediv2 = imagediv.getElementById("zoom");
                    Element a1 = imagediv2.getElementsByTag(
                            "a").first();
                    Element imageimg = null;
                    if (a1 != null) {
                        imageimg = a1.getElementsByTag(
                                "img").first();
                    } else {
                        imageimg = imagediv2.getElementsByTag(
                                "img").first();
                    }
                    String imageurl = "";
                    if (imageimg != null && imageimg.hasAttr("src")) {
                        imageurl = url + imageimg.attr("src");
                    } else {
                        imageurl = url + a1.attr("href");
                    }

                    Element imagespan = imagediv2.getElementsByTag(
                            "span").first();
                    String content = "";
                    if (imagespan != null && imagespan.hasText()) {
                        content = imagespan.text();
                    }
                    Prictrue prictrue = new Prictrue
                            (name, content, time, imageurl);
                    Message message = handler.obtainMessage();
                    message.what = 5;
                    message.obj = prictrue;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    public void getNewsDetail(final Handler handler, final String weburl) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Connection con = Jsoup.connect(weburl);
                con.timeout(5000);
                try {
                    Document doc = con.get();
                    Element div = doc.select
                            ("div.bt-article-02").first();
                    Element div1 = div.select(
                            "div.sp_time").first();
                    Element font = div1.getElementsByTag(
                            "font").first();
                    String time = font.text();
                    Element div2 = div.getElementById("zoom");
                    Elements ps = div2.getElementsByTag("p");
                    List<String> oList = new ArrayList<>();
                    for (Element item : ps) {
                        String info = item.text();
                        oList.add(info);
                    }
                    Element div3 = div2.getElementsByTag(
                            "div").first();
                    String author = div3.getElementsByTag(
                            "font").first().text();
                    NewsDetail detail = new NewsDetail
                            ("", time, author, oList);
                    Message message = handler.obtainMessage();
                    message.what = 3;
                    message.obj = detail;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getNewsList(final Handler handler, final String weburl, final String tag) {
        final List<News> oList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Connection con = Jsoup.connect(weburl);
                    con.timeout(5000);
                    Document doc = con.get();
                    Element div0 = doc.select("div.bt-tab-con").get(0);
                    Element div = div0.getElementById(tag);
                    Element ul = div.getElementsByTag("ul").first();
                    Elements lis = ul.getElementsByTag("li");
                    for (Element li : lis) {
                        Element a = li.getElementsByTag("a").first();
                        String url = JsoupUtil.this.url + a.attr("href");
                        String title = a.attr("title");
                        Element span = a.getElementsByTag("span").first();
                        String time = span.text();
                        News news = new News(title, time, url);
                        oList.add(news);
                    }
                    Message message = handler.obtainMessage();
                    message.what = 2;
                    message.obj = oList;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 抓取滑动图片信息
     */
    public void getScrollImsages(final Handler handler, final String weburl) {
        final List<ScrollImage> oList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Connection con = Jsoup.connect(weburl);
                    con.timeout(5000);
                    //获得当前网站页面的html代码
                    Document doc = con.get();
                    Elements elements = doc.select("#focus");
                    Element div = elements.first();
                    Elements lis = div
                            .getElementsByTag("ul")
                            .first().getElementsByTag("li");
                    for (Element li : lis) {
                        Element img = li
                                .getElementsByTag("a")
                                .first()
                                .getElementsByTag("img").first();
                        String imageurl = img.attr("src");
                        String title = img.attr("alt");
                        ScrollImage image = new ScrollImage(weburl + imageurl, title);
                        oList.add(image);
                    }

                    Message message = handler.obtainMessage();
                    message.what = 1;
                    message.obj = oList;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
