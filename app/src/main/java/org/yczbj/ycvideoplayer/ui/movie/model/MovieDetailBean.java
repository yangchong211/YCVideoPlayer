package org.yczbj.ycvideoplayer.ui.movie.model;

import java.util.List;

/**
 * Created by yc on 2018/3/2.
 */

public class MovieDetailBean {


    /**
     * msg : 成功
     * ret
     * code : 200
     */

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
        /**
         * couponNum : 0
         * HDURL : http://movie.vods1.cnlive.com/3/vod/2017/0606/3_e4871e503816456eb5ae84758d70d0dd/ff8080815bf6b453015c7c7e5452120b_1500.m3u8
         * downloadURL :
         * description : 21世纪末，人类的科技水平已高度发达，克隆人技术和宇宙航行早已实现，不再是梦想。与此同时，许多科学家仍孜孜不倦追索着人类起源的秘密与真相。通过对许多古老文明的考察与对比，科学家伊丽莎白·肖和查理·赫洛维发现，人类可能是来自一个遥远星系的外星人创造的。在Weyland公司资助下，他们乘坐维克丝所掌管的宇宙飞船普罗米修斯号前往那颗未知的星球。
         * pic : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2018/02/16/1518786460711038815.jpg
         * title : 普罗米修斯
         * kuaiKan : false
         * smoothURL : http://movie.vods1.cnlive.com/3/vod/2017/0606/3_e4871e503816456eb5ae84758d70d0dd/ff8080815bf6b453015c7c7e5452120b_400.m3u8
         * duration : 02:03:46
         * score : 0
         * ticketContent : {"score":"0分","name":"三块广告牌","poster_url":"https://msdbpic-cos.wepiao.com/msdb/movie/p/img_cover/small/c61cf6e3e7abaa381638d0aa552ad4686703.jpg","id":"238404","version":"2D","url":"http://m.wepiao.com/movies/238404"}
         * airTime : 2012
         * fastDataId :
         * ultraClearURL : http://movie.vods1.cnlive.com/3/vod/2017/0606/3_e4871e503816456eb5ae84758d70d0dd/ff8080815bf6b453015c7c7e5452120b_1500.m3u8
         * director : 雷德利·斯科特
         * videoType : 冒险,科幻,惊悚
         * htmlURL : http://m.svipmovie.com/#/moviedetails/e4871e503816456eb5ae84758d70d0dd
         * list
         * SDURL : http://movie.vods1.cnlive.com/3/vod/2017/0606/3_e4871e503816456eb5ae84758d70d0dd/ff8080815bf6b453015c7c7e5452120b_800.m3u8
         * actors : 劳米·拉佩斯 / 迈克尔·法斯宾德 / 查理兹·塞隆 / 伊德里斯·艾尔巴 / 盖·皮尔斯
         * canWatchFlag : false
         * adv : {"imgURL":"http://phonemovie.ks3-cn-beijing.ksyun.com/image/2018/03/02/1519955693461058585.jpg","dataId":"ff80808161d4dd470161e46be6496eaf","htmlURL":"http://www.iqiyi.com/v_19rrfg41d0.html","shareURL":"","title":"白夜侠"}
         * collectionFalg : false
         * lastPlayTime : 4
         * region : 欧美
         * vipFlag : false
         */

        private int couponNum;
        private String HDURL;
        private String downloadURL;
        private String description;
        private String pic;
        private String title;
        private String kuaiKan;
        private String smoothURL;
        private String duration;
        private int score;
        private TicketContentBean ticketContent;
        private int airTime;
        private String fastDataId;
        private String ultraClearURL;
        private String director;
        private String videoType;
        private String htmlURL;
        private String SDURL;
        private String actors;
        private String canWatchFlag;
        private AdvBean adv;
        private String collectionFalg;
        private String lastPlayTime;
        private String region;
        private String vipFlag;
        private List<ListBean> list;

        public int getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(int couponNum) {
            this.couponNum = couponNum;
        }

        public String getHDURL() {
            return HDURL;
        }

