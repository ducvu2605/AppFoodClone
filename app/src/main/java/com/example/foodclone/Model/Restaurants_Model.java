package com.example.foodclone.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodclone.Controller.Interfaces.Place_Interface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Restaurants_Model {

    boolean delivery ;
    String nameRE;
    String opentime;
    String closetime;
    String RE_code;
    List<String> listUtilities , imagesRESList;
    long likes;
    List<CmtModel> cmtModelList;
    private  DatabaseReference nodeRoot;

    public List<CmtModel> getCmtModelList() {
        return cmtModelList;
    }

    public void setCmtModelList(List<CmtModel> cmtModelList) {
        this.cmtModelList = cmtModelList;
    }

    public String getRE_code() {
        return RE_code;
    }

    public void setRE_code(String RE_code) {
        this.RE_code = RE_code;
    }

    public List<String> getImagesRESList() {
        return imagesRESList;
    }

    public void setImagesRESList(List<String> imagesRESList) {
        this.imagesRESList = imagesRESList;
    }

    public Restaurants_Model() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }
    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getNameRE() {
        return nameRE;
    }

    public void setNameRE(String nameRE) {
        this.nameRE = nameRE;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getClosetime() {
        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    public List<String> getListUtilities() {
        return listUtilities;
    }

    public void setListUtilities(List<String> listUtilities) {
        this.listUtilities = listUtilities;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public void getDanhsachQuanAn(final Place_Interface placeInterface){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("nodeRood",dataSnapshot+"");
                DataSnapshot dataSnapshotREs = dataSnapshot.child("restaurants");
                    // Get list RES
                for (DataSnapshot valueRES :dataSnapshotREs.getChildren()){
                    Restaurants_Model restaurants_model = valueRES.getValue(Restaurants_Model.class);
                    restaurants_model.setRE_code(valueRES.getKey()); // lấy Key của RES_Code
                    Log.d("CheckNameRES",restaurants_model.getNameRE());

                        // Get list Images RES follow code
                    DataSnapshot dataImagesRES = dataSnapshot.child("images").child(valueRES.getKey());// get CodeRES

                    List<String> imagesList = new ArrayList<>(); // creat 1 list PHoto
                    for(DataSnapshot valueImages : dataImagesRES.getChildren()) // duyệt các KEY ĐỘng lấy Value
                    {
                        Log.d("CheckValueImage",valueImages+"");
                        imagesList.add(valueImages.getValue(String.class));
                    }
                    restaurants_model.setImagesRESList(imagesList);

                     //Get List Comment of Restaurant
                    DataSnapshot dataCmtRES = dataSnapshot.child("comments").child(restaurants_model.getRE_code());
                    List<CmtModel> cmtList = new ArrayList<>();
                    for(DataSnapshot valueCMT : dataCmtRES.getChildren()){
                        Log.d("CheckCMT",valueCMT.getValue()+"");
                        CmtModel cmtModel = valueCMT.getValue(CmtModel.class);
                        cmtModel.setCodeCMT(valueCMT.getKey());
                        UserModel userModel = dataSnapshot.child("users").child(cmtModel.getUser_code()).getValue(UserModel.class);
                        cmtModel.setUserModel(userModel);

                        List<String>imageCMTList = new ArrayList<>();
                       DataSnapshot dataCodeCMT =  dataSnapshot.child("imageCMT").child(cmtModel.getCodeCMT());
                       for (DataSnapshot valueImageCMT : dataCodeCMT.getChildren()){
                           Log.d("CheckValueImageCMT",valueImageCMT+"");
                           imageCMTList.add(valueImageCMT.getValue(String.class));
                       }
                        cmtModel.setImageCMT(imageCMTList);
                        cmtList.add(cmtModel);
                    }
                    restaurants_model.setCmtModelList(cmtList);
                    placeInterface.getDanhsachQuanAnModel(restaurants_model);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        nodeRoot.addValueEventListener(valueEventListener);
    }
//        public void getDanhSachQuanAn(){
//        List<Restaurants_Model> restaurantsModelList = new ArrayList<>();
//
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("KIEMTRA",dataSnapshot.child("restaurants")+"");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//
//            nodeRoot.addValueEventListener(valueEventListener);
//        }
}
