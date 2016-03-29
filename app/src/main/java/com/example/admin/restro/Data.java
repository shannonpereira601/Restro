package com.example.admin.restro;

/**
 * Created by admin on 05/03/2016.
 */
public class Data {
    public int hotelphoto;
    public String hotelname;
    public String location;

    public Data(int hotelphoto,String hotelname,String location)
    {
        this.setHotelphoto(hotelphoto);
        this.setHotelname(hotelname);
        this.setLocation(location);
    }

    public int getHotelphoto(){
        return hotelphoto;
    }

    public void setHotelphoto(int hotelphoto){
        this.hotelphoto = hotelphoto;
    }

    public String getHotelname(){
        return hotelname;
    }

    public void setHotelname(String hotelname){
        this.hotelname = hotelname;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

}
