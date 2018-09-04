package cn.edu.yangtzeu.calis.yangtzeulibirary.network;

public interface OnResultListener {
    void onResponse(String response);
    void onFailure(String error);
}
