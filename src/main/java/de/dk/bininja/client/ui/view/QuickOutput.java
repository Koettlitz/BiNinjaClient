package de.dk.bininja.client.ui.view;

public interface QuickOutput {
   public void show(String format, Object... args);
   public void showError(String errorMsg, Object... args);
   public void alert(String format, Object... args);
   public void alertError(String errorMsg, Object... args);
}