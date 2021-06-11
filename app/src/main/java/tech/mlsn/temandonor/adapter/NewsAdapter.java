package tech.mlsn.temandonor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.response.NewsDataResponse;
import tech.mlsn.temandonor.tools.Tools;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsDataResponse> items;
    private List<NewsDataResponse> itemsFiltered;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, NewsDataResponse obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public NewsAdapter(Context context, List<NewsDataResponse> items) {
        this.items = items;
        this.itemsFiltered = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public ImageView ivNews;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            title =  v.findViewById(R.id.tvTitle);
            desc = v.findViewById(R.id.tvDesc);
            ivNews = v.findViewById(R.id.ivNews);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            NewsDataResponse x = itemsFiltered.get(position);
            view.title.setText(x.getHeadline());
            view.desc.setText(Tools.dateParser(x.getNewsDate()));
            Glide.with(ctx).load("http://temandonor.rzzkan.com/picture/"+x.getFilename()).centerCrop().into(view.ivNews);

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, itemsFiltered.get(position), position);
                    }
                }
            });
        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d("Filter", "performFiltering: "+charString);
                if (charString.isEmpty()) {
                    itemsFiltered = items;
                } else {
                    List<NewsDataResponse> filteredList = new ArrayList<>();
                    for (NewsDataResponse data : items) {
                        String name = data.getHeadline().toLowerCase().trim();
                        if(name.contains(charString.toLowerCase().trim())){
                            filteredList.add(data);
                        }
                    }

                    itemsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsFiltered = (ArrayList<NewsDataResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return itemsFiltered.size();
    }
}