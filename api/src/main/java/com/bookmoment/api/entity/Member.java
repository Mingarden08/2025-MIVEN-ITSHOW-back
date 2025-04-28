package com.bookmoment.api.entity;

import com.bookmoment.api.constant.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_MEMBER_INFO")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", updatable = false, length = 50, nullable = false)
    @Schema(description = "회원 이메일")
    private String email;

    @Column(name = "name", length = 25, nullable = false)
    @Schema(description = "회원이름")
    private String name;

    @Column(name = "passwd", length = 100, nullable = false)
    @Schema(description = "비빌번호")
    private String passwd;

    @Column(name = "tel", length = 11, nullable = false)
    @Schema(description = "회원 연락처")
    private String tel;

    @Column(name = "isLock", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    @Schema(description = "계정 상태, 잠김여부 true : 잠김, false : 정상")
    private boolean isLock;

    @Column(name = "isWithdraw", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    @Schema(description = "회원 탈퇴 여부, true : 탈퇴회원, false : 정상")
    private boolean isWithdraw;

    @Column(name = "role", columnDefinition = "VARCHAR(5)")
    @Schema(description = "회원 등급(USER, ADMIN)")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "memberInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //orphanRemoval = true일경우는 필수 삭제됨
    private MemberDetail memberDetail;

    public Member() {

    }

    @PrePersist
    public void prePersist() {
        //사전 초기화 작업이 필요한 참조형 변수가 있다면
        //여기에서 초기화.
        if (this.role == null) {
            this.role = Role.USER;
        }
    }

    // 부가정보 추가 메서드
    public void addMemberDetail(MemberDetail detail) {
        this.memberDetail = detail;
       detail.setMemberInfo(this);
    }
}
