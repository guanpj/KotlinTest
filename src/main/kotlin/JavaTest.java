
public class JavaTest {
    public static void main(String[] args) {
        JavaView javaView = new JavaView();
        javaView.setOnClickListener(new JavaView.OnClickListener() {
            @Override
            public void onClick(JavaView v) {
                System.out.println("After JavaView On Click");
            }
        });
        javaView.click();
    }
}
