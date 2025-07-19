package com.urgentneed.godmode.common.service;

import com.urgentneed.godmode.config.Settings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageStorageService {

    private final Settings settings;


    public void store(String imageName, byte[] bytes) {
        Path path = Paths.get(settings.getGenImagePath() + imageName);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("I/O Error: ", e);
        }
    }

    public byte[] get(String imageName) {
        Path path = Paths.get(settings.getGenImagePath() + imageName);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("I/O Error: ", e);
            return null;
        }
    }
}