        public void setHDURL(String HDURL) {
            this.HDURL = HDURL;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public void setDownloadURL(String downloadURL) {
            this.downloadURL = downloadURL;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getKuaiKan() {
            return kuaiKan;
        }

        public void setKuaiKan(String kuaiKan) {
            this.kuaiKan = kuaiKan;
        }

        public String getSmoothURL() {
            return smoothURL;
        }

        public void setSmoothURL(String smoothURL) {
            this.smoothURL = smoothURL;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public TicketContentBean getTicketContent() {
            return ticketContent;
        }

        public void setTicketContent(TicketContentBean ticketContent) {
            this.ticketContent = ticketContent;
        }

        public int getAirTime() {
            return airTime;
        }

        public void setAirTime(int airTime) {
            this.airTime = airTime;
        }

        public String getFastDataId() {
            return fastDataId;
        }

        public void setFastDataId(String fastDataId) {
            this.fastDataId = fastDataId;
        }

        public String getUltraClearURL() {
            return ultraClearURL;
        }

        public void setUltraClearURL(String ultraClearURL) {
            this.ultraClearURL = ultraClearURL;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getHtmlURL() {
            return htmlURL;
        }

        public void setHtmlURL(String htmlURL) {
            this.htmlURL = htmlURL;
        }

        public String getSDURL() {
            return SDURL;
        }

        public void setSDURL(String SDURL) {
            this.SDURL = SDURL;
        }

        public String getActors() {
            return actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getCanWatchFlag() {
            return canWatchFlag;
        }

        public void setCanWatchFlag(String canWatchFlag) {
            this.canWatchFlag = canWatchFlag;
        }

        public AdvBean getAdv() {
            return adv;
        }

        public void setAdv(AdvBean adv) {
            this.adv = adv;
        }

        public String getCollectionFalg() {
            return collectionFalg;
        }

        public void setCollectionFalg(String collectionFalg) {
            this.collectionFalg = collectionFalg;
        }

        public String getLastPlayTime() {
            return lastPlayTime;
        }

        public void setLastPlayTime(String lastPlayTime) {
            this.lastPlayTime = lastPlayTime;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getVipFlag() {
            return vipFlag;
        }

        public void setVipFlag(String vipFlag) {
            this.vipFlag = vipFlag;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class TicketContentBean {
            /**
             * score : 0分
             * name : 三块广告牌
             * poster_url : https://msdbpic-cos.wepiao.com/msdb/movie/p/img_cover/small/c61cf6e3e7abaa381638d0aa552ad4686703.jpg
             * id : 238404
             * version : 2D
             * url : http://m.wepiao.com/movies/238404
             */

            private String score;
            private String name;
            private String poster_url;
            private String id;
            private String version;
            private String url;

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoster_url() {
                return poster_url;
            }

            public void setPoster_url(String poster_url) {
                this.poster_url = poster_url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class AdvBean {
            /**
             * imgURL : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2018/03/02/1519955693461058585.jpg
             * dataId : ff80808161d4dd470161e46be6496eaf
             * htmlURL : http://www.iqiyi.com/v_19rrfg41d0.html
             * shareURL :
             * title : 白夜侠
             */

            private String imgURL;
            private String dataId;
            private String htmlURL;
            private String shareURL;
            private String title;

            public String getImgURL() {
                return imgURL;
            }

            public void setImgURL(String imgURL) {
                this.imgURL = imgURL;
            }

            public String getDataId() {
                return dataId;
            }

            public void setDataId(String dataId) {
                this.dataId = dataId;
            }

            public String getHtmlURL() {
                return htmlURL;
            }

            public void setHtmlURL(String htmlURL) {
                this.htmlURL = htmlURL;
            }

            public String getShareURL() {
                return shareURL;
            }

            public void setShareURL(String shareURL) {
                this.shareURL = shareURL;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ListBean {
            /**
             * showType : vertical
             * childList
             * title : 猜你喜欢
             */

            private String showType;
            private String title;
            private List<ChildListBean> childList;

            public String getShowType() {
                return showType;
            }

            public void setShowType(String showType) {
                this.showType = showType;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ChildListBean> getChildList() {
                return childList;
            }

            public void setChildList(List<ChildListBean> childList) {
                this.childList = childList;
            }

            public static class ChildListBean {
                /**
                 * airTime : 2015
                 * duration : 01:52:15
                 * loadtype : video
                 * score : 0
                 * angleIcon : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/05/09/1494296614609066838.png
                 * dataId : d459e58100af4e5aa6d6d09070442878
                 * description : 未来世界，水资源短缺引发了连绵的战争。人们相互厮杀，争夺有限的资源，地球变成了血腥十足的杀戮死战场。面容恐怖的不死乔在戈壁山谷建立了难以撼动的强大武装王国，他手下的战郎驾驶装备尖端武器的战车四下抢掠，杀伐无度，甚至将自己的孩子打造成战争机器。在最近一次行动中，不死乔的得力战将弗瑞奥萨（查理兹·塞隆 Charlize Theron 饰）带着生育者们叛逃，这令不死乔恼羞成怒，发誓要追回生育者。经历了激烈的追逐战和摧毁力极强的沙尘暴，弗瑞奥萨和作为血主的麦克斯（汤姆·哈迪 Tom Hardy 饰）被迫上路，而身后不仅有不死乔的追兵，还有汽油镇、子弹农场的重兵追逐。
                 　　末世战争，全面爆发……
                 * loadURL : http://api.svipmovie.com/front/videoDetailApi/videoDetail.do?mediaId=d459e58100af4e5aa6d6d09070442878
                 * shareURL : http://h5.svipmovie.com/bqdy/d459e58100af4e5aa6d6d09070442878.shtml?fromTo=shoujimovie
                 * pic : http://phonemovie.ks3-cn-beijing.ksyun.com/image/2017/06/30/1498819818604078748.jpg
                 * title : 疯狂的麦克斯4
                 */

                private int airTime;
                private String duration;
                private String loadtype;
                private int score;
                private String angleIcon;
                private String dataId;
                private String description;
                private String loadURL;
                private String shareURL;
                private String pic;
                private String title;

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

                public String getLoadtype() {
                    return loadtype;
                }

                public void setLoadtype(String loadtype) {
                    this.loadtype = loadtype;
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
            }
        }
    }
}
