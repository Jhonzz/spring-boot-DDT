package academy.devdojo.mapper;

import academy.devdojo.DTO.request.ProducerPostRequest;
import academy.devdojo.DTO.request.ProducerPutRequest;
import academy.devdojo.DTO.response.ProducerGetResponse;
import academy.devdojo.domain.Producer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producer toProducer(ProducerPostRequest postRequest);
    Producer toProducer(ProducerPutRequest producerPutRequest);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);

}
