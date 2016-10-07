package com.example.tourmate.managers;

import android.content.Context;
import android.util.Log;

import com.example.tourmate.dto.Tourist;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by IronFactory on 2016. 10. 7..
 */
public class TourAPIManager {

    private static final String TAG = "TourAPIManager";

    private static final String APP_NAME = "TravelMate";

    private static final int MAX_ROW_NUM = 999;

    private static final String API_KEY = "Lxbm1ybyU8N5PDZs85%2Fq7lPkVo9xuf2eienU0jAfV2YFMTZBElEvProHiKucWYZ5sS4R1fAA1nolBb2u7ttxcg%3D%3D";

    private static final String API_COMMON_URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon";

    private static final String API_KEYWORD_SEARCH_URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword";

    private String elementName;

    private List<Tourist> touristList;

    private Context context;

    public TourAPIHandler handler;


    public TourAPIManager(Context context) {
        this.context = context;
        handler = (TourAPIHandler) context;
    }

    public void querySearchByKeyword(String keyword) {
        OkHttpClient client = new OkHttpClient();
        StringBuilder sb = new StringBuilder();
        sb.append(API_KEYWORD_SEARCH_URL)
                .append("?").append("ServiceKey=" + API_KEY)
                .append("&").append("numOfRows=" + MAX_ROW_NUM)
                .append("&").append("pageNo=" + 1)
                .append("&").append("keyword=" + keyword)
                .append("&").append("MobileOS=" + "ETC")
                .append("&").append("MobileApp=" + APP_NAME);

        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "실패");
                handler.searchByKeywordFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "성공");
                try {
                    String res = response.body().string();
                    InputStream is = new ByteArrayInputStream(res.getBytes());

                    Map<String, String> map = new HashMap<String, String>();
                    touristList = new ArrayList<Tourist>();

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new InputStreamReader(is, "UTF-8"));

                    parser.next();
                    int eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String tag = parser.getName();
                            parser.next();
                            String value = parser.getText();
                            if (value != null) {
                                map.put(tag, value);
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            String tag = parser.getName();
                            if (tag.equals("item")) {
                                Tourist tourist = new Tourist(context, map);
                                touristList.add(tourist);
                                map.clear();
                            }
                        }
                        eventType = parser.next();
                    }

                    handler.searchByKeyword(touristList);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.searchByKeywordFailed();
                }
            }
        });
    }

    public void querySearchById(final Tourist tourist) {
        OkHttpClient client = new OkHttpClient();

        StringBuilder sb = new StringBuilder();
        sb.append(API_COMMON_URL)
                .append("?").append("ServiceKey=" + API_KEY)
                .append("&").append("contentId=" + tourist.getContentId())
                .append("&").append("contentTypeId=" + tourist.getContentTypeId())
                .append("&").append("defaultYN=N")
                .append("&").append("mapImageYN=N")
                .append("&").append("firstImageYN=Y")
                .append("&").append("areacodeYN=N")
                .append("&").append("catcodeYN=Y")
                .append("&").append("addrinfoYN=Y")
                .append("&").append("mapinfoYN=Y")
                .append("&").append("overviewYN=N")
                .append("&").append("transGuideYN=N")
                .append("&").append("MobileOS=ETC")
                .append("&").append("MobileApp=" + APP_NAME)
                .append("&").append("numOfRows=" + MAX_ROW_NUM)
                .append("&").append("pageNo=" + 1);

        Request request = new Request.Builder()
                .url(sb.toString())
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "실패");
                handler.searchByIdFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG, "성공" + res);

                try {
                    InputStream is = new ByteArrayInputStream(res.getBytes());

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new InputStreamReader(is, "UTF-8"));

                    parser.next();
                    int eventType = parser.getEventType();
                    Map<String, String> map = new HashMap<>();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String tag = parser.getName();
                            parser.next();
                            String value = parser.getText();
                            if (value != null) {
                                map.put(tag, value);
                            }
                        }

                        eventType = parser.next();
                    }
                    handler.searchById(new Tourist(context, map));
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.searchByIdFailed();
                }
            }
        });
    }
}
