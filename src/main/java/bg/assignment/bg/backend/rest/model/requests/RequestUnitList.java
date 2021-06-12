package bg.assignment.bg.backend.rest.model.requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import bg.assignment.bg.backend.model.BgUnit;
import bg.assignment.bg.backend.model.compare.UnitComparator;

public class RequestUnitList implements Serializable
{
	private static final long serialVersionUID = 4114083354367118270L;
	private List<UnitComparator> _orderBy = null;
	
	private Comparator<BgUnit> _comparator = null;
	
	private String _descFilter = "";
	
	private String _title = "";
	
	private int _regionId = -1;
	
	private int _cancelpolicyId = -1;
	
	public void setOrderBy(final String orderBy) //this is a hack
	{
		final String[] comparatorOrdinals = orderBy.split(",");
		final int ordinalsLen = comparatorOrdinals.length;
		if (ordinalsLen > 0) 
		{
			_orderBy = new ArrayList<>(ordinalsLen);
			for (final String comparatorOrdinalStr : comparatorOrdinals)
			{
				final int comparatorOrdinal = Integer.parseInt(comparatorOrdinalStr);
				if (comparatorOrdinal < ordinalsLen)
				{
					final UnitComparator unitComparator = UnitComparator.values()[comparatorOrdinal];

					_comparator = _comparator == null ? unitComparator : _comparator.thenComparing(unitComparator); //combine the comparators
					
					_orderBy.add(unitComparator);
				}
			}
		}
	}
	
	public Comparator<BgUnit> getCombinedComparator()
	{
		return _comparator;
	}
	
	public List<UnitComparator> getUnitComparators()
	{
		return _orderBy;
	}
	
	public String getDescFilter()
	{
		return _descFilter;
	}

	public void setDesc(String desc)
	{
		_descFilter = desc;
	}

	public String getTitle()
	{
		return _title;
	}

	public void setTitle(String title)
	{
		_title = title;
	}

	public int getRegionId()
	{
		return _regionId;
	}

	public void setRegionId(int regionId)
	{
		_regionId = regionId;
	}

	public int getCancelpolicyId()
	{
		return _cancelpolicyId;
	}

	public void setCancelpolicyId(int cancelpolicyId) 
	{
		_cancelpolicyId = cancelpolicyId;
	}
}
