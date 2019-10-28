package com.cds.certificacion.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {

private HttpStatus status;
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  
  private String message;
  private String debugMessage;
  private List<ApiSubError> subErrors;
  
  public ApiError() {
    timestamp = LocalDateTime.now();
  }
  
  public ApiError(HttpStatus status) {
    this();
    this.status = status;
  }
  
  public ApiError(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ApiError(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }
  
  private void addSubError(ApiSubError subError) {
    if (subErrors == null) {
        subErrors = new ArrayList<>();
    }
    subErrors.add(subError);
  }
  
  private void addValidationError(String object, String field, Object rejectedValue, String message) {
    addSubError(new ApiValidationError(object, field, rejectedValue, message));
  }

  private void addValidationError(String object, String message) {
    addSubError(new ApiValidationError(object, message));
  }

  private void addValidationError(FieldError fieldError) {
    this.addValidationError(
            fieldError.getObjectName(),
            fieldError.getField(),
            fieldError.getRejectedValue(),
            fieldError.getDefaultMessage());
  }
  
  public void addValidationErrors(List<FieldError> fieldErrors) {
    fieldErrors.forEach(this::addValidationError);
  }
  
  private void addValidationError(ObjectError objectError) {
    this.addValidationError(
            objectError.getObjectName(),
            objectError.getDefaultMessage());
  }
  
  public void addValidationError(List<ObjectError> globalErrors) {
    globalErrors.forEach(this::addValidationError);
  }
  
  /**
   * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
   *
   * @param cv the ConstraintViolation
   */
  private void addValidationError(ConstraintViolation<?> cv) {
    this.addValidationError(
            cv.getRootBeanClass().getSimpleName(),
            ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
            cv.getInvalidValue(),
            cv.getMessage());
  }

  public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    constraintViolations.forEach(this::addValidationError);
  }
  
  /**
   * @return the status
   */
  public HttpStatus getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  /**
   * @return the timestamp
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the debugMessage
   */
  public String getDebugMessage() {
    return debugMessage;
  }

  /**
   * @param debugMessage the debugMessage to set
   */
  public void setDebugMessage(String debugMessage) {
    this.debugMessage = debugMessage;
  }

  /**
   * @return the subErrors
   */
  public List<ApiSubError> getSubErrors() {
    return subErrors;
  }

  /**
   * @param subErrors the subErrors to set
   */
  public void setSubErrors(List<ApiSubError> subErrors) {
    this.subErrors = subErrors;
  }



  abstract class ApiSubError {

  }
  
  class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
      this.object = object;
      this.message = message;
    }

    /**
     * @param object
     * @param field
     * @param rejectedValue
     * @param message
     */
    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
      this.object = object;
      this.field = field;
      this.rejectedValue = rejectedValue;
      this.message = message;
    }

    /**
     * @return the object
     */
    public String getObject() {
      return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(String object) {
      this.object = object;
    }

    /**
     * @return the field
     */
    public String getField() {
      return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
      this.field = field;
    }

    /**
     * @return the rejectedValue
     */
    public Object getRejectedValue() {
      return rejectedValue;
    }

    /**
     * @param rejectedValue the rejectedValue to set
     */
    public void setRejectedValue(Object rejectedValue) {
      this.rejectedValue = rejectedValue;
    }

    /**
     * @return the message
     */
    public String getMessage() {
      return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
      this.message = message;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getEnclosingInstance().hashCode();
      result = prime * result + ((field == null) ? 0 : field.hashCode());
      result = prime * result + ((message == null) ? 0 : message.hashCode());
      result = prime * result + ((object == null) ? 0 : object.hashCode());
      result = prime * result + ((rejectedValue == null) ? 0 : rejectedValue.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ApiValidationError other = (ApiValidationError) obj;
      if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
        return false;
      if (field == null) {
        if (other.field != null)
          return false;
      } else if (!field.equals(other.field))
        return false;
      if (message == null) {
        if (other.message != null)
          return false;
      } else if (!message.equals(other.message))
        return false;
      if (object == null) {
        if (other.object != null)
          return false;
      } else if (!object.equals(other.object))
        return false;
      if (rejectedValue == null) {
        if (other.rejectedValue != null)
          return false;
      } else if (!rejectedValue.equals(other.rejectedValue))
        return false;
      return true;
    }

    private ApiError getEnclosingInstance() {
      return ApiError.this;
    }
  }

}

class LowerCaseClassNameResolver extends TypeIdResolverBase {

  @Override
  public String idFromValue(Object value) {
      return value.getClass().getSimpleName().toLowerCase();
  }

  @Override
  public String idFromValueAndType(Object value, Class<?> suggestedType) {
      return idFromValue(value);
  }

  @Override
  public JsonTypeInfo.Id getMechanism() {
      return JsonTypeInfo.Id.CUSTOM;
  }

}
