package cn.itrip.dao.itripHotelRoom;
import cn.itrip.pojo.ItripHotelRoom;
import cn.itrip.pojo.ItripHotelRoomVO;
import cn.itrip.pojo.StoreVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelRoomMapper {
	public String getRoomPrice(@Param(value = "roomid") Long roomid,@Param(value = "hotelid") Long hotelid)throws Exception;

	public List<StoreVO> getstore(Map map);

	public void flushStore(Map map);

	public ItripHotelRoom getItripHotelRoomById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotelRoomVO>	getItripHotelRoomListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripHotelRoomCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception;

	public Integer updateItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception;

	public Integer deleteItripHotelRoomById(@Param(value = "id") Long id)throws Exception;

}
