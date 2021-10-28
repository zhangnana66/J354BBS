package cn.itrip.controller;

import cn.itrip.biz.BizToken;
import cn.itrip.biz.CloopenBiz;
import cn.itrip.biz.MailSenderInfoBiz;
import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserVO;
import com.alibaba.fastjson.JSONArray;
import common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.RedisHelp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
public class UserController {
    @Resource
    ItripUserMapper dao;
    @Resource
    BizToken token;
    @Resource
    RedisHelp help;

    @RequestMapping(value = "/getuser", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object getUser(String id) throws Exception {
        return JSONArray.toJSONString(dao.getItripUserById(new Long(12)));
    }

    //登录的方法
    @RequestMapping(value = "/dologin", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto DoLogin(String name, String password, HttpServletRequest request) throws Exception {
        String md5 = MD5.getMd5(password, 32);
        ItripUser login = dao.getLogin(name, md5);
        //tockn的使用
        //tockn包含客户信息，过期信息，用户信息
        String tockns = token.generateToken(request.getHeader("User-Agent"), login);
        help.SetData(tockns, JSONArray.toJSONString(login));
        ItripTokenVO tokenVO = new ItripTokenVO(tockns, Calendar.getInstance().getTimeInMillis() * 7200, Calendar.getInstance().getTimeInMillis());

        return DtoUtil.returnDataSuccess(tokenVO);
    }

    @Resource
    CloopenBiz biz;
    @RequestMapping(value = "/registerbyphone", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto registerbyphone(@RequestBody ItripUserVO vo) throws Exception {
        int count= dao.phton(vo.getUserCode());
        if (count<=0) {
            try {
                ItripUser user=new ItripUser();
                user.setUserCode(vo.getUserCode());
                user.setUserName(vo.getUserName());
                user.setUserPassword(MD5.getMd5(vo.getUserCode(), 32));
                //第一步将数据存入数据库
                dao.registerbyphone(user);
                //第二步发送验证码
                int random = (int) (Math.random() * 10000);
                System.out.println(random);
                biz.Cloopen(vo.getUserCode(), random + "");
                help.SetData(vo.getUserCode(), random + "");
                //第三部将验证码存入redis数据库
                return DtoUtil.returnDataSuccess("注册成功");
            } catch (Exception e) {
                return DtoUtil.returnDataSuccess("注册失败");
            }
        }
        return DtoUtil.returnFail("此账号已经登录","1000000");
    }
//    激活手机号接口validatephone
    @RequestMapping(value = "/validatephone",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Dto validatephone(String user,String code)throws Exception{
        //第一步判断验证码与redis数据库中的是否相同
        String phoneCode=help.Getdata("user");//获取redis数据库当中的值通过key
        if (!(user.equals(null))||user.equals(phoneCode)){
            //第二步修正用户状态码
            dao.updateCode(user);
            return DtoUtil.returnSuccess("激活成功");
        }else{
            return DtoUtil.returnFail("激活失败","8081");
        }
    }
    //ckusr判断用户是否已经注册
    @RequestMapping(value = "/ckusr",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Dto ckusr(String name)throws Exception{
        int rows=dao.phton(name);

        if(rows<=0){
            return DtoUtil.returnDataSuccess("");
        }else{
            return DtoUtil.returnFail("此账号已经登录","1000000");
        }
    }
    //通过邮箱doregister进行注册

    @Resource
    MailSenderInfoBiz mail;
    @RequestMapping(value = "/doregister",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto doresgister(@RequestBody ItripUserVO vo)throws Exception{
       try {
           ItripUser user=new ItripUser();
           user.setUserPassword(MD5.getMd5(vo.getUserPassword(),32));
           user.setUserName(vo.getUserName());
           user.setUserCode(vo.getUserCode());
           //发送验证码
           int random = (int) (Math.random() * 1000000);
           mail.Manil(vo.getUserCode(),random+"");
           //将验证码存入redis数据库中
           help.SetData(vo.getUserCode(),random+"");
           dao.registerbyphone(user);
           return DtoUtil.returnSuccess("注册成功");
       }catch (Exception e){
           return DtoUtil.returnFail("注册失败","2342");
       }
    }
    //激活邮箱接口activate
    @RequestMapping(value = "/activate",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Dto activate(String user,String code)throws Exception{
        //第一步判断验证码与redis数据库中的是否相同
        String phoneCode=help.Getdata("user");//获取redis数据库当中的值通过key
        if (!(user.equals(null))||user.equals(phoneCode)){
            //第二步修正用户状态码
            dao.updateCode(user);
            return DtoUtil.returnSuccess("激活成功");
        }else{
            return DtoUtil.returnFail("激活失败","8081");
        }
    }


}



