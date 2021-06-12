package bg.assignment.bg.backend.rest.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.assignment.bg.backend.manager.UnitManager;
import bg.assignment.bg.backend.model.BgUnit;
import bg.assignment.bg.backend.model.BgUser;
import bg.assignment.bg.backend.model.enums.ECreateUnitResult;
import bg.assignment.bg.backend.model.enums.EReviewUnitResult;
import bg.assignment.bg.backend.rest.model.answers.AnswerUnitCreate;
import bg.assignment.bg.backend.rest.model.answers.AnswerUnitReview;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitCreate;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitReview;

@RestController
public class UnitController
{
    @Autowired
    private UnitManager unitManager;

	@PostMapping("/units/create")
	public ResponseEntity<AnswerUnitCreate> createUnit(final Model model, @ModelAttribute RequestUnitCreate createUnitRequest)
	{
		final ECreateUnitResult res = unitManager.tryCreateUnit(createUnitRequest);
		
		if (res == ECreateUnitResult.SUCCESS__DB__CREATED)
		{
			final BgUnit newUnit = createUnitRequest.getNewUnit();
			return ResponseEntity.ok(new AnswerUnitCreate(newUnit.getUnitUUID().toString()));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/units/review")
	public ResponseEntity<AnswerUnitReview> retrieveUnit(final Principal principal, final Model model, @ModelAttribute RequestUnitReview unitReviewRequest)
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final BgUser bgUser = (BgUser) auth.getPrincipal();
		
		final EReviewUnitResult res = unitManager.tryReviewUnit(bgUser, unitReviewRequest);
		
		if (res == EReviewUnitResult.SUCCESS__DB__CREATED)
			return ResponseEntity.ok(new AnswerUnitReview(unitReviewRequest.getReviewScore()));

		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/unit/retrieve")
	public ResponseEntity<BgUnit> retrieveUnit(final Model model, @RequestParam("unitId") String unitId)
	{
		return null;
	}
}
