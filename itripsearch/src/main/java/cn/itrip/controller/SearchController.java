package cn.itrip.controller;

import cn.itrip.biz.BasheDao;
import common.Dto;
import common.DtoUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.ItripHotelVO;
import pojo.Page;
import pojo.SearchHotCityVO;
import pojo.SearchHotelVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SearchController {
    @Resource
    BasheDao dao;
    @RequestMapping(value = "/hotellist/searchItripHotelListByHotCity",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Dto getSearch(@RequestBody SearchHotCityVO vo) throws IOException, SolrServerException {
        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");//全部查询、
        query.addFilterQuery("cityId:"+vo.getCityId());
        query.setStart(0);//开始位置
        query.setRows(6);//显示几条
        List<ItripHotelVO> pojo=dao.getList(query);
        return DtoUtil.returnDataSuccess(pojo);
    }

    @RequestMapping(value = "/hotellist/searchItripHotelPage",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Dto<ItripHotelVO>getPage(@RequestBody SearchHotelVO vo)throws Exception{
        SolrQuery query=new SolrQuery();//创建solr对象

        query.setQuery("*:*");
        if(vo.getPageNo()==null){
            vo.setPageNo(1);
        }
        if(vo.getPageSize()==null){
            vo.setPageSize(6);
        }
        if(vo.getHotelLevel()!=null){
            query.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
        }
        if(vo.getAscSort()!=null){
            query.setSort(vo.getAscSort(), SolrQuery.ORDER.desc);
        }
        //按照价格的区间查询
        if(vo.getMaxPrice()!=null){
            query.addFilterQuery(" minPrice:[* TO "+vo.getMaxPrice()+"]");
        }
        if(vo.getMinPrice()!=null){
            query.addFilterQuery(" minPrice:["+vo.getMinPrice()+" TO *]");
        }
        //按照酒店对的特色查询* featureIds:*,7,*  or featureIds:*,8,*
        if(vo.getFeatureIds()!=null&&vo.getFeatureIds()!=""){

            String [] Feature=vo.getFeatureIds().split(",");//将前台数据按照,拆分出来
            String str="";//用于拼接数据
            for(int i=0;i<Feature.length;i++){//循环数组
                if(i==0){//如果i没有值得时候就不拼接
                    str+="featureIds:*,"+Feature[i]+",*";
                }else{
                    str+="or featureIds:*,"+Feature[i]+",* ";
                }
            }
            query.addFilterQuery(str);
        }
        //按照酒店的位置查询tradingAreaIds:*,3619,*
        if(vo.getTradeAreaIds()!=null&&vo.getTradeAreaIds()!=""){
            String []Trade=vo.getTradeAreaIds().split(",");
            String str="";
            for(int i=0;i<Trade.length;i++){
                if(i==0){
                    str+="tradingAreaIds:*,"+Trade[i]+",*";
                }else{
                    str+="or tradingAreaIds:*,"+Trade[i]+",*";
                }
            }
            query.addFilterQuery(str);
        }
        Page <ItripHotelVO> page= dao.getPaging(query,vo.getPageNo(),vo.getPageSize());
        return DtoUtil.returnDataSuccess(page);
    }

}
