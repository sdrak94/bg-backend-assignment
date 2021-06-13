package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import bg.assignment.bg.backend.model.BgUnit;
import bg.assignment.bg.backend.model.UnitComparator;
import bg.assignment.bg.backend.model.compare.EUnitComparator;
import bg.assignment.bg.backend.model.enums.ESearchStrategy;

public class RequestUnitList implements Serializable
{
	private EnumSet<ESearchStrategy> _searchStrategies = EnumSet.noneOf(ESearchStrategy.class);
	
	private static final long serialVersionUID = 4114083354367118270L;
	private List<EUnitComparator> _comperators;
	
	private Comparator<BgUnit> _comparator;
	
	private String[] _filterTitles;
	private int[] _filterRegionIds;
	private int[] _filterPolicyIds;
	private long _offset;
	private long _limit;
	
	public void setOrderBy(final String orderBy) //this is a hack
	{
		final String[] comparatorOrdinals = orderBy.split(",");
		final int ordinalsLen = comparatorOrdinals.length;
		if (ordinalsLen > 0) 
		{
			_comperators = new ArrayList<>(ordinalsLen);
			for (final String comparatorOrdinalStr : comparatorOrdinals)
			{
				final UnitComparator unitComparator = new UnitComparator(comparatorOrdinalStr);
				_comparator = _comparator == null ? unitComparator : _comparator.thenComparing(unitComparator);
				
			}
		}
	}
	
	public Comparator<BgUnit> getCombinedComparator()
	{
		return _comparator;
	}
	
	public List<EUnitComparator> getUnitComparators()
	{
		return _comperators;
	}
	
	public String[] getFilterTitles()
	{
		return _filterTitles;
	}

	public void setTitle(final String titles)
	{
		_filterTitles = Stream.of(titles.split(",")).distinct().toArray(String[]::new);
		_searchStrategies.add(ESearchStrategy.TITLE);
	}

	public void setRegionId(final String regionIds)
	{
		_filterRegionIds = Stream.of(regionIds.split(",")).mapToInt(Integer::valueOf).distinct().toArray();
		_searchStrategies.add(ESearchStrategy.REGION);
	}

	public void setCancelpolicyId(final String policyIds) 
	{
		_filterPolicyIds = Stream.of(policyIds.split(",")).mapToInt(Integer::valueOf).distinct().toArray();
		_searchStrategies.add(ESearchStrategy.CANCELPOLICY);
	}
	
	public long getOffset()
	{
		return _offset;
	}
	
	public void setOffset(final long offset)
	{
		_offset = offset;
	}
	
	//client side limit as requested by the client
	public long getLimit()
	{
		return _limit;
	}
	
	public void setLimit(final long limit)
	{
		_limit = limit;
	}
	
	public EnumSet<ESearchStrategy> getSearchStrategies()
	{
		return _searchStrategies;
	}
	
	public int[] getFilterRegionIds()
	{
		return _filterRegionIds;
	}
	
	public int[] getFilterPolicyIds()
	{
		return _filterPolicyIds;
	}
}
