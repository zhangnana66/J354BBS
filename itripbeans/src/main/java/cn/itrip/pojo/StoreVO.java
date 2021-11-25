package cn.itrip.pojo;

import java.util.Date;

/**
 * 验证房间库存时，返回的库存列表VO
 * Created by XX on 17-5-17.
 */
public class StoreVO {



    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getStore() {
        return Store;
    }

    public void setStore(int store) {
        Store = store;
    }
    public int roomId;

    public StoreVO()
    {

    }
    public StoreVO(int roomId, Date recordDate, int store) {
        this.roomId = roomId;
        this.recordDate = recordDate;
        Store = store;
    }

    public Date recordDate;
    public int Store;
}

