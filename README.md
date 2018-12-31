# RedisIncrExpire
在应用中，限制来自某一个IP或者某一设备ID的请求频率。  超过此频率就将其放入黑名单，下次请求直接拒绝服务。 Java 中，可以通过Redis的incr 和 expire 操作来达到。
