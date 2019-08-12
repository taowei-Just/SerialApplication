package com.tao.utilslib.net;







import java.util.HashMap;
import java.util.Map;
import java.util.Set;




public class HttpUrl {

    static class TextUtils{
        public  static  boolean isEmpty(String string){
            return  string!=null && string.length()>0 &&string.toLowerCase().equals("null");
        }
    }

    private static Map<String, String> pathMap = new HashMap<>();
    private static Map<String, String> hostMap = new HashMap<>();
    private static Map<String, String> urlMap = new HashMap<>();

    public static void init( Map<String, String> hostMap,Map<String, String> pathMap, Map<String, String> urlMap) {
        initPath(pathMap);
        initHost(hostMap);
        initUrl(urlMap);
    }
   public static void init( Map<String, String> hostMap,Map<String, String> pathMap ) {
        initPath(pathMap);
        initHost(hostMap);
    }

    public static void initUrl(Map<String, String> map) {
        Set<String> Urls = map.keySet();
        for (String key : Urls) {
            if (TextUtils.isEmpty(map.get(key)))
                continue;
            if (urlMap.containsKey(key)) {
                if (!urlMap.get(key).equals(map.get(key))) {
                    urlMap.remove(key);
                    urlMap.put(key, map.get(key));
                }
            } else {
                urlMap.put(key, map.get(key));
            }
        }

    }

    public static void initHost(Map<String, String> map) {
        Set<String> Hosts = map.keySet();
        for (String key : Hosts) {
            if (TextUtils.isEmpty(map.get(key)))
                continue;
            if (hostMap.containsKey(key)) {
                if (!hostMap.get(key).equals(map.get(key))) {
                    hostMap.remove(key);
                    hostMap.put(key, map.get(key));
                }
            } else {
                hostMap.put(key, map.get(key));
            }
        }
    }

    public static void initPath(Map<String, String> map) {
        Set<String> strings = map.keySet();
        for (String key : strings) {
            if (TextUtils.isEmpty(map.get(key)))
                continue;
            if (pathMap.containsKey(key)) {
                if (!pathMap.get(key).equals(map.get(key))) {
                    pathMap.remove(key);
                    pathMap.put(key, map.get(key));
                }
            } else {
                pathMap.put(key, map.get(key));
            }
        }

    }

    public static String getUrl(String hostName, String pathName) throws Exception {

        if (!hostMap.containsKey(hostName)) {
            throw new Exception("Unknow Host Name Key");
        }

        if (!pathMap.containsKey(pathName)) {
            throw new Exception("Unknow path Name Key");
        }

        return hostMap.get(hostName) + pathMap.get(pathName);
    }

    public static String getCustomPathUrl(String hostName, String path) throws Exception {
        if (!hostMap.containsKey(hostName))
            throw new Exception("Unknow Host Name Key");
        return hostMap.get(hostName) + path;
    }

    public static String getCustomHostUrl(String host, String pathName) throws Exception {
        if (!pathMap.containsKey(pathName))
            throw new Exception("Unknow path Name Key");
        return host + pathMap.get(pathName);
    }

    public static String getDefoultUrl(String urlName) throws Exception {
        if (!urlMap.containsKey(urlName))
            throw new Exception("Unknow url Name Key");
        return urlMap.get(urlName);
    }

    public static void putUrl(String urlName, String host, String path) throws Exception {
        if (TextUtils.isEmpty(urlName) || TextUtils.isEmpty(host) || TextUtils.isEmpty(path))
            throw new Exception("Parame Not Use Null");

        hostMap.put(urlName, host);
        pathMap.put(urlName, path);
        urlMap.put(urlName, host + path);

    }

    public static void putHost(String hostName, String host) throws Exception {
        if (hostMap.containsKey(hostName))
            throw  new Exception("Host Name Allerdy exist");
        hostMap.put(hostName, host);
    }

    public static void putPath(String pathName, String path) throws Exception {
        if (pathMap.containsKey(pathName))
            throw  new Exception("Path Name Allerdy exist");

        pathMap.put(pathName, path);
    }

  public static void putHost(String hostName, String host , boolean repeat) {
      if (!pathMap.containsKey(hostName)||repeat )
        hostMap.put(hostName, host);
    }

    public static void putPath(String pathName, String path, boolean repeat) {
        if (!pathMap.containsKey(pathName)||repeat )
        pathMap.put(pathName, path);
    }



    public  static  void main(String [] s){

        Map<String ,String> host =new HashMap();
        Map<String ,String>  path =new HashMap();
        Map<String ,String>  url = new HashMap();

        for ( int i =1 ; i<5 ; i++ )
        {
            host.put("h"+i , "vh"+i );
            path.put("p"+i , "/vp"+i );
            url.put("u"+i , "vh"+i +"/p"+i);
        }
        init( host,path,url);
      try {
            System.err.println(getUrl("h1","p4"));
            System.err.println(getCustomHostUrl("mh","p1"));
            System.err.println(getCustomPathUrl("h3","mp"));
            System.err.println(getUrl("h3","p3"));
            System.err.println(getDefoultUrl("u2"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
