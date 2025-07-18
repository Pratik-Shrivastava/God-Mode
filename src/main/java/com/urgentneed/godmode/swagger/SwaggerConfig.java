package com.urgentneed.godmode.swagger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class SwaggerConfig  {

	//Add fields to be added to global headers in swagger
	List<String> keyLst=List.of("apikey"/*,"userId","role"*/);

	@Bean
	public OpenAPI getOpenAPI() {
		OpenAPI openApi=new OpenAPI()
				.components(new Components().securitySchemes(getSecuritySchemaMap()))
				.info(getInfo())
				.addSecurityItem(getSecurityRequirements());

		return openApi;
	}
	public SecurityRequirement getSecurityRequirements(){
		SecurityRequirement securityRequirement=new SecurityRequirement();
		for (String key:keyLst){
			securityRequirement.addList(key);
		}
		return securityRequirement;
	}

	public Info getInfo(){
		return new Info().title("QuickStart Application")
				.description("An application to quick start spring boot 3.")
				.version("1.0");
	}


	public Map<String,SecurityScheme> getSecuritySchemaMap(){
		Map<String,SecurityScheme> securitySchemeMap=new HashMap<>();
		for (String key:keyLst){
			SecurityScheme securityScheme=generateSecurityScheme("APIKEY",key,"APIKEY");
			securitySchemeMap.put(key, securityScheme);
		}
		return securitySchemeMap;
	}

	public SecurityScheme generateSecurityScheme(String scheme,String name,String bearerFormat){

		return new SecurityScheme()
				.type(SecurityScheme.Type.APIKEY)
				.scheme(scheme)
				.name(name)
				.bearerFormat(bearerFormat)
				.in(HEADER);
	}



}
