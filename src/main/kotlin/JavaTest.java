import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;

public class JavaTest {
    /*public static void main(String[] args) {
        JavaView javaView = new JavaView();
        javaView.setOnClickListener(new JavaView.OnClickListener() {
            @Override
            public void onClick(JavaView v) {
                System.out.println("After JavaView On Click");
            }
        });
        javaView.click();
    }*/



    public static void main(String[] args) throws InterruptedException {
        SuspendFromJavaExample.INSTANCE.getUserInfo(100L, new Continuation<String>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                System.out.println(o+"");
            }
        });

        // 防止程序提前结束
        Thread.sleep(2000L);
    }
}
