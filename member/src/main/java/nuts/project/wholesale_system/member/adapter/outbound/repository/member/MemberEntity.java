package nuts.project.wholesale_system.member.adapter.outbound.repository.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.model.Member;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "members")
public class MemberEntity {

    @Id
    @Setter
    private String memberId;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporation_id")
    @Setter
    private CorporationEntity corporationEntity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public MemberEntity() {
        this.memberId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public MemberEntity(String name, String id, String password, String contactNumber, CorporationEntity corporationEntity) {
        this();
        this.name = name;
        this.id = id;
        this.password = password;
        this.contactNumber = contactNumber;
        this.corporationEntity = corporationEntity;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Member toMember() {
        return new Member(this.name, this.id, this.password, this.contactNumber, this.corporationEntity.getCorporationId());
    }

}
