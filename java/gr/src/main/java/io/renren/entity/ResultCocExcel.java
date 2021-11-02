package io.renren.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zl
 */
@Data
public class ResultCocExcel implements Serializable {
    /**
     * {
     * "status": 0,
     * "message": "query ok",
     * "result": {
     * "title": "巴蜀文化村",
     * "location": {
     * "lng": 121.587509,
     * "lat": 29.929932
     * },
     * "ad_info": {
     * "adcode": "330205"
     * },
     * "address_components": {
     * "province": "浙江省",
     * "city": "宁波市",
     * "district": "江北区",
     * "street": "",
     * "street_number": ""
     * },
     * "similarity": 0.8,
     * "deviation": 1000,
     * "reliability": 7,
     * "level": 11
     * }
     * }
     */


    private Long status;
    private String message;
    private ResultTwo result;
}





