package com.urgentneed.godmode.common.service;

import com.google.common.collect.ImmutableList;
import com.google.genai.Client;
import com.google.genai.types.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

import static com.urgentneed.godmode.constant.GeminiModelConstant.GEMINI_2_5_FLASH;
import static com.urgentneed.godmode.constant.GeminiModelConstant.GEMINI_IMAGE_GENERATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {
    private final Client client;
    private final ImageStorageService imageStorageService;

    public String askGemini(String prompt) {
        GenerateContentResponse response =
                client.models.generateContent(
                        GEMINI_2_5_FLASH,
                        prompt,
                        null
                );

       return response.text();
    }

    public List<String> generateImages(
            String prompt,
            @Nullable List<MultipartFile> images
    ) {
        List<Part> parts = new ArrayList<>();
        parts.add(Part.fromText(prompt)); // Add prompt

        if (images != null) {
            List<Part> imageParts = images.stream()
                    .map(image -> {
                        try {
                            return Part.fromBytes(
                                    image.getBytes(),
                                    image.getContentType()
                            );
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
            parts.addAll(imageParts); // Add images
        }

        Content content = Content.builder().parts(parts).build();
        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseModalities(List.of("Text", "Image"))
                .build();

        GenerateContentResponse response = this
                .client
                .models
                .generateContent(
                        GEMINI_IMAGE_GENERATION,
                        content, config
                );

        List<Image> generatedImages = getImages(response);

        generatedImages.forEach(image -> this.imageStorageService.store(image.imageName(), image.imageBytes()));
        return generatedImages.stream().map(Image::imageName).toList();

    }

    private List<Image> getImages(GenerateContentResponse response) {
        ImmutableList<Part> responseParts = response.parts();
        if (responseParts == null || responseParts.isEmpty()) {
            return Collections.emptyList();
        }
        return responseParts
                .stream()
                .map(Part::inlineData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(inlineData -> inlineData.data().isPresent())
                .map(inlineData -> {
                    MimeType mimeType = MimeType.valueOf(inlineData.mimeType().get()); // imageMimeType
                    return new Image(
                            "%s.%s".formatted(UUID.randomUUID().toString(), mimeType.getSubtype()),
                            inlineData.data().get(), // imageBytes
                            mimeType.toString());
                })
                .toList();
    }

    /**
     * Record class representing a generated image with its name, binary data, and MIME type.
     *
     * @param imageName The name of the image file
     * @param imageBytes The binary data of the image
     * @param mimeType The MIME type of the image
     */
    record Image(String imageName, byte[] imageBytes, String mimeType) {}
}
