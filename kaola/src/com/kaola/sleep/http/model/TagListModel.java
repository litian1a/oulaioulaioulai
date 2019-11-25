package com.sleep.kaola.http.model;

import java.util.List;

/**
 * Description：
 * Time：2019-09-23 15:24
 *
 * @author：ltc
 */
public class TagListModel {
    private List<TagListBean> tagList;
    
    public List<TagListBean> getTagList() {
        return tagList;
    }
    
    public void setTagList(List<TagListBean> tagList) {
        this.tagList = tagList;
    }
    
    public static class TagListBean {
        /**
         * tagName : 分类1
         * iconUrl : xxxx
         * tagId : 1
         */
        
        private String tagName;
        private String iconUrl;
        private int tagId;
        
        public String getTagName() {
            return tagName;
        }
        
        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
        
        public String getIconUrl() {
            return iconUrl;
        }
        
        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
        
        public int getTagId() {
            return tagId;
        }
        
        public void setTagId(int tagId) {
            this.tagId = tagId;
        }
    }
}
