package com.free.banner.uitls;

import android.os.Environment;
import java.io.File;

/**
 * 常量配置
 * @author lake
 */
public class Constants {
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator + "HBanner";
    public static final String DEFAULT_DOWNLOAD_DIR = "data/data/com.free.banner" + File.separator + "_cache";
    public static final String save_path = "/Hbanner";

}
