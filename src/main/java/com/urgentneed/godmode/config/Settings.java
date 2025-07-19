package com.urgentneed.godmode.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class Settings {

	@Value("${godmode.genimage.path}")
	private String genImagePath;

}
