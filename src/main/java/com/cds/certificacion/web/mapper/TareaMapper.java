package com.cds.certificacion.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.cds.certificacion.domain.Tarea;
import com.cds.certificacion.web.resource.input.TareaResourceInput;
import com.cds.certificacion.web.resource.output.TareaResourceOutput;

@Mapper
public interface TareaMapper {

  Tarea asEntity(TareaResourceInput resourceInput);

  TareaResourceInput asInput(Tarea entity);

  TareaResourceOutput asOutput(Tarea entity);

  List<TareaResourceOutput> asOutput(List<Tarea> entities);

  void update(@MappingTarget Tarea entity, TareaResourceInput resourceInput);

}
