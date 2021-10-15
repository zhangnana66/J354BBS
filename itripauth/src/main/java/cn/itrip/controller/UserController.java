package cn.itrip.controller;

import cn.itrip.dao.itripUser.ItripUserMapper;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    ItripUserMapper dao;
    @RequestMapping(value="/getuser",method= RequestMethod.GET,produces="application/json; charset=utf-8")
    @ResponseBody
    public Object getUser(String id) throws Exception {
        return JSONArray.toJSONString(dao.getItripUserById(new Long(12)));
    }

}



