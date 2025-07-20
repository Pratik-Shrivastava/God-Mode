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
public class FileService {

    private final Settings settings;


    public void store(String filename, byte[] bytes) {
        Path path = Paths.get(settings.getGenImagePath() + filename);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("I/O Error: ", e);
        }
    }

    public byte[] get(String filename) {
        Path path = Paths.get(settings.getGenImagePath() + filename);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("I/O Error: ", e);
            return null;
        }
    }

    public boolean delete(String filename) {
        Path path = Paths.get(settings.getGenImagePath() + filename);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("I/O Error while deleting file: ", e);
            return false;
        }
    }

}