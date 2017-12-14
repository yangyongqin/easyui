/*
 * Created on 2005-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ljian
 */
public class FormulaTabDirector {
    private List builders = new ArrayList();

    public FormulaTabDirector() {
    }

    public void build() {
        for (int i = 0; i < builders.size(); i++) {
            ((TabBuilder)builders.get(i)).addUIControls();
            ((TabBuilder)builders.get(i)).initalize();
        }
    }

    public void addBuilder(TabBuilder builder) {
        builders.add(builder);
    }

    public void addBuilder(int i, TabBuilder builder) {
        builders.add(i, builder);
    }

    public void setBuilder(int i, TabBuilder builder) {
        builders.set(i, builder);
    }

    public void removeBuilder(int index) {
        builders.remove(index);
    }

    public List getBuilders() {
        return builders;
    }

    public boolean isBuilderExist(String strTabBuilderName) {
        return getBuilderIndex(strTabBuilderName) >= 0;
    }

    public int getBuilderIndex(String strTabBuilderName) {
        for (int i = 0; i < builders.size(); i++) {
            TabBuilder curbuilder = (TabBuilder)builders.get(i);
            if (curbuilder.getTabName() != null
                    && curbuilder.getTabName().equals(strTabBuilderName)) {
                return i;
            }
        }
        return -1;
    }
}
