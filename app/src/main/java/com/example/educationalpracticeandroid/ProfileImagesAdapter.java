package com.example.educationalpracticeandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProfileImagesAdapter extends BaseAdapter {

    private Context context;
    List<ProfileImages> imageList;

    public ProfileImagesAdapter(Context context, List<ProfileImages> maskList) {
        this.context = context;
        this.imageList = maskList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return imageList.get(i).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProfileImages maskProfileImage  = imageList.get(position);
        View v = null;
        if(maskProfileImage.getImageProfile() == null)
        {
            v = View.inflate(context,R.layout.profile_image_add_item,null);
        }
        else
        {
            v = View.inflate(context,R.layout.profile_image_item,null);

            ImageView Image = v.findViewById(R.id.image);
            TextView dateCreate = v.findViewById(R.id.createDate);

            if(maskProfileImage.getImageProfile().exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(maskProfileImage.getImageProfile().getAbsolutePath());
                Image.setImageBitmap(myBitmap);
            }
            dateCreate.setText(maskProfileImage.getData());
        }
        return v;
    }
}
