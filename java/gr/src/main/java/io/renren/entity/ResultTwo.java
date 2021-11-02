package io.renren.entity;

import lombok.Data;

@Data
 public class ResultTwo {
    private String title;
    private LocationExcel location;
    private AdInfo ad_info;
    private AddressComponents address_components;


    private Double similarity;
    private Integer deviation;
    private Integer reliability;
    private Integer level;
}
@Data
class AddressComponents {
   private String province;
   private String city;
   private String district;
   private String street;
   private String street_number;

}

@Data
class AdInfo {
   private String adcode;

}