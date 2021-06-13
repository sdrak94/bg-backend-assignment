package bg.assignment.bg.backend.model.compare;

import java.util.Comparator;
import java.util.function.Function;

import bg.assignment.bg.backend.model.BgUnit;

public enum EUnitComparator implements Comparator<BgUnit>
{
	BY_TITLE(null),
	BY_PRICE(BgUnit::getPriceAsDouble),
	BY_SCORE(BgUnit::getScore),
	BY_REGION(BgUnit::getRegionIdAsDouble),
	BY_CANCELPOLICY(BgUnit::getCancelpolicyIdAsDouble);

	//everything wrapped into Double for convinience
	
	private final Function<BgUnit, Double> _weight;

	private EUnitComparator(final Function<BgUnit, Double> weight)
	{
		_weight = weight;
	}

	@Override
	public int compare(final BgUnit o1, final BgUnit o2)
	{
		if (this == BY_TITLE) //special case here
			return o1.getTitle().compareTo(o2.getTitle());
		
		final var num1 = _weight.apply(o1);
		final var num2 = _weight.apply(o2);
		
		return num1.compareTo(num2);
	}
}
