package com.cds.certificacion.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cds.certificacion.domain.Tarea;
import com.cds.certificacion.utils.GenerateCode;
import com.cds.certificacion.web.exception.EntityNotFoundException;
import com.cds.certificacion.web.mapper.TareaMapper;
import com.cds.certificacion.web.repository.NotaRepository;
import com.cds.certificacion.web.resource.input.TareaResourceInput;
import com.cds.certificacion.web.resource.output.TareaResourceOutput;

@RestController
@RequestMapping("/api/tarea")
@CrossOrigin
public class TareaController {

  private final NotaRepository repository;
  private final GenerateCode code;
  private final TareaMapper mapper;

  /**
   * @param repository
   */
  public TareaController(NotaRepository repository, GenerateCode code, TareaMapper mapper) {
    this.repository = repository;
    this.code = code;
    this.mapper = mapper;
  }
  
  /**
   * Obtiene todos los registros de la tabla tarea
   * 
   * @return Lista con todos los registros
   */
  @GetMapping
  @ResponseBody
  public ResponseEntity<List<TareaResourceOutput>> findAll() {
    List<Tarea> entities = (List<Tarea>) repository.findAll();

    return ResponseEntity.ok(mapper.asOutput(entities));
  };
  
  /**
   * Guarda un registro en la tabla tareas
   * 
   * @param resourceInput 
   * @return Location header in response
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> create(@RequestBody TareaResourceInput resourceInput) {
    Tarea entity = mapper.asEntity(resourceInput);
    entity.setCodigo(code.generate());
    Tarea entityCreated = repository.save(entity);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entityCreated.getCodigo())
        .toUri();

    return ResponseEntity.created(location).build();
  }
  
  /**
   * 
   * @param El codigo de la tarea
   * @param resourceInput Datos a guardar
   * @return
   * @throws EntityNotFoundException
   */
  @PutMapping(value = "/{codigo}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<Void> update(@PathVariable String codigo,
                                     @RequestBody TareaResourceInput resourceInput)
  
      throws EntityNotFoundException {
    Tarea entity = repository.findById(codigo).orElseThrow(() -> new EntityNotFoundException(Tarea.class, "codigo", codigo));
    
    System.out.println(resourceInput.toString());
    mapper.update(entity, resourceInput);
    
    repository.save(entity);

    return ResponseEntity.noContent().build();
  }
  
  /**
   * 
   * @param El codigo de la tarea
   * @return
   * @throws EntityNotFoundException
   */
  @DeleteMapping("/{codigo}")
  @ResponseBody
  public ResponseEntity<Void> delete(@PathVariable String codigo) throws EntityNotFoundException {
    Tarea entity = repository.findById(codigo).orElseThrow(() -> new EntityNotFoundException(Tarea.class, "codigo", codigo));
    repository.delete(entity);

    return ResponseEntity.noContent().build();
  }
}
