# spring boot redis #
当在pom.xml文件中加入依赖以后，spring boot默认的`org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration`类会自动读取`application.properties`文件中关于redis的配置。
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

这种方式会提供以下两个bean：
- RedisTemplate<Object, Object> ：它可以对Redis中key和value都为Object类型的数据进行操作，默认会将对象使用`JdkSerializationRedisSerializer`进行序列化； 
- StringRedisTemplate ：它可以对Redis中key和value都是String类型的数据进行操作。