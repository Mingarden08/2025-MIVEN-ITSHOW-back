package com.bookmoment.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_MEMBER_DETAIL")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", unique = true, nullable = true)
    private Member memberInfo;

    @Column(name = "profileUrl", updatable = true, length = 200, nullable = true)
    @Schema(description = "프로필 사진(경로)")
    private String profileUrl;

    @Column(name = "birth", updatable = true, length = 8, nullable = false)
    @Schema(description = "생년월일(YYYYMMDD")
    private String birth;

    @Column(name = "addr", updatable = true, length = 250, nullable = true)
    @Schema(description = "기본주소")
    private String addr;

    @Column(name = "addrDetail", updatable = true, length = 100)
    @Schema(description = "상세주소")
    private String addrDetail;

    @Column(name = "zipCode", updatable = true, length = 10)
    @Schema(description = "우편번호")
    private String zipCode;

    @Column(name = "gender", updatable = true, length = 1, nullable = false)
    @Schema(description = "성별 -> F : 여성, M : 남성")
    private String gender;

    @PrePersist
    public void prePersist() {
        //사전 초기화 작업이 필요한 참조형 변수가 있다면
        //여기에서 초기화.
        if (this.gender == null) { //예시
            this.gender = "M";
        }
    }

    //정적 팩토리 메서드
//    public static MemberDetail create(String address, String gender, String birthday) {
//        MemberDetail detail = new MemberDetail();
//        detail.addr = address;
//        detail.gender = gender;
//        detail.birth = birthday;
//        return detail;
//    }
}
