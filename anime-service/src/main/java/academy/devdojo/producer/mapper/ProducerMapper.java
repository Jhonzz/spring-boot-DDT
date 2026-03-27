package academy.devdojo.producer.mapper;

import academy.devdojo.domain.Producer;
import academy.devdojo.producer.DTO.request.ProducerPostRequest;
import academy.devdojo.producer.DTO.request.ProducerPutRequest;
import academy.devdojo.producer.DTO.response.ProducerGetResponse;
import academy.devdojo.producer.DTO.response.ProducerPostResponse;
import academy.devdojo.producer.DTO.response.ProducerPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    Producer toProducer(ProducerPostRequest postRequest);

    Producer toProducer(ProducerPutRequest producerPutRequest);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    ProducerPostResponse toProducerPostResponse(Producer producer);

    ProducerPutResponse toProducerPutResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);
}
