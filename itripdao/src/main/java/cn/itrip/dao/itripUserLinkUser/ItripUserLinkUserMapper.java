package cn.itrip.dao.itripUserLinkUser;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserLinkUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripUserLinkUserMapper {


	//查询常用联系人
	public List<ItripUser> getUserList(ItripUser user)throws Exception;
	public ItripUserLinkUser getItripUserLinkUserById(@Param(value = "id") Long id)throws Exception;

	public List<ItripUserLinkUser>	getItripUserLinkUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripUserLinkUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

	public Integer updateItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

	public Integer updateUser(ItripUserLinkUser itripUserLinkUser)throws  Exception;
	public Integer deleteItripUserLinkUserById(@Param(value = "id") Long id)throws Exception;

}
