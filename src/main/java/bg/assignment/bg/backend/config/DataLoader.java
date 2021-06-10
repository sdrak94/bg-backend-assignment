package bg.assignment.bg.backend.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.assignment.bg.backend.model.BgCancelPolicy;
import bg.assignment.bg.backend.model.BgRegion;

@Configuration
public class DataLoader
{
	@Value("classpath:data/cancelpolicies.json")
	Resource cancelpoliciesResource;
	
	@Value("classpath:data/regions.json")
	Resource regionsResource;
	
	@Bean(name = "regionsMap")
	public Map<Integer, BgRegion> loadRegions() throws Exception
	{
		final Map<Integer, BgRegion> regionsMap = new HashMap<>();
		
		final ObjectMapper objectMapper = new ObjectMapper();

		try (final InputStream in = regionsResource.getInputStream())
		{
			final JsonNode regionNodes = objectMapper.readTree(in).get("regions");
			
			for (final JsonNode regionNode : regionNodes)
			{
				final BgRegion region = objectMapper.convertValue(regionNode, BgRegion.class);
				regionsMap.put(region.getRegionId(), region);
			}
			
		}
		
		return regionsMap;
	}
	
	@Bean(name = "cancelpoliciesMap")
	public Map<Integer, BgCancelPolicy> loadCancelpolicies() throws Exception
	{
		final Map<Integer, BgCancelPolicy> cancelpoliciesMap = new HashMap<>();
		
		final ObjectMapper objectMapper = new ObjectMapper();

		try (final InputStream in = cancelpoliciesResource.getInputStream())
		{
			final JsonNode regionNodes = objectMapper.readTree(in).get("cancelpolicies");
			
			for (final JsonNode regionNode : regionNodes)
			{
				final BgCancelPolicy cancelPolicy = objectMapper.convertValue(regionNode, BgCancelPolicy.class);
				cancelpoliciesMap.put(cancelPolicy.getCancelPolicyId(), cancelPolicy);
			}
			
		}
		
		return cancelpoliciesMap;
	}
}