package cn.itrip.biz;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;
import pojo.ItripHotelVO;
import pojo.Page;

import java.io.IOException;
import java.util.List;
@Component
public class BasheDao<T> {
    public  static  String url="http://localhost:8080/solr-4.9.1/hotel";
    HttpSolrClient httpSolrClient;
     public  BasheDao(){
         //初始化HttpSolrClient
         httpSolrClient = new HttpSolrClient(url);
         httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
         httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间
     }
     //不带分页的查询
     public List<T>getList(SolrQuery query) throws IOException, SolrServerException {
         QueryResponse queryResponse = null;
         queryResponse = httpSolrClient.query(query);
         List<T> list = (List<T>) queryResponse.getBeans(ItripHotelVO.class);
         return  list;
     }
     //带分页的查询
    public Page<T> getPaging(SolrQuery query, int Start, int PageSize)throws Exception{
         QueryResponse queryResponse=null;
         query.setStart((Start-1)*PageSize);//开始位置
         query.setRows(PageSize);//要显示几条
         queryResponse=httpSolrClient.query(query);
         List<T>list= (List<T>) queryResponse.getBeans(ItripHotelVO.class);
        SolrDocumentList solrDocument=((QueryResponse)queryResponse).getResults();
         Page page=new Page(Start,PageSize,new Long(solrDocument.getNumFound()).intValue());
         page.setRows(list);
         return page;


    }

}
