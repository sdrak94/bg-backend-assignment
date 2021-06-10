package bg.assignment.bg.backend.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.model.BgRegion;


@Service
@Scope("singleton")
public class RegionManager
{
	@Autowired
	@Qualifier("regionsMap")
	private Map<Integer, BgRegion> regionsMap;

	public BgRegion getRegionById(final int regionId)
	{
		return regionsMap.get(Integer.valueOf(regionId));
	}
}
