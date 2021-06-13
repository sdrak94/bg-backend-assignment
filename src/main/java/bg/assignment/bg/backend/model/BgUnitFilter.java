package bg.assignment.bg.backend.model;

import java.util.EnumSet;
import java.util.function.Predicate;

import bg.assignment.bg.backend.model.enums.ESearchStrategy;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitList;

public class BgUnitFilter implements Predicate<BgUnit>
{
	final EnumSet<ESearchStrategy> _searchStrategies;
	
	private final RequestUnitList _unitListRequest;

	private final String[] _containAnyTitles;
	private final int[] _containAnyRegionIds;
	private final int[] _containAnyPolicyIds;
	
	public BgUnitFilter(final RequestUnitList unitListRequest)
	{
		_unitListRequest = unitListRequest;
		
		_searchStrategies = unitListRequest.getSearchStrategies();
		
		_containAnyTitles    = unitListRequest.getFilterTitles();
		_containAnyRegionIds = unitListRequest.getFilterRegionIds();
		_containAnyPolicyIds = unitListRequest.getFilterPolicyIds();
	}
	
	@Override
	public boolean test(final BgUnit bgUnit)
	{
		//allows combined search on a single filter

		
		//filter by title
		if (_searchStrategies.contains(ESearchStrategy.TITLE))
		{
			boolean found = false;
			final String bgTitle = bgUnit.getTitle();
			for (final String anyTitle : _containAnyTitles)
			{
				if (bgTitle.contains(anyTitle))
				{
					found = true;
					break;
				}
			}
			
			if (!found)
				return false;
		}

		//filter by region Id
		if (_searchStrategies.contains(ESearchStrategy.REGION))
		{
			boolean found = false;
			final int regionId = bgUnit.getRegionId();
			for (final int anyRegionId : _containAnyRegionIds)
			{
				if (anyRegionId == regionId)
				{
					found = true;
					break;
				}
			}
			
			if (!found)
				return false;
		}
		

		//filter by policy Id
		if (_searchStrategies.contains(ESearchStrategy.CANCELPOLICY))
		{
			boolean found = false;
			final int policyId = bgUnit.getCancelPolicyId();
			for (final int anyPolicyId : _containAnyPolicyIds)
			{
				if (anyPolicyId == policyId)
				{
					found = true;
					break;
				}
			}
			
			if (!found)
				return false;
		}
		
		
		//passed all validations
		return true;
	}

}
