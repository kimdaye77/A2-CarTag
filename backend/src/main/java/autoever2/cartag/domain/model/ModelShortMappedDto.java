package autoever2.cartag.domain.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ModelShortMappedDto {

    private int modelId;
    private String modelName;
    private String modelTypeName;
    private Long modelPrice;
    private Long modelBoughtCount;
    private boolean isDefaultModel;
    private String modelImage;
    private int modelTypeId;
    private String maxPs;
    private String maxKgfm;

    @Builder
    public ModelShortMappedDto(int modelId, String modelName, String modelTypeName, Long modelPrice, Long modelBoughtCount, boolean isDefaultModel, String modelImage, int modelTypeId, String maxPs, String maxKgfm) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.modelTypeName = modelTypeName;
        this.modelPrice = modelPrice;
        this.modelBoughtCount = modelBoughtCount;
        this.isDefaultModel = isDefaultModel;
        this.modelImage = modelImage;
        this.modelTypeId = modelTypeId;
        this.maxPs = maxPs;
        this.maxKgfm = maxKgfm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelShortMappedDto that = (ModelShortMappedDto) o;
        return modelId == that.modelId && isDefaultModel == that.isDefaultModel && modelTypeId == that.modelTypeId && Objects.equals(modelName, that.modelName) && Objects.equals(modelTypeName, that.modelTypeName) && Objects.equals(modelPrice, that.modelPrice) && Objects.equals(modelBoughtCount, that.modelBoughtCount) && Objects.equals(modelImage, that.modelImage) && Objects.equals(maxPs, that.maxPs) && Objects.equals(maxKgfm, that.maxKgfm);
    }

    public void setIsDefaultModel(int isDefaultModel) {
        this.isDefaultModel = isDefaultModel > 0;
    }
}
