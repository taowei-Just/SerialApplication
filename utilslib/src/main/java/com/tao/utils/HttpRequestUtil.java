package com.tao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestUtil {
    private static OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static {
        client = new OkHttpClient();
    }

    public static OkHttpClient getClientInstance() {
        return client;
    }

    public static void postForm(String url, Map<String, String> parame, final OnReques requestCall) {
        try {
            client.newCall(new Request.Builder().post(createFormBody(parame)).url(url).build()).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {

                    requestCall.onFailed(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.code() == 200)
                        requestCall.onSuccessBody(response);
                    else
                        requestCall.onFailed(response.body().string());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            requestCall.onFailed(e.getMessage());
        }

    }


    public static void postJson(String url, String json, final OnReques onReques, boolean enqueue) {
        try {
            if (enqueue)
                client.newCall(new Request.Builder().post(createJsonForm(json)).url(url).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
 
                        onReques.onFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.code() == 200)
                            onReques.onSuccessBody(response);
                        else
                            onReques.onFailed(response.body().string());
                    }
                });
            else {
                Response response = client.newCall(new Request.Builder().post(createJsonForm(json)).url(url).build()).execute();
                if (response.code() == 200)
                    onReques.onSuccessBody(response);
                else
                    onReques.onFailed(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
            onReques.onFailed(e.getMessage());
        }
    }

    public static void get(String url, String parame, final OnReques requestCall) {
        client.newCall(new Request.Builder().get().url(url + parame).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                requestCall.onFailed(e.getMessage());
                requestCall.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                requestCall.onSuccessBody(response);
            }
        });
    }

    private static RequestBody createJsonForm(String json) {
        return RequestBody.create(JSON, json);
    }

    private static RequestBody createMultBody(Map<String, String> parema, Map<String, File> fileMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder();


        FormBody.Builder form = new FormBody.Builder();
        if (parema != null) {
            Iterator<String> iterator = parema.keySet().iterator();


            while (iterator.hasNext()) {
                String next = iterator.next();
                String value = parema.get(next);
                form.add(next, value);
            }
        }
        RequestBody body = form.build();

        if (fileMap != null) {
            Iterator<String> iterator = fileMap.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                File file = fileMap.get(next);
                builder.addFormDataPart(next, file.getName(), body);
            }
        }

        return builder.build();
    }

    private static RequestBody createFormBody(Map<String, String> parema) {
        FormBody.Builder builder = new FormBody.Builder();

        Iterator<String> iterator = parema.keySet().iterator();

        if (parema != null)
            while (iterator.hasNext()) {
                String next = iterator.next();
                String value = parema.get(next);
                builder.add(next, value);
            }

        return builder.build();
    }

    interface RequestCall<T> {

        void onFailed(Exception e);

        void onFailed(String msg);

        void onSuccessString(String str);

        void onSuccessStream(InputStream stream);

        void onSuccessJson(T t);

        void onSuccessBody(Response response) throws IOException;
    }

    public enum RequestType {
        string, stream, body, json
    }

    public static class OnReques<T extends Object> implements RequestCall<T> {
        RequestType type;
        Class<T> clazz;

        public OnReques(RequestType type) {
            this.type = type;
        }

        private OnReques(RequestType type, Class<T> clazz) {
            this.type = type;
            this.clazz = clazz;
        }

        @Override
        public void onFailed(Exception e) {

        }

        @Override
        public void onFailed(String msg) {
        }

        @Override
        public void onSuccessString(String str) {
        }

        @Override
        public void onSuccessStream(InputStream stream) {
        }

        @Override
        public void onSuccessJson(T o) {

        }

        @Override
        public void onSuccessBody(Response response) throws IOException {
            switch (type) {
                case body:
                    break;
                case stream:
                    onSuccessStream(response.body().byteStream());
                    break;
                case string:
                    onSuccessString(response.body().string());
                    break;
                case json:
                    praceJson(response.body().string());
                    break;
            }

        }

        private void praceJson(String string) {
            T o = null;
            try {
                Gson gson = new GsonBuilder().create();
                o = gson.fromJson(string, clazz);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            onSuccessJson(o);
        }


    }

    public static void main(String[] s) {


        get("https://www.baidu.com", "", new OnReques(RequestType.string) {
            @Override
            public void onSuccessString(String str) {
                System.err.println("get:" + str);
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "wx");
        map.put("rfId", "300833B2DDD9014000000000");

        postForm("http://demo-1.s1.natapp.cc/shop/pay/status", map, new OnReques(RequestType.string) {
            @Override
            public void onSuccessString(String str) {
                super.onSuccessString(str);
                System.err.println("postForm:" + str);

            }
        });

        SyncBody syncBody = new SyncBody();

        syncBody.bodies = new ArrayList<>();
        syncBody.rfId = "300833B2DDD9014000000000";

        postJson("http://demo-1.s1.natapp.cc/shop/car/syncGoods", new Gson().toJson(syncBody), new OnReques(RequestType.string) {
            @Override
            public void onSuccessString(String str) {
                super.onSuccessString(str);
                System.err.println("postJson:" + str);

            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                System.err.println("postJson" + msg);
            }
        }, true);


    }

    static class MyRequest extends OnReques<TestBean> {
        public MyRequest(RequestType type) {
            super(type);
        }

        public MyRequest(RequestType type, Class<TestBean> clazz) {
            super(type, clazz);
        }

        @Override
        public void onSuccessJson(TestBean o) {
            super.onSuccessJson(o);
            System.err.println("onSuccessJson " + o.toString());
        }

    }

    static class TestBean {

        String str = "1";

        @Override
        public String toString() {
            return "TestBean{" +
                    "str='" + str + '\'' +
                    '}';
        }
    }

    public class GoodsSyncBody {

        private long id;
        private long headId;
        private String barcode;
        private String name;
        private int num;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getHeadId() {
            return headId;
        }

        public void setHeadId(long headId) {
            this.headId = headId;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "id=" + id +
                    ", headId=" + headId +
                    ", barcode='" + barcode + '\'' +
                    ", name='" + name + '\'' +
                    ", num=" + num +
                    '}';
        }
    }


    static class SyncBody {
        public List<GoodsSyncBody> bodies;
        public String code;
        public String rfId;

        @Override
        public String toString() {
            return "SyncBody{" +
                    "syncBodies=" + bodies +
                    ", code='" + code + '\'' +
                    ", rfid='" + rfId + '\'' +
                    '}';
        }
    }

}
