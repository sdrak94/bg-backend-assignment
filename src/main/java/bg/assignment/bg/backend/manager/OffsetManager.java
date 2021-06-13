package bg.assignment.bg.backend.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bg.assignment.bg.backend.model.BgUser;
import bg.assignment.bg.backend.model.RetrieveOffset;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitList;

@Service
@Scope("singleton")
public class OffsetManager
{
	@Autowired
    private boolean retrieveServerSide;

	@Autowired
    private int clientsideHardLimit;

	@Autowired
    private int serversideLimit;

	public RetrieveOffset calculateOffsets(final RequestUnitList unitListRequest)
	{
		final long clientsideLimit = unitListRequest.getLimit();
		
		long actualLimit = Math.min(clientsideHardLimit, clientsideLimit); //won't go higher than server's hard limit

		RetrieveOffset retrieveOffset;
		if (retrieveServerSide)
		{
			final BgUser bgUser = null;//TODO use jwt to retrieve the user
			actualLimit = serversideLimit; //server hard limit is bypassed, do we want this ?
			retrieveOffset = bgUser.getUserOffsets(actualLimit);
		}
		else
			retrieveOffset = new RetrieveOffset(actualLimit);
	
		retrieveOffset.incOffset(unitListRequest.getOffset()); //the client is allowed to ask for whatever offset it wants no checks here
		
		return retrieveOffset;
	}
}
