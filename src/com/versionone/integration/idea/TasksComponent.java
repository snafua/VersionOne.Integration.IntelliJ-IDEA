/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.integration.idea;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.peer.PeerFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.UIUtil;
import static com.versionone.integration.idea.TasksProperties.*;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;

public class TasksComponent implements ProjectComponent {
    private final TasksProperties[] tasksColumnData = {Title, ID, Parent, DetailEstimeate, Done, Effort, ToDo, Status};

    private static final Logger LOG = Logger.getLogger(TasksComponent.class);
    @NonNls
    public static final String TOOL_WINDOW_ID = "V1Integration";


    private final Project project;

    private ToolWindow toolWindow;
    private JPanel contentPanel;
    private Content content;
    private final WorkspaceSettings cfg = WorkspaceSettings.getInstance();


    public TasksComponent(Project project) {
        this.project = project;
    }

    public void projectOpened() {
        String ideaVersion = ApplicationInfo.getInstance().getMajorVersion();
        System.out.println("IDEA version = " + ideaVersion);
        initToolWindow();
    }

    public void projectClosed() {
        unregisterToolWindow();
    }

    public void initComponent() {
        // empty
    }

    public void disposeComponent() {
        // empty
    }

    public String getComponentName() {
        return "V1.ToolWindow";
    }

    private void initToolWindow() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        contentPanel = createContentPanel();

        ActionGroup actions = (ActionGroup) ActionManager.getInstance().getAction("V1.ToolWindow");
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("V1.ToolWindow", actions, false);
        contentPanel.add(toolbar.getComponent(), BorderLayout.LINE_START);

        toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, false, ToolWindowAnchor.BOTTOM);
        ContentFactory contentFactory;
//        contentFactory = ContentFactory.SERVICE.getInstance();
        contentFactory = PeerFactory.getInstance().getContentFactory();
        content = contentFactory.createContent(contentPanel, cfg.projectName, false);
        toolWindow.getContentManager().addContent(content);
    }

    public void updateDisplayName() {
        if (content != null) {
            content.setDisplayName(cfg.projectName);
        }
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(UIUtil.getTreeTextBackground());
        JTable table = creatingTable();
        //panel.add(creatingTable());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        return panel;
    }


    private JTable creatingTable() {

        JTable table = new TasksTable(new HorizontalTableModel(tasksColumnData));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


//        JComboBox cb =new JComboBox(new DataLayer().getAllStatuses());
//            ItemListener listener = new ItemListener() {
//
//                public void itemStateChanged(ItemEvent e) {
//                    //DataLayer.getInstance().
//
//                    if (e.getStateChange() == ItemEvent.SELECTED) {
//                        System.out.println(e.getItem()+ " - "+e.paramString());
//                    }
//                }
//            };
//        cb.addItemListener(listener);

        //JScrollPane scrollPane = new JScrollPane(table);
        //table.setFillsViewportHeight(true);
        //table.getColumn(table.getColumnName(7)).setCellEditor(new DefaultCellEditor(cb));

        //container.setLayout();
        //container.add(table.getTableHeader(), BorderLayout.PAGE_START);
        //container.add(table, BorderLayout.CENTER);


        return table;
    }


    private void unregisterToolWindow() {
        ToolWindowManager.getInstance(project).unregisterToolWindow(TOOL_WINDOW_ID);
    }

    /**
     * Temporary method for testing purposes.
     */
    public static void main(String[] args) {
        TasksComponent plugin = new TasksComponent(null);
        JPanel panel = plugin.createContentPanel();
        JFrame frame = new JFrame("IDEA V1 Plugin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 100));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
