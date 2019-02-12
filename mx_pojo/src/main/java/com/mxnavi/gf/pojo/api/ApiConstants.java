package com.mxnavi.gf.pojo.api;

/**
 * 描述 ：网络请求常量类
 *
 * @author Mark
 * @date 2018.08.08
 */
public class ApiConstants {

    public static class Url {

        /**
         * 歌曲排行
         * 请求方式：GET
         */
        public static final String TOP = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?g_tk=5381&uin=0&format=json&inCharset=utf-8&outCharset=utf-8¬ice=0&platform=h5&needNewCode=1&tpl=3&page=detail&type=top&topid=27&_=1519963122923";

        /**
         * 随机推荐
         * 请求方式：GET
         */
        public static final String RECOMMEND = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg?g_tk=5381&uin=0&format=json&inCharset=utf-8&outCharset=utf-8¬ice=0&platform=h5&needNewCode=1&tpl=3&page=detail&type=top&topid=36&_=1520777874472";



    }
}
