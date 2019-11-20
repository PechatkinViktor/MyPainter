package com.pechatkin.sbt.mypainter.models;

import com.pechatkin.sbt.mypainter.R;

public enum Types {

    RECT(R.string.draw_type_name_rect),
    LINE(R.string.draw_type_name_line),
    PATH(R.string.draw_type_name_path),
    POINT(R.string.draw_type_name_point),
    MULTITOUCH(R.string.draw_type_name_multitouch);



    public final int mTypeName;

    Types(int typeNameRes){
        mTypeName = typeNameRes;
    }
}
