package me.zuichu.sa.picker.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class FileFolder implements Serializable {

    public String name;  //当前文件夹的名字
    public String path;  //当前文件夹的路径
    public FileItem cover;   //当前文件夹需要要显示的缩略图，默认为最近的一次视频
    public ArrayList<FileItem> files;  //当前文件夹下所有文件的集合

    /**
     * 只要文件夹的路径和名字相同，就认为是相同的文件夹
     */
    @Override
    public boolean equals(Object o) {
        try {
            FileFolder other = (FileFolder) o;
            return this.path.equalsIgnoreCase(other.path) && this.name.equalsIgnoreCase(other.name);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}

