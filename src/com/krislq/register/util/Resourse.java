package com.krislq.register.util;

import java.net.URL;

import javax.swing.ImageIcon;

public class Resourse {
    private static ClassLoader loder = ClassLoader.getSystemClassLoader();

    public static URL url_icon = loder.getResource("images/icon.png");

    public static ImageIcon app_icon = new ImageIcon(url_icon);

    private Resourse instance = null;

    private Resourse() {
        instance = this;
    }

    public Resourse getInstance() {
        if (instance == null) {
            instance = new Resourse();
        }
        return instance;
    }
}
