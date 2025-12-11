package academy.devdojo.mapper;

import academy.devdojo.DTO.request.ProducerPostRequest;
import academy.devdojo.DTO.request.ProducerPutRequest;
import academy.devdojo.DTO.response.ProducerGetResponse;
import academy.devdojo.DTO.response.ProducerPostResponse;
import academy.devdojo.DTO.response.ProducerPutResponse;
import academy.devdojo.domain.Producer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producer toProducer(ProducerPostRequest postRequest);
    Producer toProducer(ProducerPutRequest producerPutRequest);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    ProducerPostResponse toProducerPostResponse(Producer producer);

    ProducerPutResponse toProducerPutResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);
}
