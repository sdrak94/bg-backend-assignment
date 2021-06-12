package bg.assignment.bg.backend.model.compare;

import java.util.Comparator;
import java.util.function.Function;

import bg.assignment.bg.backend.model.BgUnit;

public enum UnitComparator implements Comparator<BgUnit>
{
	BY_TITLE(BgUnit::getScore),
	BY_PRICE(BgUnit::getPrice),
	BY_SCORE(BgUnit::getScore),
	BY_REGION(BgUnit::getRegionId),
	BY_CANCELPOLICY(BgUnit::getCancelPolicyId);

	private final Function<BgUnit, Number> _weight;
	
	private UnitComparator(final Function<BgUnit, Number> weight)
	{
		_weight = weight;
	}
	
	@Override
	public int compare(final BgUnit o1, final BgUnit o2)
	{
		final var num1 = _weight.apply(o1);
		final var num2 = _weight.apply(o2);
		
		return num1.doubleValue() > num2.doubleValue() ? -1 : 1;
	}
}
