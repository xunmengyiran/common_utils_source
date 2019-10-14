package com.xunmengyiran;

import java.text.SimpleDateFormat;

public interface Constants {

    interface Date_Format{
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MMddHHMMssSSS");
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");

    }

    double EARTH_RADIUS = 6371393; // 平均半径,单位：m；不是赤道半径。赤道为6378左右
}
