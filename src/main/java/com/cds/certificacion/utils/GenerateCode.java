package com.cds.certificacion.utils;

import java.time.Year;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cds.certificacion.web.repository.NotaRepository;

@Component
public class GenerateCode {

  private final NotaRepository repository;

  /**
   * @param repository
   */
  public GenerateCode(NotaRepository repository) {
    this.repository = repository;
  }
  
  /**
   * Genera un codigo para el registro ue se insertara
   * 
   * @return El codigo generado
   */
  public String generate() {
    Long rows = repository.count();
    String code = "CR" + Year.now().format(DateTimeFormatter.ofPattern("uu")) + (rows + 1);
    return StringUtils.rightPad(code, 8);
  }
}
