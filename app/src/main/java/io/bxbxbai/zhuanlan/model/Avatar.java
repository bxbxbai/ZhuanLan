package io.bxbxbai.zhuanlan.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baia on 14-6-2.
 */
public class Avatar {

    public static final String ID = "id";

    public static final String TEMPLATE = "template";

    @SerializedName(ID)
    private String id;

    @SerializedName(TEMPLATE)
    private String template;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
