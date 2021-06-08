package bg.assignment.bg.backend.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.assignment.bg.backend.model.BgUnit;

@RestController
public class UnitController
{
	@GetMapping("/retrieve")
	public ResponseEntity<BgUnit> retrieveUnit(final Model model, @RequestParam("unitId") String unitId)
	{
		return null;
	}
}
