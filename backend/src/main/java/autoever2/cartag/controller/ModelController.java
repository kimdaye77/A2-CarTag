package autoever2.cartag.controller;

import autoever2.cartag.domain.model.ModelDetailMappedDto;
import autoever2.cartag.domain.model.ModelEfficiencyDataDto;
import autoever2.cartag.domain.model.ModelShortDataDto;
import autoever2.cartag.domain.model.PowerTrainMappedDto;
import autoever2.cartag.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/modeltypes")
@Tag(name = "트림의 모델 타입", description = "트림의 모델 타입 호출 관련 api")
public class ModelController {

    private final ModelService modelTypeService;

    @Operation(summary = "차량 모델 타입 리스트 조회", description = "차량 모델 페이지에서 하단의 리스트(파워트레인 등)를 반환하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ModelShortDataDto.class)))
            })
    })
    @GetMapping("/list")
    public List<ModelShortDataDto> getTrimModelType(@Parameter(description = "선택한 차량 트림ID") @RequestParam("carid") int carId) {
        return modelTypeService.getModelTypeData(carId);
    }

    @Operation(summary = "모델타입 상세 데이터 조회", description = "모델명과 설명, 이미지 반환하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ModelDetailMappedDto.class)))
    })
    @GetMapping("/detail")
    public ModelDetailMappedDto getModelDetail(@Parameter(description = "모델 타입 ID") @RequestParam("modelid") int modelId) {
        return modelTypeService.getModelDetail(modelId);
    }

    @Operation(summary = "파워트레인 HMG 데이터 호출", description = "파워트레인의 마력과 토크값 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PowerTrainMappedDto.class)))
    })
    @GetMapping("/hmg-powertrain")
    public PowerTrainMappedDto getPowerTrainData(@Parameter(description = "파워트레인 모델 타입 ID") @RequestParam("powertrain") int powerTrainId) {
        return modelTypeService.getPowerTrainHmgData(powerTrainId);
    }

    @Operation(summary = "효율 HMG 데이터 호출", description = "파워트레인과 구동방식의 조합으로 나온 효츌 HMG값 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ModelEfficiencyDataDto.class)))
    })
    @GetMapping("/hmg-efficiency")
    public ModelEfficiencyDataDto getPowerTrainData(@Parameter(description = "파워트레인 모델 타입 ID") @RequestParam("powertrain") int powerTrainId, @Parameter(description = "구동방식 모델 타입 ID") @RequestParam("operation") int operationId) {
        return modelTypeService.getEfficiencyData(powerTrainId, operationId);
    }
}
