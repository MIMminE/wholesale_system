package nuts.project.wholesale_system.member.adapter.outbound.repository.corporation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "corporations")
public class CorporationEntity {

    @Id
    private String corporationId;

    @NotBlank
    private String corporationName;

    @NotBlank
    private String representative;

    @NotBlank
    private String contactNumber;

    @NotBlank
    @Column(unique = true)
    private String businessNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToMany(mappedBy = "corporationEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MemberEntity> registeredMembers = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CorporationEntity() {
        this.corporationId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public CorporationEntity(String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {
        this();
        this.corporationName = corporationName;
        this.representative = representative;
        this.contactNumber = contactNumber;
        this.businessNumber = businessNumber;
        this.grade = grade;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Corporation toCorporation() {
        return Corporation.builder()
                .corporationId(this.corporationId)
                .corporationName(this.corporationName)
                .representative(this.representative)
                .contactNumber(this.contactNumber)
                .businessNumber(this.businessNumber)
                .grade(this.grade)
                .registeredMembers(this.registeredMembers.stream().map(MemberEntity::toMember).toList())
                .build();
    }

    public void addMember(MemberEntity memberEntity) {
        this.registeredMembers.add(memberEntity);
    }

    public void removeMember(MemberEntity memberEntity) {
        this.registeredMembers.remove(memberEntity);
    }
}
