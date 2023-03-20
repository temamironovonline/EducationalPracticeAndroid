package com.example.educationalpracticeandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class FeelingsAdapter extends RecyclerView.Adapter<FeelingsAdapter.ViewHolder>{

    private final List<Feelings> feelingsArrayList;
    private final Context context;

    public FeelingsAdapter(List<Feelings> feelingsArrayList, Context context) {
        this.feelingsArrayList = feelingsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.feelings_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Feelings feels = feelingsArrayList.get(position);
        holder.title.setText(feels.getTitle());

        if (feels.getImage().equals("null")) {
            holder.image.setImageResource(R.drawable.default_image);
        } else {
            new DownloadImage(holder.image)
                    .execute(feels.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return feelingsArrayList.size();
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        ImageView imageBitmap;

        public DownloadImage(ImageView imageBitmap) {
            this.imageBitmap = imageBitmap;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlImage = urls[0];
            Bitmap iconImage = null;
            try {
                InputStream in = new java.net.URL(urlImage).openStream();
                iconImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return iconImage;
        }

        protected void onPostExecute(Bitmap result) {
            imageBitmap.setImageBitmap(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.feelingTitle);
            image = itemView.findViewById(R.id.feelingIcon);
        }
    }

}
