/*(c) Copyright 2010, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.integration.idea;

import com.versionone.common.sdk.Workitem;
import com.versionone.common.sdk.EntityType;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

/**
 * Render for workitems in tree. 
 */
public class WorkItemTreeTableCellRenderer extends DefaultTreeCellRenderer {
    private final Map<EntityType, Icon> icons;

    public WorkItemTreeTableCellRenderer() {
        icons = new HashMap<EntityType, Icon>();

        icons.put(EntityType.Defect, IconLoader.getIcon("/defect.gif"));
        icons.put(EntityType.Story, IconLoader.getIcon("/story.gif"));
        icons.put(EntityType.Test, IconLoader.getIcon("/test.gif"));
        icons.put(EntityType.Task, IconLoader.getIcon("/task.gif"));
    }

    /**
     * Sets specify icon for tree.
     * @param icon - icon for nodes.
     */
    private void setWorkitemIcon(Icon icon) {
        setIcon(icon);
        setOpenIcon(icon);
        setClosedIcon(icon);
        setLeafIcon(icon);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
						  boolean sel,
						  boolean expanded,
						  boolean leaf, int row,
						  boolean hasFocus) {

        Object newValue = "***ERROR***";
        if (value instanceof Workitem) {
            newValue = ((Workitem)value).getProperty("Name");
            setWorkitemIcon(icons.get(((Workitem)value).getType()));
        }        
        return super.getTreeCellRendererComponent(tree, newValue, sel, expanded, leaf, row, hasFocus);
    }
}
