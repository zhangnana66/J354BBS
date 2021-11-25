package cn.itrip.controller;

import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripHotel.ItripHotelMapper;
import cn.itrip.dao.itripHotelRoom.ItripHotelRoomMapper;
import cn.itrip.dao.itripImage.ItripImageMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.dao.itripUserLinkUser.ItripUserLinkUserMapper;
import cn.itrip.pojo.*;
import common.DateUtil;
import common.Dto;
import common.DtoUtil;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.BeginState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/api")
public class FerquentContacts {
    @Resource
    ItripUserLinkUserMapper linkUser;

    //查询常用用户的方法
    @RequestMapping(value = "/userinfo/queryuserlinkuser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto getpersonalorderlist(ItripUserLinkUser user) throws Exception {
        user.setUserId(29);
        List<ItripUser> list = linkUser.getUserList(user);
        return DtoUtil.returnDataSuccess(list);
    }

    //删除的方法
    @RequestMapping(value = "/userinfo/deluserlinkuser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto deleteUser(int ids) throws Exception {
        int num = linkUser.deleteItripUserLinkUserById(new Long(ids));
        return DtoUtil.returnDataSuccess(num);
    }

    //修改的方法
    @RequestMapping(value = "/userinfo/modifyuserlinkuser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto Modifyuserlinkuser(ItripUserLinkUser userLinkUser) throws Exception {
        int row = linkUser.updateUser(userLinkUser);
        return DtoUtil.returnDataSuccess(row);
    }

    @Resource
    ItripAreaDicMapper areaDicMapper;

    //主页酒店推荐（国内和国外）
    @RequestMapping(value = "/hotel/queryhotcity/{type}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto queryHotcity(@PathVariable("type") String Type) throws Exception {
        List<ItripAreaDic> list = areaDicMapper.RecommendedHotel(Type);
        return DtoUtil.returnDataSuccess(list);
    }

    //主页特殊酒店设施推荐
    @Resource
    ItripLabelDicMapper labelDicMapper;

    @RequestMapping(value = "/hotel/queryhotelfeature", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto queryhotelfeature() throws Exception {
        List<ItripLabelDic> list = labelDicMapper.RecommendFacilities();
        return DtoUtil.returnDataSuccess(list);
    }

    //热门城市的热门地方查询
    @RequestMapping(value = "/hotel/querytradearea/{pid}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto<ItripAreaDicVO> getHotplace(@PathVariable("pid") String pid) throws Exception {
        List<ItripAreaDicVO> list = areaDicMapper.Hotplace(pid);
        return DtoUtil.returnDataSuccess(list);
    }

    @Resource
    ItripHotelRoomMapper roomMapper;

    //根据酒店查询房间
    @RequestMapping(value = "/hotelroom/queryhotelroombyhotel", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto<List<ItripHotelRoomVO>> getRoom(@RequestBody SearchHotelRoomVO vo) throws Exception {

        Long hootid = vo.getHotelId();
        List<Date> getDate = DateUtil.getBetweenDates(vo.getStartDate(), vo.getEndDate());
        Map map = new HashMap();
        //是否包含早餐
        if (vo.getIsHavingBreakfast() != null && vo.getIsHavingBreakfast() != 0) {
            map.put("isHavingBreakfast", vo.getIsHavingBreakfast());
        }
        //是否可取消
        if (vo.getIsCancel() != null && vo.getIsCancel() != 0) {
            map.put("isCancel", vo.getIsCancel());
        }
        //可预订
        if (vo.getIsBook() != null && vo.getIsBook() != 0) {
            map.put("isBook", vo.getIsBook());
        }
        //床型
        if (vo.getRoomBedTypeId() != null && vo.getRoomBedTypeId() != 0) {
            map.put("roomBedTypeId", vo.getRoomBedTypeId());
        }
        map.put("hotelId", hootid);
        map.put("timesList", getDate);
        List<ItripHotelRoomVO> list = roomMapper.getItripHotelRoomListByMap(map);
        List<List<ItripHotelRoomVO>> roomList = null;
        roomList = new ArrayList<>();
        for (ItripHotelRoomVO room : list) {
            List<ItripHotelRoomVO> hotelRoomVOS = new ArrayList<>();
            hotelRoomVOS.add(room);
            roomList.add(hotelRoomVOS);
        }

        return DtoUtil.returnSuccess("获取成功", roomList);

    }

    //根据targetId查询酒店房型图片
    @Resource
    ItripImageMapper itripImage;

    @RequestMapping(value = "/hotelroom/getimg/{targetId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto getImg(@PathVariable String targetId) throws Exception {
        List<ItripImage> list = itripImage.getRoomImg(targetId);
        return DtoUtil.returnDataSuccess(list);
    }

    @Resource
    ItripLabelDicMapper dicMapper;

    //查询房间的房型/hotelroom/queryhotelroombed
    @RequestMapping(value = "/hotelroom/queryhotelroombed", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto gethotelroom() throws Exception {
        List<ItripLabelDic> list = dicMapper.hotelroombed();
        return DtoUtil.returnDataSuccess(list);
    }

    //根据酒店id查询酒店特色和介绍/hotel/queryhoteldetails/{id}getItripHotelById
    @Resource
    ItripHotelMapper hotelMapper;

    @RequestMapping(value = "/hotel/queryhoteldetails/{id}", produces = "application/json;chatset=UTF-8")
    @ResponseBody
    public Dto<ItripHotel>  getHotelDetails(@PathVariable Integer id) throws Exception {
        List<ItripHotel> list = hotelMapper.getDetalis(id);
        return  DtoUtil.returnDataSuccess(list);
    }

//    //生成订单前,获取预订信息/hotelorder/getpreorderinfo
    @RequestMapping(value = "/hotelorder/getpreorderinfo",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Dto getpreorderinfo(@RequestBody ValidateRoomStoreVO vo)throws Exception{
        //第一步查询存储过程，如果当前时间在临时表中没有数据就给他插入进去
        Map map=new HashMap();
        map.put("hotelid1",vo.getHotelId());
        map.put("roomId1",vo.getRoomId());
        map.put("starDate1",vo.getCheckInDate());
        map.put("endDate1",vo.getCheckOutDate());
        roomMapper.flushStore(map);
        //第二不查询房间的最小剩余数量

        Map map1=new HashMap();
        String hotelName=hotelMapper.getHotelName(vo.getHotelId());

        map1.put("roomid",vo.getRoomId());
        map1.put("starDate",vo.getCheckInDate());
        map1.put("endDate",vo.getCheckOutDate());
        List<StoreVO>list=roomMapper.getstore(map1);
        System.out.println(list.size());
        RoomStoreVO roomStoreVO=new RoomStoreVO();
        roomStoreVO.setStore(list.get(0).Store);
        roomStoreVO.setRoomId(vo.getRoomId());
        roomStoreVO.setCheckInDate(vo.getCheckInDate());
        roomStoreVO.setCheckOutDate(vo.getCheckOutDate());
        roomStoreVO.setPrice(new BigDecimal(roomMapper.getRoomPrice(vo.getRoomId(),vo.getHotelId())));
        roomStoreVO.setHotelName(hotelName);

        return  DtoUtil.returnDataSuccess(roomStoreVO);
    }


}
