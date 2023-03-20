package com.example.educationalpracticeandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ElementsAdapter extends BaseAdapter {

    private final Context context;
    List<Elements> elementsList;

    public ElementsAdapter(Context context, List<Elements> elementsList) {
        this.context = context;
        this.elementsList = elementsList;
    }

    @Override
    public int getCount() {
        return elementsList.size();
    }

    @Override
    public Object getItem(int i) {
        return elementsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return elementsList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.elements_list, null);

        TextView title = v.findViewById(R.id.Title);
        ImageView Image = v.findViewById(R.id.Images);
        TextView description = v.findViewById(R.id.description);

        Elements maskQuote = elementsList.get(position);
        title.setText(maskQuote.getTitle());

        if (maskQuote.getImage().equals("null")) {
            Image.setImageResource(R.drawable.default_image);
        } else {
            new DownloadImage(Image)
                    .execute(maskQuote.getImage());
        }

        description.setText(maskQuote.getDescription());
        return v;
    }

    static class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageBitmap;

        public DownloadImage(ImageView bmImage) {
            this.imageBitmap = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlImage = urls[0];
            Bitmap iconImage = null;
            try {
                InputStream in = new URL(urlImage).openStream();
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
}
