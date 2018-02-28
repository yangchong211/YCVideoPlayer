package org.yczbj.ycvideoplayer.ui.news.model.bean;

import java.util.List;

public class NewsCommentBean {

    /**
     * detail_no_comment : 0
     * total_number : 59
     * ban_comment : false
     * has_more : true
     * go_topic_detail : 1
     * stick_total_number : 0
     * tab_info : {"tabs":["热度","时间"],"current_tab_index":0}
     * fold_comment_count : 0
     * show_add_forum : 1
     * stable : true
     * stick_has_more : false
     * message : success
     */

    private int detail_no_comment;
    private int total_number;
    private boolean ban_comment;
    private boolean has_more;
    private int go_topic_detail;
    private int stick_total_number;
    private TabInfoBean tab_info;
    private int fold_comment_count;
    private int show_add_forum;
    private boolean stable;
    private boolean stick_has_more;
    private String message;
    private List<DataBean> data;

    public int getDetail_no_comment() {
        return detail_no_comment;
    }

    public void setDetail_no_comment(int detail_no_comment) {
        this.detail_no_comment = detail_no_comment;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public boolean isBan_comment() {
        return ban_comment;
    }

    public void setBan_comment(boolean ban_comment) {
        this.ban_comment = ban_comment;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public int getGo_topic_detail() {
        return go_topic_detail;
    }

    public void setGo_topic_detail(int go_topic_detail) {
        this.go_topic_detail = go_topic_detail;
    }

    public int getStick_total_number() {
        return stick_total_number;
    }

    public void setStick_total_number(int stick_total_number) {
        this.stick_total_number = stick_total_number;
    }

    public TabInfoBean getTab_info() {
        return tab_info;
    }

    public void setTab_info(TabInfoBean tab_info) {
        this.tab_info = tab_info;
    }

    public int getFold_comment_count() {
        return fold_comment_count;
    }

    public void setFold_comment_count(int fold_comment_count) {
        this.fold_comment_count = fold_comment_count;
    }

    public int getShow_add_forum() {
        return show_add_forum;
    }

    public void setShow_add_forum(int show_add_forum) {
        this.show_add_forum = show_add_forum;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }

    public boolean isStick_has_more() {
        return stick_has_more;
    }

    public void setStick_has_more(boolean stick_has_more) {
        this.stick_has_more = stick_has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class TabInfoBean {
        /**
         * tabs : ["热度","时间"]
         * current_tab_index : 0
         */

        private int current_tab_index;
        private List<String> tabs;

        public int getCurrent_tab_index() {
            return current_tab_index;
        }

        public void setCurrent_tab_index(int current_tab_index) {
            this.current_tab_index = current_tab_index;
        }

        public List<String> getTabs() {
            return tabs;
        }

        public void setTabs(List<String> tabs) {
            this.tabs = tabs;
        }
    }

    public static class DataBean {
        /**
         * comment : {"is_followed":0,"text":"我们的未来有希望了，","reply_count":0,"is_following":0,"reply_list":[],"user_verified":false,"is_blocking":0,"user_id":50022511998,"bury_count":0,"author_badge":[],"id":50029624449,"verified_reason":"","platform":"feifei","score":0,"user_name":"WolfRoad124180199","user_profile_image_url":"http://p1.pstatp.com/thumb/729001d88c6944f970b","user_bury":0,"user_digg":0,"is_blocked":0,"user_relation":0,"user_auth_info":"","digg_count":33,"create_time":1470145059}
         * cell_type : 1
         */

        private CommentBean comment;
        private int cell_type;

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public int getCell_type() {
            return cell_type;
        }

        public void setCell_type(int cell_type) {
            this.cell_type = cell_type;
        }

        public static class CommentBean {
            /**
             * is_followed : 0
             * text : 我们的未来有希望了，
             * reply_count : 0
             * is_following : 0
             * reply_list : []
             * user_verified : false
             * is_blocking : 0
             * user_id : 50022511998
             * bury_count : 0
             * author_badge : []
             * id : 50029624449
             * verified_reason :
             * platform : feifei
             * score : 0
             * user_name : WolfRoad124180199
             * user_profile_image_url : http://p1.pstatp.com/thumb/729001d88c6944f970b
             * user_bury : 0
             * user_digg : 0
             * is_blocked : 0
             * user_relation : 0
             * user_auth_info :
             * digg_count : 33
             * create_time : 1470145059
             */

            private int is_followed;
            private String text;
            private int reply_count;
            private int is_following;
            private boolean user_verified;
            private int is_blocking;
            private long user_id;
            private int bury_count;
            private long id;
            private String verified_reason;
            private String platform;
            //            private long score;
            private String user_name;
            private String user_profile_image_url;
            private int user_bury;
            private int user_digg;
            private int is_blocked;
            private int user_relation;
            private String user_auth_info;
            private int digg_count;
            private int create_time;
            private List<?> reply_list;
            private List<?> author_badge;

            @Override
            public boolean equals(Object o) {
                if (this == o)
                    return true;
                if (o == null || getClass() != o.getClass())
                    return false;

                CommentBean that = (CommentBean) o;

                if (create_time != that.create_time)
                    return false;
                return text.equals(that.text);
            }

            @Override
            public int hashCode() {
                int result = text.hashCode();
                result = 31 * result + create_time;
                return result;
            }

            public int getIs_followed() {
                return is_followed;
            }

            public void setIs_followed(int is_followed) {
                this.is_followed = is_followed;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getReply_count() {
                return reply_count;
            }

            public void setReply_count(int reply_count) {
                this.reply_count = reply_count;
            }

            public int getIs_following() {
                return is_following;
            }

            public void setIs_following(int is_following) {
                this.is_following = is_following;
            }

            public boolean isUser_verified() {
                return user_verified;
            }

            public void setUser_verified(boolean user_verified) {
                this.user_verified = user_verified;
            }

            public int getIs_blocking() {
                return is_blocking;
            }

            public void setIs_blocking(int is_blocking) {
                this.is_blocking = is_blocking;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public int getBury_count() {
                return bury_count;
            }

            public void setBury_count(int bury_count) {
                this.bury_count = bury_count;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getVerified_reason() {
                return verified_reason;
            }

            public void setVerified_reason(String verified_reason) {
                this.verified_reason = verified_reason;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

//            public long getScore() {
//                return score;
//            }

//            public void setScore(int score) {
//                this.score = score;
//            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_profile_image_url() {
                return user_profile_image_url;
            }

            public void setUser_profile_image_url(String user_profile_image_url) {
                this.user_profile_image_url = user_profile_image_url;
            }

            public int getUser_bury() {
                return user_bury;
            }

            public void setUser_bury(int user_bury) {
                this.user_bury = user_bury;
            }

            public int getUser_digg() {
                return user_digg;
            }

            public void setUser_digg(int user_digg) {
                this.user_digg = user_digg;
            }

            public int getIs_blocked() {
                return is_blocked;
            }

            public void setIs_blocked(int is_blocked) {
                this.is_blocked = is_blocked;
            }

            public int getUser_relation() {
                return user_relation;
            }

            public void setUser_relation(int user_relation) {
                this.user_relation = user_relation;
            }

            public String getUser_auth_info() {
                return user_auth_info;
            }

            public void setUser_auth_info(String user_auth_info) {
                this.user_auth_info = user_auth_info;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public List<?> getReply_list() {
                return reply_list;
            }

            public void setReply_list(List<?> reply_list) {
                this.reply_list = reply_list;
            }

            public List<?> getAuthor_badge() {
                return author_badge;
            }

            public void setAuthor_badge(List<?> author_badge) {
                this.author_badge = author_badge;
            }
        }
    }
}
