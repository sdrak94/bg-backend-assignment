package bg.assignment.bg.backend.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.model.BgCancelPolicy;


@Service
@Scope("singleton")
public class CancelpolicyManager
{
	@Autowired
	@Qualifier("cancelpoliciesMap")
	private Map<Integer, BgCancelPolicy> cancelpoliciesMap;

	public BgCancelPolicy getCancelpolicyById(final int cancelpolicyId)
	{
		return cancelpoliciesMap.get(Integer.valueOf(cancelpolicyId));
	}
}
