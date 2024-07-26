package nuts.project.wholesale_system.member.adapter.inbound.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.DeleteMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.UpdateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.CreateMemberResponse;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.DeleteMemberResponse;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.GetMemberResponse;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.UpdateMemberResponse;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corporation-service")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members/{id}")
    public ResponseEntity<GetMemberResponse> getMember(@PathVariable("id") String id) {
        Member member = memberService.getMember(id);

        return ResponseEntity.ok(GetMemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .contactNumber(member.getContactNumber())
                .corporationId(member.getCorporationId())
                .build());
    }

    @PostMapping("/api/v1/members")
    public ResponseEntity<CreateMemberResponse> createMember(@RequestBody @Valid CreateMemberRequest request) {

        Member member = memberService.createMember(request.getName(), request.getId(), request.getPassword(),
                request.getContactNumber(), request.getCorporationId());

        return ResponseEntity.ok(CreateMemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .contactNumber(member.getContactNumber())
                .corporationId(member.getCorporationId())
                .build());
    }

    @DeleteMapping("/api/v1/members")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@RequestBody @Valid DeleteMemberRequest request) {
        Member member = memberService.deleteMember(request.getId());

        return ResponseEntity.ok(DeleteMemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .contactNumber(member.getContactNumber())
                .corporationId(member.getCorporationId())
                .build());
    }

    @PutMapping("/api/v1/members")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestBody @Valid UpdateMemberRequest request) {

        Member beforeMember = memberService.getMember(request.getRequestId());
        Member updatedMember = new Member(request.getNewName(), request.getRequestId(), request.getNewPassword(), request.getNewContactNumber(), request.getNewCorporationId());
        memberService.updateMember(request.getRequestId(), updatedMember);

        GetMemberResponse beforeMemberResponse = GetMemberResponse.builder()
                .id(beforeMember.getId())
                .name(beforeMember.getName())
                .contactNumber(beforeMember.getContactNumber())
                .corporationId(beforeMember.getCorporationId())
                .build();

        GetMemberResponse updatedMemberResponse = GetMemberResponse.builder()
                .id(updatedMember.getId())
                .name(updatedMember.getName())
                .contactNumber(updatedMember.getContactNumber())
                .corporationId(updatedMember.getCorporationId())
                .build();

        return ResponseEntity.ok(new UpdateMemberResponse(beforeMemberResponse, updatedMemberResponse));
    }
}
