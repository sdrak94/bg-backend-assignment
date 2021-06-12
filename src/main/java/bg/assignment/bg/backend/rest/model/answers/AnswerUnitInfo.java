package bg.assignment.bg.backend.rest.model.answers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import bg.assignment.bg.backend.model.BgCancelPolicy;
import bg.assignment.bg.backend.model.BgRegion;
import bg.assignment.bg.backend.model.BgUnit;
import bg.assignment.bg.backend.model.ReviewScore;

public class AnswerUnitInfo extends AnswerUnitScore
{
	private static final DecimalFormat priceFormat = new DecimalFormat("#,###.00");
	
	private final String _imageUrl;
	
	private final String _title;

	private BgRegion _bgRegion;
	
	private final String _desc;
	
	private final BgCancelPolicy _bgCancelpolicy;
	
	private final BigDecimal _price;
	
	public AnswerUnitInfo(final BgUnit bgUnit, final ReviewScore reviewScore) 
	{
		super(bgUnit.getUnitUUID().toString(), reviewScore);
		
		_imageUrl = bgUnit.getImageUrl();
		
		_title = bgUnit.getTitle();
		
		_bgRegion = bgUnit.getRegion();
		
		_desc = bgUnit.getDesc();
		
		_bgCancelpolicy = bgUnit.getCancelPolicy();
		
		_price = bgUnit.getPrice();
	}
	
	public String getImageUrl()
	{
		return _imageUrl;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	public String getRegionName()
	{
		return _bgRegion.getName();
	}
	
	public String getDescription()
	{
		return _desc;
	}
	
	public String getCancelPolicy()
	{
		return _bgCancelpolicy.getDesc();
	}
	
	public String getPrice()
	{
		return priceFormat.format(_price);
	}
}
