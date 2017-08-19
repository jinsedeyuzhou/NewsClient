package com.study.newsclient.widget.imageloader;


public class LoaderFactory {
    private static ILoader loader;

    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (LoaderFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }
}
