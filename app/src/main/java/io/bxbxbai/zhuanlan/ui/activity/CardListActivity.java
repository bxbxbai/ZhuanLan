package io.bxbxbai.zhuanlan.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import io.bxbxbai.zhuanlan.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baia on 14-8-28.
 */
public class CardListActivity extends Activity {

    CardListView cardListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        cardListView = (CardListView)findViewById(R.id.lv_card);

        List<Card> list = new ArrayList<Card>();
        Card card = new Card(this);
        CardHeader header = new CardHeader(this);

        card.addCardHeader(header);
        list.add(card);
        list.add(new Card(this));
        list.add(new Card(this));

        CardArrayAdapter adapter = new CardArrayAdapter(this, list);
        cardListView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
