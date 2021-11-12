package cn.itrip.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Useradnfr implements Serializable {
        //
        private Integer id;
        //
        private Integer RID;
        //
        private Integer FID;
        //get set 方法
            public void setId (Integer  id){
                this.id=id;
            }
            public  Integer getId(){
                return this.id;
            }
            public void setRID (Integer  RID){
                this.RID=RID;
            }
            public  Integer getRID(){
                return this.RID;
            }
            public void setFID (Integer  FID){
                this.FID=FID;
            }
            public  Integer getFID(){
                return this.FID;
            }
}
