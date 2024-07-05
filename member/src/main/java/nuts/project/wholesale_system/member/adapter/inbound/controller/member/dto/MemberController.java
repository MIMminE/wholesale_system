package nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.DeleteMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.UpdateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.CreateMemberResponse;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.DeleteMemberResponse;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.UpdateMemberResponse;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest request) {

        Member member = memberService.createMember(request.getName(), request.getId(), request.getPassword(),
                request.getContactNumber(), request.getCorporationId());

        return ResponseEntity.ok(CreateMemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .corporationId(member.getCorporationId())
                .build());
    }

    @DeleteMapping("/members")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@RequestBody DeleteMemberRequest request) {
        memberService.deleteMember();
    }

    @PutMapping("/members")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestBody UpdateMemberRequest request) {
        memberService.updateMember();
    }

}
