package com.tao.globle;


import android.os.Environment;


public class Gloable {

    /**
     * TEST Switch
     */
    public static boolean iS_TEST = false;
 
    // 音频数据存放目录
    public static final String audioPath = Path.RootPath ==null? (Path.RootPath = Environment.getExternalStorageDirectory()+"/HytTobacco/")+"audio/":Path.RootPath+"audio/";
    // 音频数据存放目录
    public static final String videoPath =Path.RootPath + "video/";
    // 存放数据路径
    public static final String dataPath =  Path.RootPath +"data/";
    //  adv
    public static final String advPath =  Path.RootPath +"adv/"; 
    //  adv
    public static final String imageAdvPath = advPath +"image/";  
    //  adv
    public static final String videoAdvPath =  advPath+"video/";
    // 图片地址
    public static final String PicturePath  =Path.RootPath +"picture/";
    // 配置文件
    public static final String configPath = Path.RootPath +"config/";
    // 身份证头像
    public static final String headPortraits = Path.RootPath +"headPortraits/";
    // db
    public static final String DBPath =Path.RootPath + "DB/";
    // log
    public static final String logPath = Path.RootPath +"log/";
    public static final String cachePath = dataPath +"cache/";
 
    // 指令日志
    public static final String commandLog = "commandLog.txt";
    // backups
    public static final String backupsPath = Path.RootPath +"backups/";
    // tsble name goodinfo
    public static final String goodinfo = "goodinfo";
    // tsble name goodinfo
    public static final String channelflag = "channelflag";
    
    // tsble name goodinfo
    public static final String goodstock = "goodstock";
    // device code
    public static String devcode = "U00099";
    public static String deviceid = "deviceid";
    public static String devcoid_key = "devcoid_key";
    public static String IdcardKey = "IdcardKey";
    public static String autoRefundKey = "autoRefundKey";
    // 退出程序一次
    public static String currentExciteKey = "currentExciteKey";
    //  线上支付Ip
    //还阳通
    public final static  String hytHost="tobacco.sun-hyt.com";
    public final static  String payUrlPre="http://"+hytHost;


    // 微信支付 *
    public static String wechartPayURL = payUrlPre + "/wxpay/createpay";

    // 微信支付状态查询*
    public static String wechartPayStatuURL = payUrlPre + "/wxpay/status";

    // 微信关闭支付订单*
    public static String wechartPayCloseURL = payUrlPre + "/wxpay/closepay";

    // 阿里支付： Post*
    public static String alipayURL = payUrlPre + "/alipay/createpay";

    // 阿里支付状态查询 Post*
    public static String alipayStatusURL = payUrlPre + "/alipay/status";

    // 阿里支付： Post*
    public static String alipayCloseURL = payUrlPre + "/alipay/closepay";
 
    // Project Name project
    public static String project = "2";
    // 主板
    public static String mainBoardSerialPath = "/dev/ttyS1";
    public static String mainBoardPort02 = "/dev/ttyS2";
    public static String scanSerialPath = "/dev/ttyS3";
    public static String mainBoardPort_04 = "/dev/ttyS4";
    public static final int baund_115200 = 115200;
    public static final int baund_9600 = 9600;

    // 烟草表名
    public static String ycDbFileName = "yc.db";
 
    public static String tobaccoInfo = "tobaccoInfo";
    // 获取配置文件标志
    public static String isHaveconfig = "isHaveconfig";
    // 身份信息
    public static String information = "information";

    public static String acheter_marche = "acheter_marche";
    // TEST
    public final static int goodsPackageKind = 1;
    public final static int goodStripKind = 2;
    public final static int aliPayKind = 1;
    public final static int wechartPayKind = 2;
 
    public final static int orderbuy = 2;
    public static String updataPath = Path.RootPath +"updata/";

    //  保活闹钟
    public final static int daemonAlarmTag = 10001;
    // 更新升级闹钟
    public final static int updataAlarmTag = daemonAlarmTag + 1;
    // 重启闹钟
    public final static int restartAlarmTag = updataAlarmTag + 1;
    // 心跳
    public final static int heartAlarmTag = restartAlarmTag + 1;
    // 检查是否处于前台
    public final static int checkTopAlarmTag = heartAlarmTag + 1;

    // 云端人脸识别
    public  static  String faceUrl = "http://"+hytHost+":8088/rank/feature";
    public  static  String facequeryUrl = "http://"+hytHost+":8088/rank";
    
    public static String image = Path.RootPath +"image/";
    public static String backimage = Path.RootPath +"backimage/";
    public static String updataApkUrl   = "http://"+hytHost +":8078/version";
    public static int heartbeatTime =5*60;
    public static int updataAlareTime = 24*60*60;
    public static int checkTopTime = 30;
    public static String remoteAppActionStart ="HYT_DEMO_UTILS_APK_START";
    public static String MQ_TAG = "mqLog";
    public static String mqHost =hytHost;
    public static String mqPort= "1883";

    public static String updatePath = "update";

    // tsble name goodinfo
    public static String channelConfig = "channelConfig";

    // tsble name goodinfo
    public static String deviceinfo = "deviceinfo";

    // tsble name goodinfo
    public static String goodoutstock = "goodoutstock";
    // sync goods url
    public static String syncGoodsURL = "http://dc.sun-hyt.com/interface/asyngoods";
    // local goods List Url request ： Post
//	public static String GoodsListURL = "http://dc.sun-hyt.com/interface/goodlist";
    public static String GoodsListURL = "http://dc.sun-hyt.com/yancao/goods";
    // 提交购买订单 ： Post
    public static String addrecordURL = " http://dc.sun-hyt.com/interface/addrecord";
    
    // local goods List Url request ： Post
    public static String registerDeviceURL = "http://dc.sun-hyt.com/interface/register";
    // 门禁
    public static String doorGuardPort_03 = "/dev/ttyS3";// 门禁
    public static String doorGuardPort_02 = "/dev/ttyS2";
    // 主板
    public static String mainBoardPort_01 = "/dev/ttyS1";
    public static String RemotePackage="com.tao.subsidiary";
}
