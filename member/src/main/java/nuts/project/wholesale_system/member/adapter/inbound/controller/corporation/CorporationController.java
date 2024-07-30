package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.DeleteCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.SearchCorporationsRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.UpdateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response.*;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.service.corporation.CorporationService;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationResultSet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corporation-service")
public class CorporationController {

    private final CorporationService corporationService;

    @GetMapping("/api/v1/corporations/{corporationId}")
    public ResponseEntity<GetCorporationResponse> getCorporation(@PathVariable("corporationId") String corporationId) {

        Corporation corporation = corporationService.getCorporation(corporationId);
        GetCorporationResponse getCorporationResponse = GetCorporationResponse.fromCorporation(corporation);

        return ResponseEntity.ok(getCorporationResponse);
    }

    @PostMapping("/api/v1/corporations/search")
    public ResponseEntity<SearchCorporationResponse> searchCorporation(@RequestBody @Valid SearchCorporationsRequest request) {

        String corporationName = request.getCorporationName();
        String representative = request.getRepresentative();
        String contactNumber = request.getContactNumber();
        String businessNumber = request.getBusinessNumber();
        String corporationId = request.getCorporationId();
        Grade grade = Grade.valueOf(request.getGrade());

        List<Corporation> corporations = corporationService.searchCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade);
        SearchCorporationResponse searchCorporationResponse = SearchCorporationResponse.fromCorporations(corporations);

        return ResponseEntity.ok(searchCorporationResponse);
    }

    @PostMapping("/api/v1/corporations")
    public ResponseEntity<CreateCorporationResponse> createCorporation(@RequestBody @Valid CreateCorporationRequest request) {

        String corporationName = request.getCorporationName();
        String representative = request.getRepresentative();
        String contactNumber = request.getContactNumber();
        String businessNumber = request.getBusinessNumber();
        Grade grade = Grade.valueOf(request.getGrade());

        Corporation corporation = corporationService.createCorporation(corporationName, representative, contactNumber, businessNumber, grade);
        CreateCorporationResponse createCorporationResponse = CreateCorporationResponse.fromCorporation(corporation);

        return ResponseEntity.ok(createCorporationResponse);
    }

    @PutMapping("/api/v1/corporations")
    public ResponseEntity<UpdateCorporationResponse> updateCorporation(@RequestBody @Valid UpdateCorporationRequest request) {

        String corporationId = request.getCorporationId();
        String corporationName = request.getCorporationName();
        String representative = request.getRepresentative();
        String contactNumber = request.getContactNumber();
        String businessNumber = request.getBusinessNumber();
        Grade grade = Grade.valueOf(request.getGrade());

        UpdateCorporationResultSet updateCorporationResultSet = corporationService.updateCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade);
        UpdateCorporationResponse updateCorporationResponse = UpdateCorporationResponse.fromUpdateCorporationResultSet(updateCorporationResultSet);

        return ResponseEntity.ok(updateCorporationResponse);
    }

    @DeleteMapping("/api/v1/corporations")
    public ResponseEntity<DeleteCorporationResponse> deleteCorporation(@RequestBody @Valid DeleteCorporationRequest request) {

        String corporationId = request.getCorporationId();

        Corporation corporation = corporationService.deleteCorporation(corporationId);
        DeleteCorporationResponse deleteCorporationResponse = DeleteCorporationResponse.fromCorporation(corporation);

        return ResponseEntity.ok(deleteCorporationResponse);
    }
}
