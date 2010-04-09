package com.versionone.integration.idea.actions;

import com.intellij.openapi.ui.Messages;
import com.versionone.common.sdk.DataLayerException;
import com.versionone.common.sdk.ValidatorException;
import com.versionone.common.sdk.Workitem;
import com.versionone.integration.idea.TasksTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextMenuActionListener implements ActionListener {

        private final Workitem item;
        private final TasksTable view;

        public ContextMenuActionListener(@NotNull Workitem item, @NotNull TasksTable view) {
            this.item = item;
            this.view = view;
        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if(command.equals(TasksTable.CONTEXT_MENU_QUICK_CLOSE)) {
                quickClose();
            } else if(command.equals(TasksTable.CONTEXT_MENU_CLOSE)) {
                close();
            } else if(command.equals(TasksTable.CONTEXT_MENU_SIGNUP)) {
                signup();
            } else {
                throw new UnsupportedOperationException("This menu action is not supported");
            }
        }

        private void signup() {
            try {
                item.signup();
                view.updateData();
            } catch(DataLayerException ex) {
                displayError(ex.getMessage());
            }
        }

        private void close() {
            // open a dialog etc
        }

        private void quickClose() {
            try {
                item.quickClose();
                view.updateData();
            } catch(DataLayerException ex) {
                displayError(ex.getMessage());
            } catch(ValidatorException ex) {
                displayError(ex.getMessage());
            }
        }

        private void displayError(String message) {
            Icon icon = Messages.getErrorIcon();
            Messages.showMessageDialog(message, "Error", icon);
        }
    }
