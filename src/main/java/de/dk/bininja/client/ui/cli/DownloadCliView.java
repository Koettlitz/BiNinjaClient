package de.dk.bininja.client.ui.cli;

import static de.dk.bininja.net.DownloadState.CANCELLED;
import static de.dk.bininja.net.DownloadState.COMPLETE;
import static de.dk.bininja.net.DownloadState.ERROR;

import de.dk.bininja.net.DownloadListener;
import de.dk.bininja.net.DownloadState;

/**
 * @author David Koettlitz
 * <br>Erstellt am 07.08.2017
 */
public class DownloadCliView implements DownloadListener {
   private DownloadState state;
   private DownloadCliViewManager manager;

   public DownloadCliView() {

   }

   @Override
   public void stateChanged(DownloadState state) {
      this.state = state;
      switch (state) {
      case INITIALIZING:
         System.out.println("Initializing download...");
         break;
      case RUNNING:
         System.out.println("Download started");
         break;
      case LOADING_FINISHED:
         System.out.println("Download fully loaded.");
         break;
      case CANCELLED:
         System.out.println("Download cancelled.");
         synchronized (this) {
            notify();
         }
         if (manager != null)
            manager.remove(this);
         break;
      case COMPLETE:
         System.out.println("Download complete.");
         synchronized (this) {
            notify();
         }
         if (manager != null)
            manager.remove(this);
         break;
      case ERROR:
         System.err.println("Error during download!");
         synchronized (this) {
            notify();
         }
         if (manager != null)
            manager.remove(this);
         break;
      }
   }

   public void waitFor() throws InterruptedException {
      if (state == CANCELLED || state == ERROR || state == COMPLETE)
         return;

      synchronized (this) {
         wait();
      }
   }

   @Override
   public void loadProgress(double progress, long receivedBytes, long total) {

   }

   @Override
   public void writeProgress(double progress, long writtenBytes, long total) {

   }

   public void setManager(DownloadCliViewManager manager) {
      this.manager = manager;
   }

}
