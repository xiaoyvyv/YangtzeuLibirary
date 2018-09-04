package cn.edu.yangtzeu.calis.yangtzeulibirary.entity;

public class Urls {
    //图书馆主页
    public static final String Url_Home = "http://lib.yangtzeu.edu.cn/";
    //图书馆系统Host
    public static final String Url_Host = "http://calis.yangtzeu.edu.cn:8000";
    //图书馆系统登录地址
    public static final String Url_Login = "http://calis.yangtzeu.edu.cn:8000/opac/reader/doLogin";
    //图书馆系统修改密码
    public static final String Url_Change_Psd ="http://calis.yangtzeu.edu.cn:8000/opac/reader/updatePassword";
    //图书馆系统注销地址
    public static final String Url_Logout = "http://calis.yangtzeu.edu.cn:8000/opac/reader/logout";
    //图书馆系统主页
    public static final String Url_Index = "http://calis.yangtzeu.edu.cn:8000/opac/index";
    //图书馆系统主页通知公告
    public static final String Url_Notice = "http://lib.yangtzeu.edu.cn/List.aspx?categoryID=17";
    //图书馆系统主页馆内消息
    public static final String Url_News = "http://lib.yangtzeu.edu.cn/List.aspx?categoryID=45";
    //图书馆系统主培训页讲座
    public static final String Url_Learn = "http://lib.yangtzeu.edu.cn/List.aspx?categoryID=44";
    //图书馆系统主页新消息标签
    public static final String Url_IsNew = "http://lib.yangtzeu.edu.cn/images/images/new.gif";
    //图书馆系统书目查询
    public static final String Url_Search="http://calis.yangtzeu.edu.cn:8000/opac/search";
    //根据ISBN获取图书信息
    public static final String Url_ISBN = "http://api.interlib.com.cn/interlibopac/websearch/metares?glc=U1HBE0716024&cmdACT=getImages&type=0&isbns=";
    //图书馆系统书目二维码查询
    public static final String Url_Book_QrCode = "http://calis.yangtzeu.edu.cn:8000/opac/getQRCode?bookrecno=";
    //图书馆系统书目所在馆查询
    public static final String Url_Book_Where = "http://calis.yangtzeu.edu.cn:8000/opac/api/holding/";
    //图书馆系统书目浏览
    public static final String Url_All_Book_CLS="http://calis.yangtzeu.edu.cn:8000/opac/browse/query?category=cls&id=";
    //图书馆系统书目浏览书目大小
    public static final String Url_All_Book_Size="http://calis.yangtzeu.edu.cn:8000/opac/browse/count";
    //图书馆系统书目浏览书目详情
    public static final String Url_All_Book_List = "http://calis.yangtzeu.edu.cn:8000/opac/search?searchWay=class";
    //图书馆系统添加图书到书架
    public static final String Url_Add_My_Book = "http://calis.yangtzeu.edu.cn:8000/opac/privateCollection/add/";
    //图书馆系统移除图书到书架
    public static final String Url_Remove_My_Book = "http://calis.yangtzeu.edu.cn:8000/opac/privateCollection/delete";
    //图书馆系统书架目录
    public static final String Url_My_Book_List = "http://calis.yangtzeu.edu.cn:8000/opac/privateCollection/privateCollectionList?page=";
    //图书馆系统个人信息
    public static final String Url_Mine_Info = "http://calis.yangtzeu.edu.cn:8000/opac/reader/space";
    //图书馆系统借阅中
    public static final String Url_Current = "http://calis.yangtzeu.edu.cn:8000/opac/loan/currentLoanList?page=";
    //图书馆系统借阅历史
    public static final String Url_History = "http://calis.yangtzeu.edu.cn:8000/opac/loan/historyLoanList?page=";
    //图书馆系统催还通知
    public static final String Url_Book_Back = "http://calis.yangtzeu.edu.cn:8000/opac/publicNotice/bookOverdue";
    //图书馆系统缴费记录
    public static final String Url_PayList = "http://calis.yangtzeu.edu.cn:8000/opac/finance/financeList?page=";
    //图书馆系统图书续借查询
    public static final String Url_Book_Renew_List = "http://calis.yangtzeu.edu.cn:8000/opac/loan/renewList?page=";
    //图书馆系统图书续借
    public static final String Url_Book_Renew= "http://calis.yangtzeu.edu.cn:8000/opac/loan/doRenew";


    /*
     * 云图书馆
     */
    public static final String Url_Image_Host = "http://img.ytsg.cn/";
    public static final String Url_M_Host = "http://m.ytsg.cn/";


    //图书馆主页Banner
    public static final String Url_Home_News = "http://m.ytsg.cn/userApp/news/homeNews?locationCode=421003";

    public static final String Url_News_List = "http://m.ytsg.cn/userApp/news/newsList/region?locationCode=421003&pageCount=20&pageNo=";
    //ID查询
    public static final String Url_News_ID = Url_M_Host + "userApp/news/info?identity=faca5b18-2bce-4133-a6f6-33ace1bf2e95&fromSearch=0&newsId=";


    public static final String Url_AppDownUrl = "https://www.coolapk.com/apk/cn.edu.yangtzeu.calis.yangtzeulibirary";
    public static final String Url_UpDate = "http://m.xyll520.top/library/update/libUpDate.json";
    public static final String Url_DialogNotice = "http://m.xyll520.top/library/notice/dialogNotice.json";

    /*
     *收藏图标
     */
    public static final String Url_Web_Png = "";
    //图书馆徽章
    public static final String Url_Lib_Png = "http://lib.yangtzeu.edu.cn/images/bg/kaijuanyouyi.jpg";
    //图书默认图标
    public static final String Url_Book_Holder = "http://calis.yangtzeu.edu.cn:8000/opac/media/images/book-default-small.gif";
    //图书头像图标
    public static final String Url_Default_Header = "http://calis.yangtzeu.edu.cn:8000/opac/media/images/defaultReaderPic.jpg";


    //鸠摩搜书
    public static final String Url_JiuMo = "https://www.jiumodiary.com/";


    /**
     * 关于界面
     */
    public static final String Url_QQ = "http://p04pfl7p6.bkt.clouddn.com/App/QQ.jpg";
    public static final String Url_Wx = "http://p04pfl7p6.bkt.clouddn.com/App/WeiXin.jpg";
    //我的App列表
    public static final String Url_MyApp = "http://m.xyll520.top/MyApp.json";


    //背景音乐
    public static String Url_Music = "http://p04pfl7p6.bkt.clouddn.com/App/me-music.mp3";
}
