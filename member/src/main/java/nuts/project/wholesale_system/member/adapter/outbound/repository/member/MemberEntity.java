package nuts.project.wholesale_system.member.adapter.outbound.repository.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "members")
public class MemberEntity {

    @Id
    private String memberId;

    @NotBlank
    private String name;

    @NotBlank
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporation_id")
    private CorporationEntity corporationEntity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    protected MemberEntity() {
        this.memberId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
