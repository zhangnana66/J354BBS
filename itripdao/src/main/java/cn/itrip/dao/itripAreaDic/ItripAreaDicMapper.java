package cn.itrip.dao.itripAreaDic;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripAreaDicVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripAreaDicMapper {
	//热门城市的热门地点
	public  List<ItripAreaDicVO>Hotplace(@Param(value = "pid")String pid)throws Exception;
	//热门酒店推荐国内国外
	public List<ItripAreaDic> RecommendedHotel(@Param(value = "istype")String type)throws Exception;

	public ItripAreaDic getItripAreaDicById(@Param(value = "id") Long id)throws Exception;

	public List<ItripAreaDic>	getItripAreaDicListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripAreaDicCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripAreaDic(ItripAreaDic itripAreaDic)throws Exception;

	public Integer updateItripAreaDic(ItripAreaDic itripAreaDic)throws Exception;

	public Integer deleteItripAreaDicById(@Param(value = "id") Long id)throws Exception;

}
