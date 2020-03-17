package com.example.foodclone.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodclone.Model.CmtModel;
import com.example.foodclone.Model.Restaurants_Model;
import com.example.foodclone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecycler_Place extends RecyclerView.Adapter<AdapterRecycler_Place.ViewHolder> {
    List<Restaurants_Model> restaurantsModelList;
    int resource;
    public AdapterRecycler_Place(List<Restaurants_Model> restaurantsModelList,int resource) {
        this.restaurantsModelList = restaurantsModelList;
        this.resource = resource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameREsPlace , tvAddressRES,tvTitleCmt,tvTitleCmt2,tvContentCmt,tvContentCmt2,tvPoint,tvPoint2,
        tvTotalCMT,tvTotalPhoto , tvMediumSocre;
        private Button btnOrderRES;
        private  ImageView imageRES;
        private CircleImageView imgUserCmt,imgUserCmt2;
        private LinearLayout ContainerCMT,ContainerCMT2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameREsPlace = itemView.findViewById(R.id.tv_NameREsPlace);
            tvAddressRES = itemView.findViewById(R.id.tv_AddressRES);
            btnOrderRES = itemView.findViewById(R.id.btn_OrderRES);
            imageRES = itemView.findViewById(R.id.img_ImageRES);
            imgUserCmt = itemView.findViewById(R.id.img_UserCmt);
            imgUserCmt2 = itemView.findViewById(R.id.img_UserCmt2);
            tvTitleCmt = itemView.findViewById(R.id.tv_TitleCmt);
            tvTitleCmt2 = itemView.findViewById(R.id.tv_TitleCmt2);
            tvContentCmt = itemView.findViewById(R.id.tv_ContentCmt);
            tvContentCmt2 = itemView.findViewById(R.id.tv_ContentCmt2);
            tvPoint = itemView.findViewById(R.id.tv_Point);
            tvPoint2 = itemView.findViewById(R.id.tv_Point2);
            ContainerCMT = itemView.findViewById(R.id.ContainerCMT);
            ContainerCMT2 = itemView.findViewById(R.id.ContainerCMT2);
            tvTotalCMT = itemView.findViewById(R.id.tv_TotalCMT);
            tvTotalPhoto = itemView.findViewById(R.id.tv_TotalPhoto);
            tvMediumSocre = itemView.findViewById(R.id.tv_MediumScore);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Restaurants_Model restaurants_model = restaurantsModelList.get(position);
        holder.tvNameREsPlace.setText(restaurants_model.getNameRE());
        holder.tvAddressRES.setText(restaurants_model.getNameRE());

        if (restaurants_model.isDelivery()) {
            // Giao hang == true => SHhow BUtton
            holder.btnOrderRES.setVisibility(View.VISIBLE);
        }
        Log.d("CheckSizeImages", restaurants_model.getImagesRESList().size() + ""); // Check có bao nhiêu hình trong list;
        if (restaurants_model.getImagesRESList().size() > 0) {
            StorageReference storageImages = FirebaseStorage.getInstance().getReference().child("imageRES").child(restaurants_model.getImagesRESList().get(0)); // lấy hình ở vị trí 0
            long ONE_MEGA_BYTE = 1024 * 1024;
            storageImages.getBytes(ONE_MEGA_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageRES.setImageBitmap(bitmap);
                }
            });
        }
        Log.d("CheckSizeCMT", restaurants_model.getCmtModelList().size()+"");
        // Danh sách các Cmt của QUán ăn
        if(restaurants_model.getCmtModelList().size()>0) {
            CmtModel cmtModel = restaurants_model.getCmtModelList().get(0);
            holder.tvTitleCmt.setText(cmtModel.getTitle());
            holder.tvContentCmt.setText(cmtModel.getContent());
            holder.tvPoint.setText(cmtModel.getPoint()+"");
            setImageCmt(holder.imgUserCmt,cmtModel.getUserModel().getPhoto());

            if(restaurants_model.getCmtModelList().size()>2) {
                CmtModel cmtMode2 = restaurants_model.getCmtModelList().get(1);
                holder.tvTitleCmt2.setText(cmtMode2.getTitle());
                holder.tvContentCmt2.setText(cmtMode2.getContent());
                holder.tvPoint2.setText(cmtModel.getPoint()+"");
                setImageCmt(holder.imgUserCmt2, cmtMode2.getUserModel().getPhoto());
            }
            holder.tvTotalCMT.setText(restaurants_model.getCmtModelList().size()+"");

            int totalPhotoCMT = 0; double totalScore = 0;
            // Đếm tổng số hình ảnh và tổng điểm của các bình luận
            for (CmtModel cmtModel1: restaurants_model.getCmtModelList()){
                totalPhotoCMT += cmtModel1.getImageCMT().size();
                totalScore += cmtModel1.getPoint();
            }
            double mediumScore = totalScore /restaurants_model.getCmtModelList().size(); // Tổng điểm chia tổng số cmt;
            holder.tvMediumSocre.setText(String.format("%.1f",mediumScore));

            if (totalPhotoCMT > 0) {
                holder.tvTotalPhoto.setText(totalPhotoCMT + "");
            }
        }else{
            holder.ContainerCMT.setVisibility(View.GONE);
            holder.ContainerCMT2.setVisibility(View.GONE);

        }


    }
    private  void setImageCmt(final CircleImageView circleImageView, String linkHinh){
        StorageReference storageImagesCMT = FirebaseStorage.getInstance().getReference().child("user").child(linkHinh);// lấy hình ở vị trí 0
        long ONE_MEGA_BYTE = 1024 * 1024;
        storageImagesCMT.getBytes(ONE_MEGA_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }


    @Override
    public int getItemCount() {
        return restaurantsModelList.size();
    }


}
