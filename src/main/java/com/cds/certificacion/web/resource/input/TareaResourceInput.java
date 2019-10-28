package com.cds.certificacion.web.resource.input;

import javax.validation.constraints.NotBlank;

import com.cds.certificacion.domain.Tarea.Type;

public class TareaResourceInput {

  @NotBlank
  private String titulo;

  private String descripcion;

  private Type tipo;

  // Constructores
  // Sin parametros
  public TareaResourceInput() {
  }

  // Con parametros
  /**
   * @param titulo
   * @param descripcion
   * @param tipo
   */
  public TareaResourceInput(@NotBlank String titulo, String descripcion, Type tipo) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.tipo = tipo;
  }

  // Getters y Setters
  /**
   * @return the titulo
   */
  public String getTitulo() {
    return titulo;
  }

  /**
   * @param titulo the titulo to set
   */
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the tipo
   */
  public Type getTipo() {
    return tipo;
  }

  /**
   * @param tipo the tipo to set
   */
  public void setTipo(Type tipo) {
    this.tipo = tipo;
  }

  @Override
  public String toString() {
    return "NotaResourceInput [titulo=" + titulo + ", descripcion=" + descripcion + ", tipo=" + tipo + "]";
  }
}
