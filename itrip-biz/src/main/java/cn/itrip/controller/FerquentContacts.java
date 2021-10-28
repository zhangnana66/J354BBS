package cn.itrip.controller;

import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.dao.itripUserLinkUser.ItripUserLinkUserMapper;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripLabelDic;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserLinkUser;
import common.Dto;
import common.DtoUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api")
public class FerquentContacts {
    @Resource
    ItripUserLinkUserMapper linkUser;
    //查询常用用户的方法
    @RequestMapping(value="/userinfo/queryuserlinkuser",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Dto getpersonalorderlist(ItripUser user)throws Exception{
        user.setId(new Long(29));
       List<ItripUser> list=linkUser.getUserList(user);
        return  DtoUtil.returnDataSuccess(list);
    }
    //删除的方法
    @RequestMapping(value = "/userinfo/deluserlinkuser",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto deleteUser(int ids)throws  Exception{
        int num=linkUser.deleteItripUserLinkUserById(new Long(ids));
        return DtoUtil.returnDataSuccess(num);
    }
    //修改的方法
    @RequestMapping(value = "/userinfo/modifyuserlinkuser",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto Modifyuserlinkuser(ItripUserLinkUser userLinkUser)throws  Exception{
        int row=linkUser.updateUser(userLinkUser);
        return  DtoUtil.returnDataSuccess(row);
    }
    @Resource
    ItripAreaDicMapper areaDicMapper;
    //主页酒店推荐（国内和国外）
    @RequestMapping(value = "/hotel/queryhotcity/{type}",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto queryHotcity(@PathVariable("type") String Type)throws Exception{
        List<ItripAreaDic>list=areaDicMapper.RecommendedHotel(Type);
        return  DtoUtil.returnDataSuccess(list);
    }
    //主页特殊酒店设施推荐
    @Resource
    ItripLabelDicMapper labelDicMapper;
    @RequestMapping(value = "/hotel/queryhotelfeature",produces ="application/json;charset=UTF-8")
    @ResponseBody
    public Dto queryhotelfeature()throws Exception{
        List<ItripLabelDic>list=labelDicMapper.RecommendFacilities();
        return DtoUtil.returnDataSuccess(list);
    }
}
