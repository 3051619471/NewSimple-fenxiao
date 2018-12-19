package com.astgo.naoxuanfeng;

/**
 * Created by Administrator on 2016/2/19.
 * 接口地址常量
 */
public class NetUrl {
    public static final int SIPIPORT = 5080;
    //有些是webview加载的网址
    /**
     * 域名
     */
   //和顺商圈
  //public static final String IP = "http://hssq.dykj168.com/";
    //脑炫风
 public static final String IP = "http://nxf.dykj168.com/";
  //public static final String IP = "http://192.168.1.117/fxsys/";
   // public static final String IP1 = "http://nxf.dykj168.com/";
/**
 * 获取首页栏目
 */
    public static  final String GET_COLUMN = IP+"index.php?m=Home&c=index&a=module";
    /**
     * 登录接口
     */
    public static final String LOGIN = IP + "index.php?m=Home&c=user&a=login";
    /**
     * 发送验证码
     */
    public static final String CODE = IP + "index.php?m=Home&c=user&a=getcode";
    /**
     * 注册
     */
    public static final String REGISTER = IP + "index.php?m=Home&c=user&a=register";
    /**
     * 忘记密码
     */
    public static final String FORGET_PASSWORD = IP + "index.php?m=Home&c=user&a=forgetpass";
    /**
     * 获取版本信息
     */
    public static final String GET_VERSION_INFO = IP + "index.php?m=Home&c=user&a=get_version";
    /**
     * 商品列表
     * p			=> 页数
     * category_id	=> 分类
     * keywords	    => 搜索关键词
     * gtype		=> 商品类别：5淘宝商品2自有商品1档位商品3全返商品4积分商品
     * otype		=> 排序：comprehensive 综合排序，volume销量，newgoods 新品，price_desc价格降序，price_asc 价格升序
     */
    public static final String GET_SHOP_LIST = IP + "index.php?m=Home&c=index&a=goods";
    /**
     * 商品分类
     */
    public static final String SHOP_CATEGRORY = IP + "index.php?m=Home&c=index&a=category";
    /**
     * shop轮播图
     */
    public static final String BANNER_SHOP = IP + "index.php?m=Home&c=index&a=swiper";
    //SHOP分类
    public static final String BANNER_CLASS = IP + "index.php?s=/Home/study/studyPic&id=12";

    /**
     * video轮播图
     */
    public static final String BANNER_VIDEO = IP + "index.php?m=Home&c=api&a=carouselimg";
    /**
     * 视频底部广告图
     */
    public static final String VIDEO_BOTTOM_AD = IP + "index.php?m=Home&c=api&a=bottomadv";
    /**
     * 视频中间的公告
     */
    public static final String VIDEO_NOTICE = IP + "index.php?m=Home&c=api&a=notice";
    /**
     * 视频播放前显示的图片
     */
    public static final String VIDEO_PLAY_IMG = IP + "index.php?m=Home&c=api&a=startadv";
    /**
     *获取解析地址
     */
    public static final String VIDEO_analysis_ADDRESSS = IP + "index.php?m=Home&c=api&a=getanalysisurl";

    /**
     * 是否更换设备
     */
    public static final String SWITCH_DEVICE = IP + "index.php?m=Home&c=index&a=preload";








    //webviewURL
    /**
     * 我的public static final String MINE = "http://fenxiao.7oks.net/index.php?m=Home&c=user&a=center"
     */
    public static final String MINE = IP + "index.php?m=Home&c=user&a=center";
    /**
     * 搜索界面
     */
    public static final String SHOP_SEARCH = IP+"index.php?m=Home&c=index&a=search";
    /**
     * 订单界面
     */
    public static final String SHOP_ORDER = IP+"index.php?m=Home&c=order&a=index";
    /**
     *我的收益
     */
    public static final String MINE_INCOME = IP+"index.php?m=Home&c=user&a=awardrecord";
    /**
     *我的团队
     */
    public static final String MINE_TEAM = IP+"index.php?m=Home&c=user&a=team";
    /**
     *我的分享
     */
    public static final String MINE_SHARE = IP+"index.php?m=Home&c=user&a=share";
    /**
     *档位商品
     */
    public static final String MINE_SHOP = IP+"index.php?m=Home&c=index&a=lvgoods";
    /**
     *我要提现
     */
    public static final String MINE_WITHDRAW= IP+"index.php?m=Home&c=withdraw&a=index";
    /**
     *转出注册币
     */
    public static final String MINE_YCZ= IP+"index.php?m=Home&c=user&a=transreg";
    /**
     *查看奖金池
     */
    public static final String MINE_MONEY_POOL= IP+"index.php?m=Home&c=user&a=bonus_pool";
    /**
     *收货地址
     */
    public static final String MINE_ADDRESS= IP+"index.php?m=Home&c=shop&a=address";
    /**
     *个人设置
     */
    public static final String MINE_SETTING= IP+"index.php?m=Home&c=user&a=setting";
//    /**
//     *个人设置
//     */
//    public static final String MINE_UPDATE= IP+"index.php?m=Home&c=user&a=setting";
    /**
     *我的挪车吗
     */
    public static final String MINE_CAR_CORD= IP+"index.php?m=Home&c=user&a=simicard";
    /**
     * 档位商品http://hssq.dykj168.com/index.php?m=home&c=index&a=lvgoods
     */
    public static final String HOME_SHOP_ONE= IP+"index.php?m=home&c=index&a=lvgoods";
}
