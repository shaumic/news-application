package com.app.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//Store List Adapter
public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get business data
        final News item = getItem(position);

        // Create a layout
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);

        // Get ImageView and TextView
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView content = (TextView)itemView.findViewById(R.id.content);

        // Set up ITextView based on data
        title.setText(item.getTitle());
        content.setText(item.getContent());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Initialize an Intent that is ready to jump to NEWSDetailActivity
                Intent intent = new Intent(getContext(), NewsActivity.class);

                // Into into the Intent NEWS related data for TeacherDetailActivity use
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content",item.getContent());
               // intent.putExtra("teacher_desc", teacher.getDesc());

                //  Initialize an Intent that is ready to jump to NEWSDetailActivity
                getContext().startActivity(intent);
            }
        });
        return itemView;
    }
}
