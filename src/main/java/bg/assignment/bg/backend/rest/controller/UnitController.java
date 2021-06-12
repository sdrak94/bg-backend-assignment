package bg.assignment.bg.backend.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.assignment.bg.backend.manager.UnitManager;
import bg.assignment.bg.backend.model.BgUnit;
import bg.assignment.bg.backend.model.BgUser;
import bg.assignment.bg.backend.model.ReviewScore;
import bg.assignment.bg.backend.model.enums.ECreateUnitResult;
import bg.assignment.bg.backend.model.enums.EReviewUnitResult;
import bg.assignment.bg.backend.rest.model.answers.AnswerUnitInfo;
import bg.assignment.bg.backend.rest.model.answers.AnswerUnitScore;
import bg.assignment.bg.backend.rest.model.answers.AnswerUnitUUID;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitCreate;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitInfo;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitList;
import bg.assignment.bg.backend.rest.model.requests.RequestUnitReview;

@RestController
public class UnitController
{
    @Autowired
    private UnitManager unitManager;

	@PostMapping("/units/create")
	public ResponseEntity<AnswerUnitUUID> createUnit(final Model model, @ModelAttribute final RequestUnitCreate createUnitRequest)
	{
		final ECreateUnitResult res = unitManager.tryCreateUnit(createUnitRequest);
		
		if (res == ECreateUnitResult.SUCCESS__DB__CREATED)
		{
			final BgUnit newUnit = createUnitRequest.getNewUnit();
			return ResponseEntity.ok(new AnswerUnitUUID(newUnit.getUnitUUID().toString()));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/units/review")
	public ResponseEntity<AnswerUnitScore> reviewUnit(final Model model, @ModelAttribute final RequestUnitReview unitReviewRequest)
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final BgUser bgUser = (BgUser) auth.getPrincipal();
		
		final EReviewUnitResult res = unitManager.tryReviewUnit(bgUser, unitReviewRequest);
		
		if (res == EReviewUnitResult.SUCCESS__DB__CREATED)
			return ResponseEntity.ok(new AnswerUnitScore(unitReviewRequest.getUnitUUID(), unitReviewRequest.getReviewScore()));

		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/units/getscore")
	public ResponseEntity<AnswerUnitScore> getScore(final Model model, @ModelAttribute final RequestUnitReview unitReviewRequest)
	{
		final String unitUUID = unitReviewRequest.getUnitUUID();
		final ReviewScore reviewScore = unitManager.getTotalScoreByUnitId(unitUUID);
		
		return ResponseEntity.ok(new AnswerUnitScore(unitUUID, reviewScore));
	}
	
	@GetMapping("/units/getinfo")
	public ResponseEntity<AnswerUnitInfo> getUnitInfo(final Model model, @ModelAttribute final RequestUnitInfo unitInfoRequest)
	{
		final String unitUUID = unitInfoRequest.getUnitUUID();
		final BgUnit bgUnit = unitManager.getUnitById(unitUUID);

		if (bgUnit != null)
		{
			final ReviewScore reviewScore = unitManager.getTotalScoreByUnitId(unitUUID);
			return ResponseEntity.ok(new AnswerUnitInfo(bgUnit, reviewScore));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/units/retrieve")
	public ResponseEntity<List<AnswerUnitInfo>> retrieveUnit(final Model model, @ModelAttribute final RequestUnitList unitListRequest)
	{
		final List<AnswerUnitInfo> unitInfos = new ArrayList<>();
		final List<BgUnit> bgUnits = unitManager.getAllUnits();
		
		for (final BgUnit bgUnit : bgUnits)
		{
			final ReviewScore reviewScore = unitManager.getTotalScoreByUnitId(bgUnit.getUnitUUID().toString());
			final AnswerUnitInfo unitInfoAnswer = new AnswerUnitInfo(bgUnit, reviewScore);
			unitInfos.add(unitInfoAnswer);
		}

		return ResponseEntity.ok(unitInfos);
	}
}
