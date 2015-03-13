package io.bxbxbai.zhuanlan.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.bxbxbai.zhuanlan.R;
import it.gmariotti.cardslib.library.internal.Card;

import java.lang.reflect.ParameterizedType;

/**
 * Created by baia on 14-8-23.
 * @author bxbxbai
 * @version 1.0.0
 * @since 2014.08.23
 */
public class NewsCard extends Card {

    ImageView newsImage;
    TextView questionTitle;
    TextView dailyTitle;

    public NewsCard(Context context) {
        this(context, R.layout.search_result_list_header);
    }

    public NewsCard(Context context, int layout) {
        super(context, layout);

    }

    private void init(){
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        newsImage = (ImageView) parent.findViewById(R.id.profilePic);
        dailyTitle = (TextView) parent.findViewById(R.id.tv_title);
        questionTitle = (TextView) parent.findViewById(R.id.tv_sub_title);
    }
}
