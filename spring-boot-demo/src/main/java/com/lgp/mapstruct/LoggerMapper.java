package com.lgp.mapstruct;

import com.lgp.dto.logger.LoggerDTO;
import com.lgp.entity.logger.LoggerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-06 16:10
 */
@Mapper(componentModel = "spring")
public interface LoggerMapper {

    @Mappings({
            @Mapping(source = "loggerEntity.id", target = "id"),
            @Mapping(source = "loggerEntity.clientIp", target = "clientIp"),
            @Mapping(source = "loggerEntity.uri", target = "uri"),
            @Mapping(source = "loggerEntity.type", target = "type"),
            @Mapping(source = "loggerEntity.method", target = "method"),
            @Mapping(source = "loggerEntity.paramData", target = "paramData"),
            @Mapping(source = "loggerEntity.returnTime", target = "returnTime"),
            @Mapping(source = "loggerEntity.returnData", target = "returnData"),
    })
    LoggerDTO from(LoggerEntity loggerEntity);


    List<LoggerDTO> fromList(List<LoggerEntity> loggerEntity);


}
