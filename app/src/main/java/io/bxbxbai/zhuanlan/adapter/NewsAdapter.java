package io.bxbxbai.zhuanlan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.DailyNews;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import taobe.tec.jcc.JChineseConvertor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public final class NewsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private LayoutInflater mInflater;

    private List<DailyNews> newsList;
    private List<String> dateResultList;

    private JChineseConvertor convertor;
    private boolean canConvert = true;
    private boolean shouldConvert = Locale.getDefault().equals(Locale.TRADITIONAL_CHINESE);

    public NewsAdapter(Context context, List<DailyNews> newsList) {
        this(context, newsList, null);
    }

    public NewsAdapter(Context context, List<DailyNews> newsList,
                       List<String> dateResultList) {
        this.mInflater = LayoutInflater.from(context);
        this.newsList = newsList;
        this.dateResultList = dateResultList;

        try {
            convertor = JChineseConvertor.getInstance();
        } catch (IOException e) {
            canConvert = false;
        }
    }

    public void updateNewsList(List<DailyNews> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void updateDateResultList(List<String> dateResultList) {
        this.dateResultList = dateResultList;
        notifyDataSetChanged();
    }

    public void updateContents(List<DailyNews> newsList, List<String> dateResultList) {
        updateNewsList(newsList);
        updateDateResultList(dateResultList);
    }

    @Override
    public int getCount() {
        return newsList == null ? 0 : newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardViewHolder holder;
        if (convertView == null) {
            holder = new CardViewHolder();
            convertView = mInflater.inflate(R.layout.news_list_item, null);

            assert convertView != null;
            holder.newsImage = (NetworkImageView) convertView.findViewById(R.id.profilePic);
            holder.dailyTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.questionTitle = (TextView) convertView.findViewById(R.id.tv_sub_title);
            convertView.setTag(holder);
        } else {
            holder = (CardViewHolder) convertView.getTag();
        }

        DailyNews dailyNews = new DailyNews(newsList.get(position));

//        imageLoader.displayImage(dailyNews.getThumbnailUrl(), holder.newsImage, options, animateFirstListener);
        holder.newsImage.setImageUrl(dailyNews.getThumbnailUrl(), App.getInstance().getImageLoader());

        if (shouldConvert && canConvert) {
            if (dailyNews.isMulti()) {
                holder.questionTitle.setText(convertor.s2t(dailyNews.getDailyTitle()));
                String traditionalMultiQuestion = "這裏包含多個知乎討論，請點擊後選擇";
                holder.dailyTitle.setText(traditionalMultiQuestion);
            } else {
                holder.questionTitle.setText(convertor.s2t(dailyNews.getQuestionTitle()));
                holder.dailyTitle.setText(convertor.s2t(dailyNews.getDailyTitle()));
            }
        } else {
            if (dailyNews.isMulti()) {
                holder.questionTitle.setText(dailyNews.getDailyTitle());
                String simplifiedMultiQuestion = "这里包含多个知乎讨论，请点击后选择";
                holder.dailyTitle.setText(simplifiedMultiQuestion);
            } else {
                holder.questionTitle.setText(dailyNews.getQuestionTitle());
                holder.dailyTitle.setText(dailyNews.getDailyTitle());
            }
        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return dateResultList.get(position).hashCode();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.search_result_list_header, null);

            assert convertView != null;
            holder.headerTitle = (TextView)
                    convertView.findViewById(R.id.header_title);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.headerTitle.setText(dateResultList.get(position));
        return convertView;
    }

    private final static class CardViewHolder {
        NetworkImageView newsImage;
        TextView questionTitle;
        TextView dailyTitle;
    }

    private final static class HeaderViewHolder {
        TextView headerTitle;
    }

//    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//        @Override
//        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//            if (loadedImage != null) {
//                ImageView imageView = (ImageView) view;
//                boolean firstDisplay = !displayedImages.contains(imageUri);
//                if (firstDisplay) {
//                    FadeInBitmapDisplayer.animate(imageView, 500);
//                    displayedImages.add(imageUri);
//                }
//            }
//        }
//    }
}
