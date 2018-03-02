package org.yczbj.ycvideoplayer.ui.movie.model;

import java.util.List;

/**
 * Created by yc on 2018/3/1.
 */

public class MovieBean {

    private String msg;
    private RetBean ret;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RetBean getRet() {
        return ret;
    }

    public void setRet(RetBean ret) {
        this.ret = ret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class RetBean {
        private List<HotSearchListBean> hotSearchList;
        private List<ListBean> list;

        public List<HotSearchListBean> getHotSearchList() {
            return hotSearchList;
        }

        public void setHotSearchList(List<HotSearchListBean> hotSearchList) {
            this.hotSearchList = hotSearchList;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class HotSearchListBean {
            /**
             * refCounter : 1
             * cnname : xingjichuanyue
             * siteId : 1
             * simplename : xjcy
             * id : ff8080815a5f91db015a68a763b750d5
             * tagName : 星际穿越
             * createdtime : 2017-02-23 09:48:04
             * enname :
             */

            private int refCounter;
            private String cnname;
            private String siteId;
            private String simplename;
            private String id;
            private String tagName;
            private String createdtime;
            private String enname;

            public int getRefCounter() {
                return refCounter;
            }

            public void setRefCounter(int refCounter) {
                this.refCounter = refCounter;
            }

            public String getCnname() {
                return cnname;
            }

            public void setCnname(String cnname) {
                this.cnname = cnname;
            }

            public String getSiteId() {
                return siteId;
            }

            public void setSiteId(String siteId) {
                this.siteId = siteId;
            }

            public String getSimplename() {
                return simplename;
            }

            public void setSimplename(String simplename) {
                this.simplename = simplename;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getCreatedtime() {
                return createdtime;
            }

            public void setCreatedtime(String createdtime) {
                this.createdtime = createdtime;
            }

            public String getEnname() {
                return enname;
            }

            public void setEnname(String enname) {
                this.enname = enname;
            }
        }

        public static class ListBean {

            private String showStyle;
            private String loadType;
            private String changeOpenFlag;
            private int line;
            private String showType;
            private String moreURL;
            private String title;
            private String bigPicShowFlag;
            private List<ChildListBean> childList;

            public String getShowStyle() {
                return showStyle;
            }

            public void setShowStyle(String showStyle) {
                this.showStyle = showStyle;
            }

            public String getLoadType() {
                return loadType;
            }

            public void setLoadType(String loadType) {
                this.loadType = loadType;
            }

            public String getChangeOpenFlag() {
                return changeOpenFlag;
            }

            public void setChangeOpenFlag(String changeOpenFlag) {
                this.changeOpenFlag = changeOpenFlag;
            }

            public int getLine() {
                return line;
            }

            public void setLine(int line) {
                this.line = line;
            }

            public String getShowType() {
                return showType;
            }

            public void setShowType(String showType) {
                this.showType = showType;
            }

            public String getMoreURL() {
                return moreURL;
            }

            public void setMoreURL(String moreURL) {
                this.moreURL = moreURL;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBigPicShowFlag() {
                return bigPicShowFlag;
            }

            public void setBigPicShowFlag(String bigPicShowFlag) {
                this.bigPicShowFlag = bigPicShowFlag;
            }

            public List<ChildListBean> getChildList() {
                return childList;
            }

            public void setChildList(List<ChildListBean> childList) {
                this.childList = childList;
            }

            public static class ChildListBean {
                /**
                 * airTime : 2012
                 * duration : 02:03:46
                 * loadType : video
                 * score : 0
                 * angleIcon : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/05/09/1494296614609066838.png
                 * dataId : e4871e503816456eb5ae84758d70d0dd
                 * description : 21世纪末，人类的科技水平已高度发达，克隆人技术和宇宙航行早已实现，不再是梦想。与此同时，许多科学家仍孜孜不倦追索着人类起源的秘密与真相。通过对许多古老文明的考察与对比，科学家伊丽莎白·肖和查理·赫洛维发现，人类可能是来自一个遥远星系的外星人创造的。在Weyland公司资助下，他们乘坐维克丝所掌管的宇宙飞船普罗米修斯号前往那颗未知的星球。
                 * loadURL : http://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=e4871e503816456eb5ae84758d70d0dd
                 * shareURL : http://m.svipmovie.com/#/moviedetails/e4871e503816456eb5ae84758d70d0dd
                 * pic : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/06/06/1496743562076010888.jpg
                 * title : 恐怖异形起源之谜
                 * roomId :
                 */

                private int airTime;
                private String duration;
                private String loadType;
                private int score;
                private String angleIcon;
                private String dataId;
                private String description;
                private String loadURL;
                private String shareURL;
                private String pic;
                private String title;
                private String roomId;

                public int getAirTime() {
                    return airTime;
                }

                public void setAirTime(int airTime) {
                    this.airTime = airTime;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getLoadType() {
                    return loadType;
                }

                public void setLoadType(String loadType) {
                    this.loadType = loadType;
                }

                public int getScore() {
                    return score;
                }

                public void setScore(int score) {
                    this.score = score;
                }

                public String getAngleIcon() {
                    return angleIcon;
                }

                public void setAngleIcon(String angleIcon) {
                    this.angleIcon = angleIcon;
                }

                public String getDataId() {
                    return dataId;
                }

                public void setDataId(String dataId) {
                    this.dataId = dataId;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getLoadURL() {
                    return loadURL;
                }

                public void setLoadURL(String loadURL) {
                    this.loadURL = loadURL;
                }

                public String getShareURL() {
                    return shareURL;
                }

                public void setShareURL(String shareURL) {
                    this.shareURL = shareURL;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getRoomId() {
                    return roomId;
                }

                public void setRoomId(String roomId) {
                    this.roomId = roomId;
                }
            }
        }
    }
}
