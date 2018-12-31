# 通过Redis的incr 和 expire 操作限制请求频次

在应用中，限制来自某一个IP或者某一设备ID的请求频率。
超过此频率就将其放入黑名单，下次请求直接拒绝服务。Java 中，可以通过Redis的incr 和 expire 操作来达到。

上述代码，可将同一个IP 的请求限制在10秒10次请求。
此逻辑靠近访问链路的签名效果越好，比如直接在Nginx 中拦截，其效果要比在业务应用中做得好。

其中，包括了Redis、UnitTest、Mock、CyclicBarrier、ExecutorService 等等。

