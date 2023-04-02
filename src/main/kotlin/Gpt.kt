//import kotlin.coroutines.Continuation;
//import kotlin.coroutines.CoroutineContext;
//import kotlin.coroutines.EmptyCoroutineContext;
//import kotlin.coroutines.jvm.internal.ContinuationImpl;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public final class CoroutineExampleKt {
//    public static final void main() {
//        CoroutineExampleKt$main$1 localCoroutineExampleKt$main$1 = new CoroutineExampleKt$main$1(null);
//        ContinuationImpl continuationImpl = new ContinuationImpl(localCoroutineExampleKt$main$1, localCoroutineExampleKt$main$1);
//        continuationImpl.resumeWith(localCoroutineExampleKt$main$1.invoke(continuationImpl));
//    }
//
//    public static final int foo(@NotNull Continuation<? super Integer> paramContinuation) {
//        Object object;
//        CoroutineExampleKt$foo$1 localCoroutineExampleKt$foo$1 = new CoroutineExampleKt$foo$1(paramContinuation);
//        Object object1 = object = localCoroutineExampleKt$foo$1.result;
//        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
//            DebugProbesKt.probeCoroutineSuspended(paramContinuation);
//            return 0;
//        }
//        return 1;
//    }
//
//    public static final void bar(@NotNull Continuation<? super String> paramContinuation) {
//        Object object;
//        CoroutineExampleKt$bar$1 localCoroutineExampleKt$bar$1 = new CoroutineExampleKt$bar$1(paramContinuation);
//        Object object1 = object = localCoroutineExampleKt$bar$1.result;
//        if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
//            DebugProbesKt.probeCoroutineSuspended(paramContinuation);
//            return;
//        }
//        System.out.println((String)object);
//    }
//
//    public static final int sum(int n) {
//        int i = 0;
//        for (int j = 0; j <= n; ++j) {
//        i += j;
//    }
//        return i;
//    }
//
//    public static final class CoroutineExampleKt$main$1 extends ContinuationImpl {
//        Object L $0;
//        int label;
//        /* synthetic */ Object result;
//        final /* synthetic */ CoroutineExampleKt this$0;
//
//        @Nullable
//        public final Object invokeSuspend (@NotNull Object object) {
//        this.result =
//        object;
//        this.label |= Integer.MIN_VALUE;
//        return CoroutineExampleKt.foo((Continuation)this);
//    }
//
//        CoroutineExampleKt$main$1(CoroutineExampleKt coroutineExampleKt) {
//        this.this$0 = coroutineExampleKt;
//    }
//
//        @Nullable
//        public final Object invokeSuspend $$forInline(@NotNull Object object) {
//        this.label | = Integer.MIN_VALUE;
//        return CoroutineExampleKt.foo((Continuation)this);
//    }
//    }
//
//    public static
//    final class CoroutineExampleKt$foo$1
//    extends ContinuationImpl
//    {
//        int label;
//        /* synthetic */ Object result;
//        final /* synthetic */ Continuation
//                val $continuation;
//
//        @Nullable
//        public final Object invokeSuspend (@NotNull Object object) {
//        this.result =
//        object;
//        this.label |= Integer.MIN_VALUE;
//        return CoroutineExampleKt.bar((Continuation)this);
//    }
//        CoroutineExampleKt$foo$1(Continuation continuation) {
//        this.
//        val $continuation = continuation;
//    }
//
//        @Nullable
//        public final Object invokeSuspend $$forInline(@NotNull Object object) {
//        this.label | = Integer.MIN_VALUE;
//        return CoroutineExampleKt.bar(
//            (Continuation)this.
//            val $continuation
//        );
//    }
//    }
//
//    public static
//    final class CoroutineExampleKt$bar$1
//    extends ContinuationImpl
//    {
//        int label;
//        /* synthetic */ Object result;
//        final /* synthetic */ Continuation
//                val $continuation;
//
//        @Nullable
//        public final Object invokeSuspend (@NotNull Object object) {
//        this.result =
//        object;
//        this.label |= Integer.MIN_VALUE;
//        return CoroutineExampleKt.sum(3);
//    }
//
//        CoroutineExampleKt$bar$1(Continuation continuation) {
//        this.
//        val $continuation = continuation;
//    }
//
//        @Nullable
//        public final Object invokeSuspend $$forInline(@NotNull Object object) {
//        this.label | = Integer.MIN_VALUE;
//        return CoroutineExampleKt.sum(3);
//    }
//    }
//}
//
//
///*可以看到，反编译后的 Java 代码结构非常清晰。首先，我们定义了 `main` 方法，
//该方法创建了一个 `CoroutineExampleKt$main$1` 对象，然后创建了一个 `ContinuationImpl` 对象，
//该对象将 `CoroutineExampleKt$main$1` 对象作为 `Continuation` 参数。
//接下来，通过调用 `ContinuationImpl` 的 `resumeWith` 方法来恢复 `CoroutineExampleKt$main$1` 协程的执行。
//
//在 `foo` 和 `bar` 方法中，我们也创建了 `CoroutineExampleKt$foo$1` 和 `CoroutineExampleKt$bar$1` 对象，
//并将 `Continuation` 对象传递给这些对象。然后，我们调用 `CoroutineExampleKt.foo`
//和 `CoroutineExampleKt.bar` 方法来启动这些协程的执行。
//
//在每个 `CoroutineExampleKt$*` 类中，我们定义了一个 `invokeSuspend` 方法，该方法接收一个 `Object` 类型的参数，
//并将该参数赋值给 `result` 变量。然后，我们使用位运算设置 `label` 变量，
//以指示该协程将从何处继续执行。在这些方法中，我们调用了其他的协程方法，
//例如 `CoroutineExampleKt.bar` 和 `CoroutineExampleKt.sum`，并将 `Continuation` 对象作为参数传递。
//
//总之，Kotlin 协程的实现依赖于字节码增强和生成。在 Kotlin 中，协程代码的结构和语法与普通代码非常相似，
//但是 Kotlin 编译器在编译协程代码时会对其进行转换和优化，以便实现协程所需的挂起、恢复和调度等操作。
//在字节码级别上，协程代码被转换成一个有限状态机（finite state machine），
//其中每个状态表示协程的不同执行阶段。通过 Continuation 对象，协程可以在执行过程中暂停和恢复，并且可以跨线程运行，
//实现非阻塞的异步编程。
//
//协程的实现过程并不是简单的一步到位，涉及到许多细节和技巧。在实际应用中，我们需要深入理解 Kotlin 协程的
//工作原理和底层实现细节，以便能够更好地使用和优化协程代码。*/
//
//
//
//
//
//
