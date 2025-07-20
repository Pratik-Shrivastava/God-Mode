package com.urgentneed.godmode.controller;

import com.urgentneed.godmode.common.model.GeneralResponse;
import com.urgentneed.godmode.common.service.GeminiService;
import com.urgentneed.godmode.common.service.FileService;
import com.urgentneed.godmode.constant.StatusCodeConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gemini")
public class GeminiController {
    private final GeminiService geminiService;
    private final FileService fileService;

    @GetMapping(
            value = "/ask",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "FAILED"),
            @ApiResponse(code = 500, message = "Exception Occurred")
    })
    @ApiOperation(value = "")
    public Object getGeminiPromptResponse(
            HttpServletRequest request,
            @NotNull @RequestParam String prompt
    ) throws Exception {

        String response = "AI chose to remain silent on your question!";

        try {
            if(prompt.isEmpty()) {
                throw new Exception("AI can't read your mind!");
            }
            response = this.geminiService.askGemini(prompt);

        } catch (Exception e) {
            log.error("An error occured:", e);
        }
        return new ResponseEntity<>(
                new GeneralResponse(
                        true,
                        StatusCodeConstant.HTTP_OK,
                        "Query has been answered!",
                        response
                ),
                HttpStatus.OK
        );
    }


    @PostMapping(
            value = "/generate-image"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "FAILED"),
            @ApiResponse(code = 500, message = "Exception Occurred")
    })
    @ApiOperation(value = "")
    public Object generate(
            @RequestParam("prompt") String prompt,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        List<String> imageNames = new ArrayList<>();
        try {
            imageNames = this.geminiService.generateImages(prompt, images);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }

        return new ResponseEntity<>(
                new GeneralResponse(
                        true,
                        StatusCodeConstant.HTTP_OK,
                        "Images has been generated!",
                        imageNames
                ),
                HttpStatus.OK
        );
    }


}
