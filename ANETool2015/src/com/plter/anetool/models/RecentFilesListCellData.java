package com.plter.anetool.models;

import java.io.File;

/**
 * Created by plter on 11/23/15.
 */
public class RecentFilesListCellData {


    private File file;

    public RecentFilesListCellData(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        assert obj instanceof RecentFilesListCellData;
        return ((RecentFilesListCellData) obj).getFile().getAbsolutePath().equals(getFile().getAbsolutePath());
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getAbsolutePath() {
        return getFile().getAbsolutePath();
    }

    public String getName() {
        return getFile().getName();
    }
}
