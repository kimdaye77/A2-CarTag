package autoever2.cartag.service;

import autoever2.cartag.domain.option.*;
import autoever2.cartag.repository.CarRepository;
import autoever2.cartag.repository.OptionRepository;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
class OptionServiceTest {

    @InjectSoftAssertions
    private SoftAssertions softAssertions;

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private CarRepository carRepository;

    private List<SubOptionMappedDto> optionList;

    private List<String> hashtagList;

    @BeforeEach
    void setUp() {
        hashtagList = new ArrayList<>();
        optionList = new ArrayList<>();
        hashtagList.add("여행");
        hashtagList.add("장거리 운전");
        hashtagList.add("시원한");

        optionList.add(SubOptionMappedDto.builder()
                .optionId(1)
                .optionCategoryName("상세품목")
                .optionImage("/images/options/sub/2seats.jpg")
                .optionPrice(100000L)
                .optionName("2열 통풍 시트")
                .optionBoughtCount(2800L)
                .optionUsedCount(30.0)
                .build());

        optionList.add(SubOptionMappedDto.builder()
                .optionId(2)
                .optionCategoryName("악세사리")
                .optionImage("/images/options/sub/legwarmer.jpg")
                .optionPrice(230000L)
                .optionName("적외선 무릎 워머")
                .optionBoughtCount(4200L)
                .optionUsedCount(42.0)
                .build());

        optionList.add(SubOptionMappedDto.builder()
                .optionId(3)
                .optionCategoryName("악세사리")
                .optionImage("/images/options/sub/murfler.jpg")
                .optionPrice(840000L)
                .optionName("듀얼 머플러 패키지")
                .optionBoughtCount(1300L)
                .optionUsedCount(55.0)
                .build());

        optionList.add(SubOptionMappedDto.builder()
                .optionId(4)
                .optionCategoryName("휠")
                .optionImage("/images/options/sub/darkwheel.jpg")
                .optionPrice(840000L)
                .optionName("20인치 다크 스퍼터링 휠")
                .optionBoughtCount(3850L)
                .optionUsedCount(12.0)
                .build());
    }

    @Test
    @DisplayName("추가옵션 리스트를 가져와 퍼센트로 변환")
    void getSubOptionList() {
        int carId = 1;
        Long boughtCount = 2000L;
        when(optionRepository.findAllSubOptionWithCategoryNameByCarId(carId)).thenReturn(optionList);
        when(carRepository.findCarBoughtCountByCarId(carId)).thenReturn(Optional.of(10000L));
        when(optionRepository.findAllHashtagNameBySubOptionId(1)).thenReturn(hashtagList);
        when(optionRepository.findAllHashtagNameBySubOptionId(2)).thenReturn(hashtagList);
        when(optionRepository.findAllHashtagNameBySubOptionId(3)).thenReturn(hashtagList);
        when(optionRepository.findAllHashtagNameBySubOptionId(4)).thenReturn(hashtagList);


        //when
        List<SubOptionDto> result = optionService.getSubOptionList(carId);

        //then
        assertEquals(4, result.size());
        assertEquals(1, result.get(0).getSubOptionId());
        assertEquals("상세품목", result.get(0).getOptionCategoryName());
        assertEquals("/images/options/sub/legwarmer.jpg", result.get(1).getOptionImage());
        assertEquals(230000L, result.get(1).getOptionPrice());
        assertEquals("듀얼 머플러 패키지", result.get(2).getOptionName());
        assertEquals(hashtagList, result.get(2).getHashtagName());
        assertEquals(38, result.get(3).getPercentage());
        assertTrue(result.get(3).isHasHmgData());
    }

