package ua.birthdays.app.models.audio;

import javazoom.jl.player.advanced.AdvancedPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * The MP3Player class implements the {@link Audio} interface and provides functionality for playing audio in MP3 format.
 */
public class MP3Player implements Audio {
    private static final Logger logger = LogManager.getLogger(MP3Player.class.getName());

    private final String filePath;
    private AdvancedPlayer player;
    private volatile boolean isPlaying = false;

    public MP3Player(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void play() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            player = new AdvancedPlayer(bis);
            isPlaying = true;

            // Create new thread for playing sound
            Thread musicThread = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    logger.error("Create thread isn't successful!", e);
                }
                isPlaying = false;
            });
            musicThread.start();
        } catch (Exception e) {
            logger.error("Create stream isn't successful!", e);
        }
    }

    @Override
    public void playInBackground() {
        Thread musicThread = new Thread(() -> {
            try {
                isPlaying = true;
                while (isPlaying) {
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
                    player = new AdvancedPlayer(bis);
                    player.play();
                    bis.close();
                }
            } catch (Exception e) {
                logger.error("Create stream isn't successful!", e);
            }
        });
        musicThread.start();
    }

    @Override
    public void stop() {
        isPlaying = false;
        if (player != null) {
            player.close();
        }
    }
}