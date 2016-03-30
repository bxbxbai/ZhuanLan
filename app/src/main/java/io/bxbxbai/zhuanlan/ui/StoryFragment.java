package io.bxbxbai.zhuanlan.ui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.Scanner;

import butterknife.ButterKnife;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.common.utils.CommonExecutor;
import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebViewClient;
import io.bxbxbai.zhuanlan.utils.Utils;
import io.bxbxbai.zhuanlan.widget.CommonWebView;
import io.bxbxbai.zhuanlan.widget.ObservableScrollView;

/**
 * Created by xuebin on 15/10/15.
 */
public class StoryFragment extends Fragment {

    public static final String KEY_POST = "_post";

    private ObservableScrollView scrollView;
    private CommonWebView mWebView;
    private CircleImageView mAvatarView;
    private ImageView headerImageView;
    private TextView titleView;
    private TextView authorTextView;

    private Post mPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mWebView = ButterKnife.findById(view, R.id.web_view);
        scrollView = ButterKnife.findById(view, R.id.scroll_view);
        titleView = ButterKnife.findById(view, R.id.tv_title);
        authorTextView = ButterKnife.findById(view, R.id.tv_author);
        headerImageView = ButterKnife.findById(view, R.id.iv_article_header);
        mAvatarView = ButterKnife.findById(view, R.id.iv_avatar);

        scrollView.addOnScrollListener(new ObservableScrollView.OnScrollListener() {
            @Override
            public void onScroll(int distance) {
                StopWatch.log("dis: " + distance);
            }
        });

        mPost = getArguments().getParcelable(KEY_POST);
        if (mPost == null) {
            getActivity().finish();
            return;
        }

        mWebView.setWebViewClient(new ZhuanLanWebViewClient(getActivity()));
//        mWebView.loadDataWithBaseURL(null, CSS_STYLE + mPost.getContent(), MIME_TYPE, ENCODING_UTF_8, null);
        setStory();
    }

    private void setStory() {
        loadHtmlContent(mPost.getContent());
        Glide.with(getActivity()).load(mPost.getImageUrl()).crossFade().into(headerImageView);
        titleView.setText(mPost.getTitle());
        authorTextView.setText(mPost.getAuthorName() + ", " + Utils.convertPublishTime(mPost.getPublishedTime()));

        String id = mPost.getAuthor().getAvatar().getId();
        String picUrl = Utils.getAuthorAvatarUrl(mPost.getAuthor().getAvatar().getTemplate(),
                id, ZhuanLanApi.PIC_SIZE_XS);
        Glide.with(getActivity()).load(picUrl).crossFade().into(mAvatarView);
        CommonExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                injectCSS();
            }
        }, 200);
    }

    private void loadHtmlContent(String section) {
        String content = String.format(readFile("template.txt"), section);
        mWebView.loadDataWithBaseURL(null, content, CommonWebView.MIME_TYPE, CommonWebView.ENCODING_UTF_8, null);
    }

    private void injectCSS() {
        String encoded = Base64.encodeToString(readFile("zhuanlan.main.css").getBytes(), Base64.NO_WRAP);
        mWebView.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "style.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(style)" +
                "})()");
    }

    private String readFile(String fileName) {
        AssetManager manager = getActivity().getAssets();
        try {
            Scanner scanner = new Scanner(manager.open(fileName));
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static StoryFragment newInstance(Post post) {
        StoryFragment fragment = new StoryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_POST, post);
        fragment.setArguments(bundle);
        return fragment;
    }
}
