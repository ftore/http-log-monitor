package com.example.httplogmonitor.monitor;

import java.io.File;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Whatches the specified file for changes in separate thread.
 *
 * @Author arakhimoff@gmail.com
 */
public class FileWatcher extends Thread {

    /**
     * File to watch. Absolute location of the should be specified.
     */
    private final File file;

    private AtomicBoolean stop = new AtomicBoolean(false);

    public FileWatcher(File file) {
        this.file = file;
    }

    /**
     * Check whether the thread that watches a file is stopped or not.
     *
     * @return boolean
     */
    public boolean isStopped() { return stop.get(); }

    /**
     * Sets the flag of the thread to be stopped.
     */
    public void stopThread() { stop.set(true); }

    /**
     * On change to the file, call this method.
     */
    public void doOnChange() {
        // Do whatever action you want here
    }

    @Override
    public void run() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = file.toPath().getParent();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (!isStopped()) {
                WatchKey key;
                try { key = watcher.poll(25, TimeUnit.MILLISECONDS); }
                catch (InterruptedException e) { return; }
                if (key == null) { Thread.yield(); continue; }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
                            && filename.toString().equals(file.getName())) {
                        doOnChange();
                    }
                    boolean valid = key.reset();
                    if (!valid) { break; }
                }
                Thread.yield();
            }
        } catch (Throwable e) {
            // Log or rethrow the error
        }
    }
}
