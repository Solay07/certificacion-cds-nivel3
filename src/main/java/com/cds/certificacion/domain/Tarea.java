package com.cds.certificacion.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * The persistent class for the notas database table.
 * 
 */
@Entity
@Table(name = "tareas")
public class Tarea implements Serializable {
  private static final long serialVersionUID = 1L;

  // Opciones para el estado
  public enum Type {
    Personal, Familiar, Trabajo
  }

  @Id
  @Column(length = 8, columnDefinition = "char")
  private String codigo;

  @NotBlank
  @Column(length = 35)
  private String titulo;

  @Lob
  @Column(length = 65535, columnDefinition = "text")
  private String descripcion;

  @Enumerated(EnumType.STRING)
  private Type tipo;

  public Tarea() {
  }

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Type getTipo() {
    return this.tipo;
  }

  public void setTipo(Type tipo) {
    this.tipo = tipo;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

}