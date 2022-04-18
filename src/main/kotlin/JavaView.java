

public class JavaView {

    // 成员变量
    private OnClickListener mOnClickListener;

    // 监听手指点击事件
    public void setOnClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    // 为传递这个点击事件，专门定义了一个接口
    public interface OnClickListener {
        void onClick(JavaView v);
    }

    public void click() {
        System.out.println("JavaView On Click");
        if (null != mOnClickListener) {
            mOnClickListener.onClick(this);
        }
    }
}
