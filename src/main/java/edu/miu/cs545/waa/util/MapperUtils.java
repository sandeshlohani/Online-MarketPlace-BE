package edu.miu.cs545.waa.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtils<E> {

  ModelMapper modelMapper;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public List<?> mapList(List<?> list, E convertTo) {
    return list.stream().map(o -> getMap(o, convertTo)).collect(Collectors.toList());
  }

  public Object getMap(Object o, E convertTo) {
    return modelMapper.map(o, convertTo.getClass());
  }
}
