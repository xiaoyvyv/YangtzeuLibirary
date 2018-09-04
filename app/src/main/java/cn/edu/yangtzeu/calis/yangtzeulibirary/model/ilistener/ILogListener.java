package cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener;

public interface ILogListener {
    //成功
    void onSuccess(String info);
    //错误
    void onError(String error);
}