    @Test
    @DisplayName("옵션의 상세 데이터를 가져온다.")
    void getOptionDetail() {
        int carId = 1;
        int singleOption = 4;
        int packageOption = 69;
        OptionDetailMappedDto data1 = OptionDetailMappedDto.builder()
                .optionName("20인치 다크 스퍼터링 휠")
                .optionImage("/images/options/sub/darkwheel.jpg")
                .optionUsedCount(12.0)
                .optionBoughtCount(3850L)
                .optionDescription("다크 휠입니다.")
                .categoryName("휠")
                .build();

        OptionDetailDto expected1 = OptionDetailDto.builder()
                .categoryName("휠")
                .optionDescription("다크 휠입니다.")
                .optionName("20인치 다크 스퍼터링 휠")
                .optionImage("/images/options/sub/darkwheel.jpg")
                .hmgData(OptionHmgDataVo.builder().optionUsedCount(12.0).optionBoughtCount(3850L).build())
                .isPackage(false)
                .build();

        OptionDetailMappedDto data2 = OptionDetailMappedDto.builder()
                .optionName("컴포트2")
                .optionImage("/options/rear-passenger.png")
                .optionBoughtCount(7890L)
                .categoryName("상세품목")
                .build();

        List<OptionDetailMappedDto> subPackages = new ArrayList<>();
        subPackages.add(OptionDetailMappedDto.builder()
                .optionName("후석 승객알림")
                .optionImage("/options/rear-passenger.png")
                .optionUsedCount(82.0)
                .optionDescription("초음파 센서를 통해 뒷좌석에 남아있는 승객의 움직임을 감지하여 운전자에게 경고함으로써 부주의에 의한 유아 또는 반려 동물 등의 방치 사고를 예방하는 신기술입니다.")
                .categoryName("악세사리")
                .optionBoughtCount(7890L)
                .build());
        subPackages.add(OptionDetailMappedDto.builder()
                .optionName("메탈 리어범퍼스텝")
                .optionImage("/options/metalrearbumper.png")
                .optionDescription("러기지 룸 앞쪽 하단부를 메탈로 만들어 물건을 싣고 내릴 때나 사람이 올라갈 때 차체를 보호해줍니다.")
                .categoryName("외관")
                .build());

        List<OptionDetailDto> subOptions = new ArrayList<>();
        subOptions.add(OptionDetailDto.builder()
                .categoryName("악세사리")
                .optionName("후석 승객알림")
                .optionImage("/options/rear-passenger.png")
                .isPackage(false)
                .optionDescription("초음파 센서를 통해 뒷좌석에 남아있는 승객의 움직임을 감지하여 운전자에게 경고함으로써 부주의에 의한 유아 또는 반려 동물 등의 방치 사고를 예방하는 신기술입니다.")
                .hmgData(OptionHmgDataVo.builder()
                        .optionBoughtCount(7890L)
                        .optionUsedCount(82.0)
                        .build())
                .build());
        subOptions.add(OptionDetailDto.builder()
                .categoryName("외관")
                .optionName("메탈 리어범퍼스텝")
                .optionImage("/options/metalrearbumper.png")
                .isPackage(false)
                .hmgData(OptionHmgDataVo.builder().optionBoughtCount(7890L).build())
                .optionDescription("러기지 룸 앞쪽 하단부를 메탈로 만들어 물건을 싣고 내릴 때나 사람이 올라갈 때 차체를 보호해줍니다.")
                .build());

        OptionDetailDto expected2 = OptionDetailDto.builder()
                .categoryName("상세품목")
                .optionName("컴포트2")
                .optionImage("/options/rear-passenger.png")
                .isPackage(true)
                .hmgData(OptionHmgDataVo.builder().optionBoughtCount(7890L).build())
                .subOptionList(subOptions)
                .build();

        when(optionRepository.findOptionDetail(carId, singleOption)).thenReturn(Optional.of(data1));
        when(optionRepository.findPackageSubOptions(singleOption)).thenReturn(Collections.emptyList());
        when(optionRepository.findOptionDetail(carId, packageOption)).thenReturn(Optional.of(data2));
        when(optionRepository.findPackageSubOptions(packageOption)).thenReturn(subPackages);

        OptionDetailDto singleResult = optionService.getOptionDetailData(carId, singleOption);
        OptionDetailDto packageResult = optionService.getOptionDetailData(carId, packageOption);

        softAssertions.assertThat(singleResult).usingRecursiveComparison().isEqualTo(expected1);
        softAssertions.assertThat(packageResult).usingRecursiveComparison().isEqualTo(expected2);
    }
}