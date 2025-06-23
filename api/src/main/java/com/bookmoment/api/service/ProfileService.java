package com.bookmoment.api.service;

import com.bookmoment.api.dto.req.PatchProfileReqDto;
import com.bookmoment.api.dto.res.MusicRes;
import com.bookmoment.api.dto.res.ProfileRes;
import com.bookmoment.api.dto.res.QuoteRes;
import com.bookmoment.api.entity.Member;
import com.bookmoment.api.entity.Music;
import com.bookmoment.api.entity.Profile;
import com.bookmoment.api.entity.Quote;
import com.bookmoment.api.repository.ProfileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import jakarta.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final  MemberService memberService;

    private final GalleryService galleryService;

    /**
     * 프로필 등록 또는 수정
     * @param userId
     * @param reqDto
     * @return
     */
    public boolean patchProfile(String userId, @Valid PatchProfileReqDto reqDto) {
        Member member = memberService.getMemberByEmail(userId);
        log.info("member: {}", member.getName());
        Optional<Profile> profileOptional = profileRepository.findByMemberId(member.getId());
        log.info("{}", profileOptional.isPresent());
        if (profileOptional.isPresent()) { //등록된 프로필이 존재하는 경우 update
            Profile profile = profileOptional.get();

            //로그인한 사용자의 프로필 수정이 아니면 리턴
            if (!profile.getMember().getName().equals(member.getName())) {
                log.info(">>>> 로그인 사용자 프로필이 아닌 다른 프로필 수정 : " + profile.getMember().getName());
                return false;
            }

            int quoteCount = Math.toIntExact(galleryService.getMyGalleryCount(member));
            profile.setQuoteCount(quoteCount);

            if (profile.getMusic() == null) {
                profile.setMusic(reqDto.getMusic().toEntity()); //없을 때 설정
                Music music = profile.getMusic();
                music.setProfileInfo(profile);
            } else {
                profile.getMusic().updateFrom(reqDto.getMusic()); //뮤직정보 업데이트
            }

            if (profile.getQuote() == null) {
                profile.setQuote(reqDto.getQuote().toEntity()); //없을 때 설정
                Quote quote = profile.getQuote();
                quote.setProfileInfo(profile);
            } else {
                profile.getQuote().updateFrom(reqDto.getQuote()); //인용구 업데이트
            }
            profile.updateFrom(reqDto, profile); //프로필 업데이트
            profile.getMember().updateEntity(reqDto.getName()); //member에 담긴 이름 업데이트

        } else { //없을 경우 신규 생성(추가)

            //profile안에는 이미 music과 quote가 만들어져 있음.
            //                .music(this.music.toEntity())
            //                .quote(this.quote.toEntity())
            // JPA연속성에 의거, profile이 저장될 때 저장된 profile 을 참조해야하는
            // music과 queote에는 자동으로 profile_id가 참조되어진 것으로 이해할 필요 있음.
            Profile profile = reqDto.toEntity(member);

            int quoteCount = Math.toIntExact(galleryService.getMyGalleryCount(member));
            profile.setQuoteCount(quoteCount);

            //저장된 newProfile에서 각각 Music, Quote에 저장된 Profile을 설정해준다.
            //양방향 매핑관계에서는 주인과 비주인 둘 다 설정을 추가해주기 위함.

            // 따라서, new Music할게 아니라 profile.getMusic()을 해서
            // music 을 가져오고 music이 참조하는 profile은 위에서 만든 profile을 연결하는거지.
            // 현재까지 실제 db에는 어떠한 insert도 일어나지 않은 상태.
            Music music = profile.getMusic(); //newProfile.getMusic();
            music.setProfileInfo(profile);

            Quote quote = profile.getQuote();
            quote.setProfileInfo(profile);

            profileRepository.save(profile);
        }
        return true;
    }

    /**
     * 프로필 조회
     * @param userId
     * @return
     */
    public ProfileRes getProfile(String userId) {
        Member member = memberService.getMemberByEmail(userId);
        log.info("member: {}", member.getName());
        Long id = member.getId();
        Optional<Profile> profileOptional = profileRepository.findByMemberId(id);
        ProfileRes profileRes = new ProfileRes();
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            //TODO: - music과 quote null일 때 처리 필요
            return profileRes.builder()
                    .profileImg(profile.getImg())
                    .name(profile.getName())
                    .coverColor(profile.getCoverColor())
                    .quoteCount(profile.getQuoteCount())
                    .quote(new QuoteRes(profile.getQuote()))
                    .music(new MusicRes(profile.getMusic()))
                    .build();
        } else {
            int quoteCount = Math.toIntExact(galleryService.getMyGalleryCount(member));
            return profileRes.builder()
                    .name(member.getName())
                    .quoteCount(quoteCount)
                    .build();
        }
    }

    /**
     * 프로필 이미지 저장
     * @param userId
     * @param profileImage
     * @return
     */
    public boolean updateImage(String userId, String profileImage) {
        boolean result = true;
        Member member = memberService.getMemberByEmail(userId);
        log.info("member: {}", member.getName());
        Long id = member.getId();
        Optional<Profile> profileOptional = profileRepository.findByMemberId(id);

        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setImg(profileImage);
            profileRepository.save(profile);
        } else {
            Profile profile = new Profile();
            profile.setImg(profileImage);
            profile.setName(member.getName());
            profile.setMember(member);
            int quoteCount = Math.toIntExact(galleryService.getMyGalleryCount(member));
            profile.setQuoteCount(quoteCount);
            profileRepository.save(profile);
        }
        
        return result;
    }
}
