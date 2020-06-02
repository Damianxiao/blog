package com.damian.blog.vo;

public class BlogQuery {

        private String title;
        private  Long typeId;

        @Override
        public String toString() {
                return "BlogQuery{" +
                        "title='" + title + '\'' +
                        ", typeId=" + typeId +
                        ", recommend=" + recommend +
                        '}';
        }

        public boolean isRecommend() {
                return recommend;
        }

        public void setRecommend(boolean recommend) {
                this.recommend = recommend;
        }

        private  boolean   recommend;

        public BlogQuery(){

        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public Long getTypeId() {
                return typeId;
        }

        public void setTypeId(Long typeId) {
                this.typeId = typeId;
        }

}
