package bg.assignment.bg.backend.model;

import java.util.Comparator;

import bg.assignment.bg.backend.model.compare.EUnitComparator;

public class UnitComparator implements Comparator<BgUnit>
{
	private Comparator<BgUnit> _unitComparator = EUnitComparator.BY_REGION; //default ...
	
	public UnitComparator(String input)
	{
		try
		{
			boolean descending = false;
			if (input.startsWith("v"))
			{
				descending = true;
				input = input.substring(1);
			}
			
			final int ordinal = Integer.parseInt(input);

			final EUnitComparator unitComparator = EUnitComparator.values()[ordinal];
			
			_unitComparator = descending ? unitComparator.reversed() : unitComparator;
		}
		catch (Exception e)
		{
		}
	}
	
	public Comparator<BgUnit> getComparator()
	{
		return _unitComparator;
	}

	@Override
	public int compare(BgUnit o1, BgUnit o2)
	{
		return _unitComparator.compare(o1, o2);
	}
}