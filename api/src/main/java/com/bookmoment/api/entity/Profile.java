package com.bookmoment.api.entity;

import com.bookmoment.api.dto.req.PatchProfileReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "TBL_PROFILE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@Slf4j
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Schema(description = "회원 이름")
    private String name;

    @Column(name = "img")
    @Schema(description = "회원 프로필 이미지")
    private String img;

    @Column(name = "coverColor")
    @Schema(description = "커버 색상")
    private String coverColor;

    @Column(name = "quoteCount")
    @Schema(description = "리뷰 개수")
    private int quoteCount;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false, unique = true)
    private Member member;

    @OneToOne(mappedBy = "profileInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Music music;

    @OneToOne(mappedBy = "profileInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Quote quote;

    public Profile() {

    }

    /**
     * Entity Update
     * @param reqDto
     */
    public void updateFrom(PatchProfileReqDto reqDto, Profile profile) {
        String userName = reqDto.getName();
        log.info(">>>> userName: " + userName);
        this.name = userName;

        if (this.name == null) {
            this.name = profile.getName();
        }

        //this.setName((reqDto.getName() == null) ? profile.getName() : null);  //클라이언트가 이름을 보내지 않으면 기본적으로 회원 테이블의 이름을 사용.
        this.setCoverColor(reqDto.getCoverColor());
    }
}
