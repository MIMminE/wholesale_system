package nuts.project.wholesale_system.member.domain.service.member;

import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.usecase.create.CreateMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.delete.DeleteMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.update.UpdateMemberUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private CreateMemberUseCase createMemberUseCase;

    @Mock
    private DeleteMemberUseCase deleteMemberUseCase;

    @Mock
    private UpdateMemberUseCase updateMemberUseCase;

    @InjectMocks
    private MemberService memberService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("createMemberUseCase 성공 테스트")
    @Test
    void SuccessCreateMemberUseCase() {
        // given
        String name = "tester";
        String id = "test_id";
        String password = passwordEncoder.encode("test_password");
        String contactNumber = "010-1215-4513";
        String corporationId = "ds5c3";

        Member member = new Member(name, id, password, contactNumber, corporationId);

        BDDMockito.given(createMemberUseCase.execute(name, id, password, contactNumber, corporationId))
                .willReturn(member);

        // when
        Member resultMember = memberService.createMember(name, id, password, contactNumber, corporationId);

        // then
        assertThat(resultMember).isEqualTo(member);
        BDDMockito.verify(createMemberUseCase).execute(name, id, password, contactNumber, corporationId);
    }
}