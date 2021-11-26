package com.example.sysubbsproject.db.bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class Person extends BmobUser{
    private String nickname = "";
    private Integer age;
    private BmobRelation myLikes_App;
    private BmobRelation myCollections_App;
    private String icon = "";
    private String gender = "";
    private String country = "";
    private String city = "";
    private String birthday = "";

    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNick() {
        return this.nickname;
    }
    public void setNick(String nick) {
        this.nickname = nick;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getProfile() {
        return this.icon;
    }
    public void setProfile(String profile) {
        this.icon = profile;
    }
    public String getCountry() { return this.country; }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public BmobRelation getMyLikes(){ return this.myLikes_App;}
    public void setMyLikes(BmobRelation likes){ this.myLikes_App = likes;}
    public BmobRelation getMyCollections(){ return this.myCollections_App;}
    public void setMyCollections(BmobRelation collections){ this.myCollections_App = collections;}


}
