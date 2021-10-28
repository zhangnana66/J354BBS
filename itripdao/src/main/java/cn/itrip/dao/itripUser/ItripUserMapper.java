package cn.itrip.dao.itripUser;
import cn.itrip.pojo.ItripUser;
import cn.itrip.pojo.ItripUserVO;
import common.ItripTokenVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ItripUserMapper{

	//登录功能
	public ItripUser getLogin(@Param(value = "name") String name,@Param(value = "password")String password)throws Exception;
	public ItripUser getItripUserById(@Param(value = "id") Long id)throws Exception;
	//查询数据库是否存在这个手机号
	public int phton(@Param(value = "code")String code)throws Exception;

	public List<ItripUser>	getItripUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripUser(ItripUser itripUser)throws Exception;
	//手机号注册
	public Integer registerbyphone(ItripUser user)throws Exception;

	public Integer updateItripUser(ItripUser itripUser)throws Exception;
	//修改用户的状态码修改为激活成功
	public  Integer updateCode(@Param(value = "userCode")String userCode)throws Exception;

	public Integer deleteItripUserById(@Param(value = "id") Long id)throws Exception;

}
