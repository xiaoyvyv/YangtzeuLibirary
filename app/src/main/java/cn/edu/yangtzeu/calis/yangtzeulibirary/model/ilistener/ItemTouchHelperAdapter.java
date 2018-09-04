package cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener;

public interface ItemTouchHelperAdapter {
    //数据交换
    void onItemChange(int fromPosition,int toPosition);
    //数据删除
    void onItemRemove(int position);
}