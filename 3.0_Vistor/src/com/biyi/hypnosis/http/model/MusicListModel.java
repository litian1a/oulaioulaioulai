package com.biyi.hypnosis.http.model;

import java.util.List;

/**
 * Description：
 * Time：2019-09-23 18:03
 *
 * @author：ltc
 */
public class MusicListModel {
    
    private List<TagListBean> musicList;
    
    public List<TagListBean> getTagList() {
        return musicList;
    }
    
    public void setTagList(List<TagListBean> tagList) {
        this.musicList = tagList;
    }
    
    public static class TagListBean {
        /**
         * musicName : 夏⽇日⻛风扇
         * musicId : 206ca66f882b29af2bd5c746baca25d5
         * url : xxxx
         */
        
        private String musicName;
        private String musicId;
        private String url;
        
        public String getMusicName() {
            return musicName;
        }
        
        public void setMusicName(String musicName) {
            this.musicName = musicName;
        }
        
        public String getMusicId() {
            return musicId;
        }
        
        public void setMusicId(String musicId) {
            this.musicId = musicId;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
    }
}
