package cn.itrip.controller;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private volatile static   Test i;
    private Test(){
    }
    public Test getI(){
        if (i==null){
            synchronized (Test.class){
            if (i==null){
                i=new Test();
            }
        }
        }
        return i;
    }
    public static void main(String[] args) {


        System.out.println(i);
        List<String> list = new ArrayList();
        list.add("A");
        list.add("A");
        list.add("c");
//        list.remove("A");
//        System.out.println(list);

//        for (String str : list) {
//            if ("A".equals(str)) {
//                list.remove(str);
//            }
//            System.out.println(list);
//        }
        for (int i=0;i<list.size();i++){
            if ("A".equals(list.get(i))) {
                list.remove(list.get(i));
            }
        }
        System.out.println(list);


    }
}
