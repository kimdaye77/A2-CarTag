package autoever2.cartag.service;

import autoever2.cartag.domain.model.ModelShortDataDTO;
import autoever2.cartag.repository.model.ModelRepository;
import autoever2.cartag.domain.model.ModelTypeMappedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;

    public List<ModelShortDataDTO> getModelTypeData(int carId) {
        List<ModelTypeMappedDto> modelData = modelRepository.findAllModelTypeData(carId);
        Long carBoughtCount = modelRepository.findCarBoughtCountByCarId(carId).orElse(0L);

        return modelData.stream().map(modelTypeMappedDto -> {
                            int percentage = 0;
                            if (carBoughtCount != 0) {
                                percentage = (int) (modelTypeMappedDto.getModelBoughtCount() * 100 / carBoughtCount);
                            }

                            return ModelShortDataDTO.builder()
                                    .modelId(modelTypeMappedDto.getModelId())
                                    .modelName(modelTypeMappedDto.getModelName())
                                    .modelTypeName(modelTypeMappedDto.getModelTypeName())
                                    .modelPrice(modelTypeMappedDto.getModelPrice())
                                    .isDefaultOption(modelTypeMappedDto.isDefaultOption())
                                    .percentage(percentage)
                                    .build();
                        }
                )

                .collect(Collectors.toList());
    }
}
