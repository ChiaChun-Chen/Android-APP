package com.example.youbikeInfoGetting__fragment_recyclerview_api.placeholder;

import java.util.ArrayList;
import java.util.List;

public class mPlaceholder {

    public static final List<mPlaceholderItem> ITEMS = new ArrayList<mPlaceholderItem>();


    public static void addItem(mPlaceholderItem item){
        ITEMS.add(item);
    }

    public static mPlaceholderItem createMyPlaceholderItem(int position, String sna, String tot){
        return new mPlaceholderItem(String.valueOf(position), "Item "+ position, sna, tot);
    }

    public static class mPlaceholderItem{
        public final String mId;
        public final String mContent;
        public final String sna;
        public final String tot;
        public mPlaceholderItem(String id, String content, String sna, String tot){
            this.mId = id;
            this.mContent = content;
            this.sna = sna;
            this.tot = tot;
        }
    }
}
