package util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisHelp {
    public static void main(String[] args) {
        RedisHelp redisHelp=new RedisHelp();
        redisHelp.SetData("name","哆啦A梦");
        System.out.println(redisHelp.Getdata("name"));
    }

    //将数据写入到数据库
    public void SetData(String key,String Value)
    {
        Jedis jedis=new Jedis("127.0.0.1");
        jedis.auth("123456");
        jedis.setex(key,60*60*2,Value);
    }

    //将数据库的数据读出来
    public String Getdata(String key)
    {
        Jedis jedis=new Jedis("127.0.0.1");
        jedis.auth("123456");
        return jedis.get(key);
    }
}
