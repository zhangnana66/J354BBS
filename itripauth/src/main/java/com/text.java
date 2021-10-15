package com;

import com.pojo.User;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class text {
    public static void main(String[] args) throws Exception {
//       a1();
        b1();

    }
    public static  void a1()throws  Exception{
        Map map=new HashMap<>();
        List<User>list=new ArrayList<>();
        for (int i=0;i<10;i++){
            User user=new User();
            user.setName("哆啦a梦"+i);
            user.setId(i);
            list.add(user);
        }
        map.put("li",list);
        Configuration myCfg = new Configuration(Configuration.VERSION_2_3_23);
        myCfg.setDefaultEncoding("utf-8");
        myCfg.setDirectoryForTemplateLoading(new File("D:\\study\\idea\\itripbackend\\itripauth\\src\\main\\resources"));
        Template template=myCfg.getTemplate("a.flt");
        template.process(map,new FileWriter("E:\\爱旅行项目\\a.html"));
    }
    public static  void b1()throws Exception{
        Map map=new HashMap<>();
        map.put("name","哆啦A梦");
        Configuration myCfg = new Configuration(Configuration.VERSION_2_3_23);
        myCfg.setDefaultEncoding("utf-8");
        myCfg.setDirectoryForTemplateLoading(new File("D:\\study\\idea\\itripbackend\\itripauth\\src\\main\\resources"));
        Template template=myCfg.getTemplate("b.flt");
        template.process(map,new FileWriter("E:\\爱旅行项目\\b.text"));
    }
}
