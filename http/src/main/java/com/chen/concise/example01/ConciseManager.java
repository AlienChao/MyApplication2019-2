package com.chen.concise.example01;


import android.content.Context;

/**
 * The core of the jar package
 * Created by chenyongan
 */
public class ConciseManager {
    //Core control class
    private static ConciseManager conciseManager = null;
    //config
    private static Builders builders = null;

    /**
     * Initialize the
     */
    public static ConciseManager getInstance() {
        synchronized (ConciseManager.class) {
            if (conciseManager == null) {
                conciseManager = new ConciseManager();
            }
            return conciseManager;
        }
    }

    public void init() {

    }

    public static Builders getBuilders() {
        return builders;
    }

    /**
     * Initialize the
     *
     * param builders config
     */
    public void init(Builders builders) {
        this.builders = builders;
    }

    public static final class Builders {
        private String appname;
        private String appversion;
        private String token;
        private String device;
        private Context context;

        public Builders() {

        }

        public Context getContext() {
            return context;
        }



        public String getAppname() {
            return appname;
        }

        public String getAppversion() {
            return appversion;
        }

        public String getToken() {
            return token;
        }

        public String getDevice() {
            return device;
        }

        public Builders appname(String appname) {
            this.appname = appname;
            return this;
        }


        public Builders context(Context context) {
            this.context = context;
            return  this;
        }

        public Builders appversion(String appversion) {
            this.appversion = appversion;
            return this;
        }

        public Builders token(String token) {
            this.token = token;
            return this;
        }

        public Builders device(String device) {
            this.device = device;
            return this;
        }
    }

}
